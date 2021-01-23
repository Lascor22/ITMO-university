const variables = ['x', 'y', 'z'];

const abstractOperation = operation => (...operands) => (...values) => {
    let result = [];
    for (let operand of operands) {
        result.push(operand(...values));
    }

    return operation(...result);
};

const variable = name => {
    let index = variables.indexOf(name);
    return (...values) => values[index];
};

const cnst = value => () => value;

const add = abstractOperation((a, b) => a + b);

const subtract = abstractOperation((a, b) => a - b);

const multiply = abstractOperation((a, b) => a * b);

const divide = abstractOperation((a, b) => a / b);

const negate = abstractOperation(a => -a);

const abs = abstractOperation(a => Math.abs(a));

const iff = abstractOperation((a, b, c) => a >= 0 ? b : c);

function isDigit(c) {
    return c >= '0' && c <= '9';
}

const operations = {
    '+': [add, 2],
    '-': [subtract, 2],
    '*': [multiply, 2],
    '/': [divide, 2],
    'negate': [negate, 1],
    'abs': [abs, 1],
    'iff': [iff, 3],
};

const parse = (expression) => (...values) => {
    let tokens = expression.split(/\s+/);
    let stack = [];
    tokens.map(function (token) {
        if (token in operations) {
            let args = [];
            let len = stack.length;
            stack.slice(len - operations[token][1], len).forEach(function () {
                args.push(stack.pop());
            });
            args.reverse();
            stack.push(operations[token][0](...args));
        } else if (isDigit(token[0]) || (token[0] === '-' && token.length !== 1)) {
            stack.push(cnst(parseInt(token)));
        } else if (variables.indexOf(token) !== -1) {
            stack.push(variable(token));
        }
    });
    return stack.pop()(...values);
};