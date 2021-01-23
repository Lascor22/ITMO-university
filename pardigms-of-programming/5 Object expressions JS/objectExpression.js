const variables = ['x', 'y', 'z'];

function Operation() {
    this.operands = arguments;
}

Operation.prototype.evaluate = function () {
    return this.count(...Array.from(this.operands).map((operand) => operand.evaluate.apply(operand, arguments)));
};

Operation.prototype.toString = function () {
    return Array.from(this.operands).reduce((sum, cur) => sum + cur + ' ', '') + this.operation;
};

Operation.prototype.diff = function (name) {
    let res = [];
    Array.from(this.operands).forEach((operand) => {
        res.push(operand);
        res.push(operand.diff(name));
    });
    return this.countDiff(...res);
};

function Const(a) {
    this.value = a;
}

Const.prototype.evaluate = function () {
    return this.value;
};

Const.prototype.toString = function () {
    return this.value.toString();
};

Const.prototype.diff = function () {
    return new Const(0);
};


function Variable(name) {
    this.index = variables.indexOf(name);
}

Variable.prototype.evaluate = function () {
    return arguments[this.index];
};

Variable.prototype.toString = function () {
    return variables[this.index];
};

Variable.prototype.diff = function (name) {
    if (variables[this.index] === name)
        return new Const(1);
    else
        return new Const(0);
};


function Add(a, b) {
    Operation.call(this, a, b);
    this.operation = '+'
}

Add.prototype = Object.create(Operation.prototype);

Add.prototype.count = function (a, b) {
    return a + b;
};

Add.prototype.countDiff = function (a, a_dir, b, b_dir) {
    return new Add(a_dir, b_dir);
};


function Subtract(a, b) {
    Operation.call(this, a, b);
    this.operation = '-'
}

Subtract.prototype = Object.create(Operation.prototype);

Subtract.prototype.count = function (a, b) {
    return a - b;
};

Subtract.prototype.countDiff = function (a, a_dir, b, b_dir) {
    return new Subtract(a_dir, b_dir);
};


function Multiply(a, b) {
    Operation.call(this, a, b);
    this.operation = '*'
}

Multiply.prototype = Object.create(Operation.prototype);

Multiply.prototype.count = function (a, b) {
    return a * b;
};

Multiply.prototype.countDiff = function (a, a_dir, b, b_dir) {
    return new Add(new Multiply(a, b_dir), new Multiply(a_dir, b));
};


function Divide(a, b) {
    Operation.call(this, a, b);
    this.operation = '/'
}

Divide.prototype = Object.create(Operation.prototype);

Divide.prototype.count = function (a, b) {
    return a / b;
};

Divide.prototype.countDiff = function (a, a_dir, b, b_dir) {
    return new Divide(new Subtract(new Multiply(a_dir, b), new Multiply(a, b_dir)), new Multiply(b, b));
};


function Negate(a) {
    Operation.call(this, a);
    this.operation = 'negate'
}

Negate.prototype = Object.create(Operation.prototype);

Negate.prototype.count = function (a) {
    return -a;
};

Negate.prototype.countDiff = function (a, a_dir) {
    return new Negate(a_dir);
};


function Sinh(a) {
    Operation.call(this, a);
    this.operation = 'sinh';
}

Sinh.prototype = Object.create(Operation.prototype);

Sinh.prototype.count = function (a) {
    return Math.sinh(a);
};

Sinh.prototype.countDiff = function (a, a_dir) {
    return new Multiply(new Cosh(a), a_dir);
};


function Cosh(a) {
    Operation.call(this, a);
    this.operation = 'cosh';

}

Cosh.prototype = Object.create(Operation.prototype);

Cosh.prototype.count = function (a) {
    return Math.cosh(a);
};

Cosh.prototype.countDiff = function (a, a_dir) {
    return new Multiply(new Sinh(a), a_dir);

};


function myNew(constructor, args) {
    let temp = Object.create(constructor.prototype);
    constructor.apply(temp, args);
    return temp;
}

const operations = {
    '+': [Add, 2],
    '-': [Subtract, 2],
    '*': [Multiply, 2],
    '/': [Divide, 2],
    'negate': [Negate, 1],
    'sinh': [Sinh, 1],
    'cosh': [Cosh, 1]
};

function isDigit(c) {
    return c >= '0' && c <= '9';
}

const parse = (expression) => {
    let stack = [];
    let tokens = expression.split(/\s+/);
    tokens.map((token) => {
        if (token in operations) {
            let length = stack.length;
            let args = [];
            stack.slice(length - operations[token][1], length).forEach(function () {
                args.push(stack.pop());
            });
            args.reverse();
            stack.push(myNew(operations[token][0], args));
        } else if (variables.indexOf(token) !== -1) {
            stack.push(new Variable(token));
        } else if ((token[0] === '-' && token.length !== 1) || isDigit(token[0])) {
            stack.push(new Const(parseInt(token)));
        }
    });
    return stack.pop();
};