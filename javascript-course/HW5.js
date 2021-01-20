'use strict';

const { callbackify } = require("util");

/**
 * Итератор по друзьям
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 */
function Iterator(friends, filter, maxLevel = Infinity) {
    if (!(filter instanceof Filter)) {
        throw new TypeError('')
    }
    const result = generalIterator(friends, filter, maxLevel);
    let i = 0;
    this.next = () => {
        if (i >= result.length) {
            return null;
        }
        return result[i++];
    };
    this.done = () => i >= result.length;
}


/**
 * Итератор по друзям с ограничением по кругу
 * @extends Iterator
 * @constructor
 * @param {Object[]} friends
 * @param {Filter} filter
 * @param {Number} maxLevel – максимальный круг друзей
 */
function LimitedIterator(friends, filter, maxLevel) {
    if (!(filter instanceof Filter)) {
        throw new TypeError('')
    }
    Iterator.call(this, friends, filter, maxLevel);
}

/**
 * Фильтр друзей
 * @constructor
 */
function Filter() {
    this.predicate = (_) => true;
}

/**
 * Фильтр друзей
 * @extends Filter
 * @constructor
 */
function MaleFilter() {
    this.predicate = (friend) => friend.gender === 'male';
}

/**
 * Фильтр друзей-девушек
 * @extends Filter
 * @constructor
 */
function FemaleFilter() {
    this.predicate = (friend) => friend.gender === 'female';
}

function generalIterator(friends, filter, maxLevel = Infinity) {
    const graph = objToMap(friends);
    let init = friends.filter(e => e.best);
    const result = bfs(graph, init, maxLevel);
    return result.filter(filter.predicate);
}

function comparator(a, b) {
    if (a.name < b.name) {
        return -1;
    }
    if (a.name > b.name) {
        return 1;
    }
    return 0;
}

/**
 * @param {Map<string, Friend>} graph - graph of friends 
 * @param {Map<Friend, Boolean>} used - special map for dfs 
 * @param {Friend[]} result - resulted list of friend 
 * @param {Friend} initial - init friend
 */
function bfs(graph, initial, maxLevel) {
    const was = {};
    const levels = [];
    const layers = {};
    const queue = [];
    initial.forEach(friend => {
        was[friend.name] = true;
        layers[friend.name] = 0;
    });

    queue.push(...initial);
    while (queue.length > 0) {
        const value = queue[0];
        queue.shift()

        if (!levels[layers[value.name]]) {
            levels[layers[value.name]] = [];
        }

        levels[layers[value.name]].push(value);

        value.friends.forEach(friendName => {
            const friend = graph.get(friendName);
            if (friend && !was[friend.name]) {
                layers[friend.name] = layers[value.name] + 1;
                queue.push(friend);
                was[friend.name] = true;
            }
        });
    }
    const result = [];
    for (const level of levels) {
        level.sort(comparator);
        result.push(...level);
    }
    return result.filter(friend => layers[friend.name] < maxLevel);
}

/**
 * 
 * @param {Friend[]} friends - friends
 * @returns {Map<string, Friend>} - map from name to friend 
 */

function objToMap(friends) {
    const res = new Map();
    friends.forEach(friend => res.set(friend.name, friend));
    return res;
}

MaleFilter.prototype = Object.create(Filter.prototype);
MaleFilter.prototype.constructor = MaleFilter;
FemaleFilter.prototype = Object.create(Filter.prototype);
FemaleFilter.prototype.constructor = FemaleFilter;
LimitedIterator.prototype = Object.create(Iterator.prototype);
LimitedIterator.prototype.constructor = LimitedIterator;

function main() {
    const friends = [
        {
            name: 'Sam',
            friends: ['Mat', 'Sharon'],
            gender: 'male',
            best: true
        },
        {
            name: 'Sally',
            friends: ['Brad', 'Emily'],
            gender: 'female',
            best: true
        },
        {
            name: 'Mat',
            friends: ['Sam', 'Sharon'],
            gender: 'male'
        },
        {
            name: 'Sharon',
            friends: ['Sam', 'Itan', 'Mat'],
            gender: 'female'
        },
        {
            name: 'Brad',
            friends: ['Sally', 'Emily', 'Julia'],
            gender: 'male'
        },
        {
            name: 'Emily',
            friends: ['Sally', 'Brad'],
            gender: 'female'
        },
        {
            name: 'Itan',
            friends: ['Sharon', 'Julia'],
            gender: 'male'
        },
        {
            name: 'Julia',
            friends: ['Brad', 'Itan'],
            gender: 'female'
        }
    ];

    function friend(name) {
        let len = friends.length;

        while (len--) {
            if (friends[len].name === name) {
                return friends[len];
            }
        }
    }

    const maleFilter = new MaleFilter();
    const femaleFilter = new FemaleFilter();
    const maleIterator = new LimitedIterator(friends, maleFilter, 2);
    const femaleIterator = new Iterator(friends, femaleFilter);

    const invitedFriends = [];

    while (!maleIterator.done() && !femaleIterator.done()) {
        invitedFriends.push([
            maleIterator.next(),
            femaleIterator.next()
        ]);
    }

    while (!femaleIterator.done()) {
        invitedFriends.push(femaleIterator.next());
    }

    // invitedFriends:
    [
        [friend('Sam'), friend('Sally')],
        [friend('Brad'), friend('Emily')],
        [friend('Mat'), friend('Sharon')],
        friend('Julia')
    ]
}

// main();

exports.Iterator = Iterator;
exports.LimitedIterator = LimitedIterator;

exports.Filter = Filter;
exports.MaleFilter = MaleFilter;
exports.FemaleFilter = FemaleFilter;
