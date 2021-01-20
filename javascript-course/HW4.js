'use strict';

/**
 * Возвращает новый emitter
 * @returns {Object}
 */
function getEmitter() {
    const events = {};
    const answer = {
        /**
         * Подписаться на событие
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         */
        on: function (event, context, handler, times = -1, freq = -1) {
            times = times < 0 ? -1 : times;
            freq = freq < 0 ? -1 : freq;
            if (!events.hasOwnProperty(event)) {
                events[event] = [];
            }
            events[event].push({
                context: context,
                handler: handler,
                times: times,
                freq: freq,
                currFreq: freq === -1 ? -1 : 0
            });
            return this;
        },

        /**
         * Отписаться от события
         * @param {String} event
         * @param {Object} context
         */
        off: function (event, context) {
            function removeEvent(e) {
                events[e] = events[e].filter(obj => context !== obj.context);
            }
            if (events.hasOwnProperty(event)) {
                removeEvent(event);
                Object.keys(events).forEach(e => {
                    if (e.startsWith(`${event}.`)) {
                        removeEvent(e);
                    }
                });
            }
            return this;
        },

        /**
         * Уведомить о событии
         * @param {String} event
         */
        emit: function (event) {
            function execute(handlers) {
                handlers = handlers.map(sub => {
                    if (sub.times === 0) {
                        return sub;
                    }
                    if (sub.freq === -1 || sub.currFreq % sub.freq === 0) {
                        sub.handler.apply(sub.context);
                    }
                    if (sub.freq !== -1) {
                        sub.currFreq = (sub.currFreq + 1) % sub.freq;
                    }
                    sub.times = sub.times == -1 ? -1 : sub.times - 1;
                    return sub;
                }).filter(e => e !== null);
            }

            let curr = event;
            while (curr.length > 0) {
                if (events.hasOwnProperty(curr)) {
                    execute(events[curr]);
                }
                const ind = curr.lastIndexOf('.');
                curr = ind > -1 ? curr.slice(0, ind) : '';
            }
            return this;
        },

        /**
         * Подписаться на событие с ограничением по количеству полученных уведомлений
         * @star
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         * @param {Number} times – сколько раз получить уведомление
         */
        several: function (event, context, handler, times) {
            return answer.on(event, context, handler, times);
        },

        /**
         * Подписаться на событие с ограничением по частоте получения уведомлений
         * @star
         * @param {String} event
         * @param {Object} context
         * @param {Function} handler
         * @param {Number} frequency – как часто уведомлять
         */
        through: function (event, context, handler, frequency) {
            // console.info(event, context, handler, frequency);
            return answer.on(event, context, handler, -1, frequency);
        }
    }
    return answer;
}

module.exports = {
    getEmitter
};
