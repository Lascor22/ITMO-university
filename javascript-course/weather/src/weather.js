'use strict';

const fetch = require('node-fetch');

const API_KEY = require('./key.json');
/**
 * @typedef {object} TripItem Город, который является частью маршрута.
 * @property {number} geoid Идентификатор города
 * @property {number} day Порядковое число дня маршрута
 */

class TripBuilder {

    constructor(geoids) {
        this.geoids = geoids || [];
        this.weatherConfig = [];
        this.maxDays = Infinity;
    }

    /**
     * Метод, добавляющий условие наличия в маршруте
     * указанного количества солнечных дней
     * Согласно API Яндекс.Погоды, к солнечным дням
     * можно приравнять следующие значения `condition`:
     * * `clear`;
     * * `partly-cloudy`.
     * @param {number} daysCount количество дней
     * @returns {object} Объект планировщика маршрута
     */
    sunny(daysCount) {
        for (let i = 0; i < daysCount; i++) {
            this.weatherConfig.push(0);
        }
        return this;
    }

    /**
     * Метод, добавляющий условие наличия в маршруте
     * указанного количества пасмурных дней
     * Согласно API Яндекс.Погоды, к солнечным дням
     * можно приравнять следующие значения `condition`:
     * * `cloudy`;
     * * `overcast`.
     * @param {number} daysCount количество дней
     * @returns {object} Объект планировщика маршрута
     */
    cloudy(daysCount) {
        for (let i = 0; i < daysCount; i++) {
            this.weatherConfig.push(1);
        }
        return this;
    }

    /**
     * Метод, добавляющий условие максимального количества дней.
     * @param {number} daysCount количество дней
     * @returns {object} Объект планировщика маршрута
     */
    max(daysCount) {
        this.maxDays = daysCount;
        return this;
    }

    /**
     * Метод, возвращающий Promise с планируемым маршрутом.
     * @returns {Promise<TripItem[]>} Список городов маршрута
     */
    async build() {
        const geoWeather = await getAllWeather(this.geoids);
        let prev = -1;
        let prevCnt = 0;
        let currDay = 0;
        const result = [];
        this.weatherConfig.forEach(dayType => {
            if (prev !== -1 && isOK(dayType, geoWeather.get(prev)[currDay]) && prevCnt < this.maxDays) {
                currDay++;
                result.push({
                    geoid: prev,
                    day: currDay
                });
                prevCnt++;
                return;
            }
            if (prev !== -1) {
                geoWeather.delete(prev);
            }
            for (const [geoid, days] of geoWeather.entries()) {
                if (isOK(dayType, days[currDay])) {
                    prevCnt = 1;
                    prev = geoid;
                    currDay++;
                    result.push({
                        geoid: geoid,
                        day: currDay
                    });
                    return;
                }
            }
            throw new Error('Не могу построить маршрут!');
        });
        return result;
    }
}

/**
 * Фабрика для получения планировщика маршрута.
 * Принимает на вход список идентификаторов городов, а
 * возвращает планировщик маршрута по данным городам.
 *
 * @param {number[]} geoids Список идентификаторов городов
 * @returns {TripBuilder} Объект планировщика маршрута
 * @see https://yandex.ru/dev/xml/doc/dg/reference/regions-docpage/
 */
function planTrip(geoids) {
    return new TripBuilder(geoids);
};

async function getAllWeather(geoids) {
    const geoWeather = new Map();
    for (const geoid of geoids) {
        geoWeather.set(geoid, await getWeather(geoid));
    }
    return geoWeather;
}

async function getWeather(geoid) {
    const API_URL = `https://api.weather.yandex.ru/v2/forecast?hours=false&geoid=${geoid}&limit=7`;
    const rawResponse = await fetch(API_URL, {
        headers: {
            "X-Yandex-API-Key": API_KEY.key
        }
    });
    const JSONResponse = await rawResponse.json();
    const result = [];
    JSONResponse.forecasts.forEach(day => {
        result.push(day.parts.day_short.condition);
    });
    return result;
}

function isOK(dayType, condition) {
    if (dayType === 0) {
        return condition === 'clear' || condition === 'partly-cloudy';
    }
    return condition === 'cloudy' || condition === 'overcast';
}

module.exports = {
    planTrip
};
