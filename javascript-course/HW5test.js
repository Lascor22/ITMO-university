const lib = require('./HW5.js');
const assert = require('assert');

const friends = [
    { name: 'Sam',
        friends: ['Mat', 'Sharon'],
        gender: 'male',
        best: true },
    { name: 'Sally',
        friends: ['Brad', 'Emily'],
        gender: 'female',
        best: true },
    { name: 'Mat',
        friends: ['Sam', 'Sharon'],
        gender: 'male' },
    { name: 'Sharon',
        friends: ['Sam', 'Itan', 'Mat'],
        gender: 'female' },
    { name: 'Brad',
        friends: ['Sally', 'Emily', 'Julia'],
        gender: 'male' },
    { name: 'Emily',
        friends: ['Sally', 'Brad'],
        gender: 'female' },
    { name: 'Itan',
        friends: ['Sharon', 'Julia'],
        gender: 'male' },
    { name: 'Julia',
        friends: ['Brad', 'Itan'],
        gender: 'female' } ];

const shortMaleFriends = [
    { name: 'A',
        friends: ['B', 'C'],
        gender: 'male',
        best: true },
    { name: 'B',
        friends: ['B', 'C'],
        gender: 'male',
        best: true },
    { name: 'C',
        friends: ['B', 'C', 'D'],
        gender: 'male',
        best: true },
    { name: 'D',
        friends: ['C', 'F'],
        gender: 'male' },
    { name: 'F',
        friends: ['D'],
        gender: 'female' }
];

const shortFemaleFriends = [
    { name: 'A',
        friends: ['B', 'C'],
        gender: 'female',
        best: true },
    { name: 'B',
        friends: ['B', 'C'],
        gender: 'female',
        best: true },
    { name: 'C',
        friends: ['B', 'C', 'D'],
        gender: 'female',
        best: true },
    { name: 'D',
        friends: ['C'],
        gender: 'female' }
];

function friend(name, arr = friends) {
    let len = arr.length;

    while (len--) {
        if (arr[len].name === name) {
            return arr[len];
        }
    }
}


