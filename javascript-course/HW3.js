'use strict';

/**
 * @param {Object} schedule Расписание Банды
 * @param {number} duration Время на ограбление в минутах
 * @param {Object} workingHours Время работы банка
 * @param {string} workingHours.from Время открытия, например, "10:00+5"
 * @param {string} workingHours.to Время закрытия, например, "18:00+5"
 * @returns {Object}
 */
function getAppropriateMoment(schedule, duration, workingHours) {
    const bankTimeZone = Number.parseInt(workingHours.from.split('+')[1]);
    const times = [];
    let id = 1;
    Object.keys(schedule).forEach(key => {
        times.push(...normalizeBoy(bankTimeZone, schedule[key], id++));
    });
    addToTime(bankTimeZone, times, workingHours);
    let breakSegments = findTime(times, duration);
    let answer;
    if (breakSegments.length > 0) {
        answer = breakSegments[0];
    }
    let answerExist = !!answer;
    return {
        /**
         * Найдено ли время
         * @returns {boolean}
         */
        exists() {
            return !!answer;
        },

        /**
         * Возвращает отформатированную строку с часами
         * для ограбления во временной зоне банка
         *
         * @param {string} template
         * @returns {string}
         *
         * @example
         * ```js
         * getAppropriateMoment(...).format('Начинаем в %HH:%MM (%DD)') // => Начинаем в 14:59 (СР)
         * ```
         */
        format(template) {
            let formatted;
            if (!!answer) {
                formatted = parseToFormat(answer.start);
            }
            return !!answer ?
                template.replace(/%DD/, formatted.day)
                    .replace(/%HH/, formatted.hours)
                    .replace(/%MM/, formatted.minutes) :
                '';
        },

        /**
         * Попробовать найти часы для ограбления позже [*]
         * @note Не забудь при реализации выставить флаг `isExtraTaskSolved`
         * @returns {boolean}
         */
        tryLater() {
            if (answerExist) {
                if (answer.end - (answer.start + 30) >= duration) {
                    answer.start += 30;
                } else {
                    const filtred = breakSegments.filter(time => time.start >= answer.start + 30);
                    if (filtred.length > 0) {
                        answer = filtred[0];
                    } else {
                        answerExist = false;
                    }
                }
            }
            return answerExist;
        }
    };
}


/**
 * @param {Object[]} times массив для scanline
 * @param {Object[]} duration время на ограбление
 */

function findTime(times, duration) {
    const isFree = {
        ['4']: false
    };
    function isAllFree() {
        return Object.values(isFree).every(state => state);
    }
    let startBreaks = [];
    let previousTime;
    times.forEach(({ value, type }) => {
        const previousState = isAllFree();
        if (type.slice(1) === '4') {
            isFree[type.slice(1)] = type.startsWith('(');
        } else {
            isFree[type.slice(1)] = type.startsWith('(') ? false : true;
        }
        if (previousTime !== -1 && previousState && !isAllFree() && value - previousTime >= duration) {
            startBreaks.push({ start: previousTime, end: value });
        } else if (isAllFree() && !previousState) {
            previousTime = value;
        }
    });
    return startBreaks;
}

/**
 * @param {number} bankTimeZone часовой пояс банка
 * @param {string} day День: ПН | ВТ | СР
 * @param {string} time Время в формате "10:00+5"
 */
function toMinute(bankTimeZone, day, time) {
    let result;
    if (day === 'ПН') {
        result = 0 * (24 * 60);
    } else if (day === 'ВТ') {
        result = 1 * (24 * 60);
    } else if (day === 'СР') {
        result = 2 * (24 * 60);
    } else {
        result = 3 * (24 * 60);
    }
    result += Number.parseInt(time.split(':')[0]) * 60;
    result += Number.parseInt(time.split(':')[1].split('+')[0]);
    result += (bankTimeZone - Number.parseInt(time.split(':')[1].split('+')[1])) * 60;
    return result;
}

/**
 * @param {number} bankTimeZone часовой пояс банка
 * @param {Object} boy член Банды
 * @param {number} id номер человека
 * @returns {Object[]}
 */
function normalizeBoy(bankTimeZone, boy, id) {
    const result = [];
    boy.forEach(function ({ from, to }) {
        result.push({ value: toMinute(bankTimeZone, ...from.split(' ')), type: `(${id}` });
        result.push({ value: toMinute(bankTimeZone, ...to.split(' ')), type: `)${id}` });
    });
    return result;
}

/** 
 * @param {number} bankTimeZone Часовой пояс банка
 * @param {Object[]} times массив для scanline
 * @param {Object} workingHours Время работы банка
 * @param {string} workingHours.from Время открытия, например, "10:00+5"
 * @param {string} workingHours.to Время закрытия, например, "18:00+5"
 */
function addToTime(bankTimeZone, times, workingHours) {
    const bankStart = toMinute(bankTimeZone, 'ПН', workingHours.from);
    const bankEnd = toMinute(bankTimeZone, 'ПН', workingHours.to);
    //понедельник
    times.push({ value: bankStart, type: '(4' });
    times.push({ value: bankEnd, type: ')4' });
    //вторник
    times.push({ value: bankStart + 24 * 60, type: '(4' });
    times.push({ value: bankEnd + 24 * 60, type: ')4' });
    //среда
    times.push({ value: bankStart + 2 * 24 * 60, type: '(4' });
    times.push({ value: bankEnd + 2 * 24 * 60, type: ')4' });
    times.sort((a, b) => a.value - b.value);
}

/**
 * 
 * @param {number} time время в минутах 
 * @return {Object} Форматированное время
 */
function parseToFormat(time) {
    let day;
    if (time < 24 * 60) {
        day = 'ПН';
    } else if (time < 2 * 24 * 60) {
        day = 'ВТ';
    } else {
        day = 'СР';
    }
    let minutes = (time % (24 * 60)) % 60;
    let hours = Math.floor(((time % (24 * 60)) - minutes) / 60);
    function format(n) {
        if (n === 0) {
            return '00';
        }
        if (n < 10) {
            return '0'.concat(n.toString());
        }
        return n.toString();
    }
    return {
        day: day,
        hours: format(hours),
        minutes: format(minutes)
    };
}

const isExtraTaskSolved = true;

module.exports = {
    getAppropriateMoment,
    isExtraTaskSolved
};