describe('lib.run', () => {
    beforeEach(() => {
    });
    it('MaleFilter instanceof Filter', () => {
        assert.strictEqual((new lib.MaleFilter()) instanceof lib.Filter, true);
    });
    it('FemaleFilter instanceof Filter', () => {
        assert.strictEqual((new lib.FemaleFilter()) instanceof lib.Filter, true);
    });
    it('LimitedIterator instanceof Iterator', () => {
        let maleFilter = new lib.MaleFilter();
        assert.strictEqual((new lib.LimitedIterator(friends, maleFilter, 2)) instanceof lib.Iterator, true);
    });
    it('Test from the task page', () => {
        const maleFilter = new lib.MaleFilter();
        const femaleFilter = new lib.FemaleFilter();
        const maleIterator = new lib.LimitedIterator(friends, maleFilter, 2);
        const femaleIterator = new lib.Iterator(friends, femaleFilter);

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

        assert.deepStrictEqual(invitedFriends, [ [friend('Sam'), friend('Sally')],
                                                 [friend('Brad'), friend('Emily')],
                                                 [friend('Mat'), friend('Sharon')],
                                                 friend('Julia') ])
    }),
    it('Depth one', () => {
        const maleFilter = new lib.MaleFilter();
        const femaleFilter = new lib.FemaleFilter();
        const maleIterator = new lib.LimitedIterator(friends, maleFilter, 1);
        const femaleIterator = new lib.LimitedIterator(friends, femaleFilter, 1);

        const invitedFriends = [];

        while (!maleIterator.done() && !femaleIterator.done()) {
            invitedFriends.push([
                maleIterator.next(),
                femaleIterator.next()
            ]);
        }

        assert.deepStrictEqual(invitedFriends, [ [friend('Sam'), friend('Sally')] ]);
    }),
    it('Depth unlimited', () => {
        const maleFilter = new lib.MaleFilter();
        const femaleFilter = new lib.FemaleFilter();
        const maleIterator = new lib.Iterator(friends, maleFilter);
        const femaleIterator = new lib.Iterator(friends, femaleFilter);

        const invitedFriends = [];

        while (!maleIterator.done() && !femaleIterator.done()) {
            invitedFriends.push([
                maleIterator.next(),
                femaleIterator.next()
            ]);
        }

        assert.deepStrictEqual(invitedFriends, [ [friend('Sam'), friend('Sally')],
                                                 [friend('Brad'), friend('Emily')],
                                                 [friend('Mat'), friend('Sharon')],
                                                 [friend('Itan'), friend('Julia')]])
    })

    it('Males only depth unlim', () => {
        const maleFilter = new lib.MaleFilter();
        const maleIterator = new lib.Iterator(shortMaleFriends, maleFilter);

        const invitedFriends = [];
        while (!maleIterator.done()) {
            invitedFriends.push(maleIterator.next());
        }

        assert.deepStrictEqual(invitedFriends, [
            friend('A', shortMaleFriends),
            friend('B', shortMaleFriends),
            friend('C', shortMaleFriends),
            friend('D', shortMaleFriends)
        ]);
    })

    it('Males only depth 1', () => {
        const maleFilter = new lib.MaleFilter();
        const maleIterator = new lib.LimitedIterator(shortMaleFriends, maleFilter, 1);

        const invitedFriends = [];
        while (!maleIterator.done()) {
            invitedFriends.push(maleIterator.next());
        }

        assert.deepStrictEqual(invitedFriends, [
            friend('A', shortMaleFriends),
            friend('B', shortMaleFriends),
            friend('C', shortMaleFriends)
        ]);
    })

    it('Males only default filter', () => {
        const filter = new lib.Filter();
        const maleIterator = new lib.LimitedIterator(shortMaleFriends, filter, 1);

        const invitedFriends = [];
        while (!maleIterator.done()) {
            invitedFriends.push(maleIterator.next());
        }

        assert.deepStrictEqual(invitedFriends, [
            friend('A', shortMaleFriends),
            friend('B', shortMaleFriends),
            friend('C', shortMaleFriends)
        ]);
    })

    it('Feales only depth unlim', () => {
        const femaleFilter = new lib.FemaleFilter();
        const femaleIterator = new lib.Iterator(shortFemaleFriends, femaleFilter);

        const invitedFriends = [];
        while (!femaleIterator.done()) {
            invitedFriends.push(femaleIterator.next());
        }

        assert.deepStrictEqual(invitedFriends, [
            friend('A', shortFemaleFriends),
            friend('B', shortFemaleFriends),
            friend('C', shortFemaleFriends),
            friend('D', shortFemaleFriends)
        ]);
    })

    it('Females only depth 1', () => {
        const femaleFilter = new lib.FemaleFilter();
        const femaleIterator = new lib.LimitedIterator(shortFemaleFriends, femaleFilter, 1);

        const invitedFriends = [];
        while (!femaleIterator.done()) {
            invitedFriends.push(femaleIterator.next());
        }

        assert.deepStrictEqual(invitedFriends, [
            friend('A', shortFemaleFriends),
            friend('B', shortFemaleFriends),
            friend('C', shortFemaleFriends)
        ]);
    })

    it('Females only default filter', () => {
        const filter = new lib.Filter();
        const femaleIterator = new lib.LimitedIterator(shortFemaleFriends, filter, 1);

        const invitedFriends = [];
        while (!femaleIterator.done()) {
            invitedFriends.push(femaleIterator.next());
        }

        assert.deepStrictEqual(invitedFriends, [
            friend('A', shortFemaleFriends),
            friend('B', shortFemaleFriends),
            friend('C', shortFemaleFriends)
        ]);
    })
});
