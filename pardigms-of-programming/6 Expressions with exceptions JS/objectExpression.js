"use strict";

Array.prototype.last = function () {
    return this[this.length - 1]
};

Array.prototype.empty = function () {
    return this.length === 0
};

const operators = (function () {

    function AbstractOperator(...op) {
        this.op = op;
    }

    AbstractOperator.prototype.evaluate = function (...args) {
        return this.makeOperation(...this.op.map(function (operand) {
            return operand.evaluate(...args)
        }));
    };

    AbstractOperator.prototype.toString = function () {
        return (this.op).reduce((current, operand) => current += operand.toString() + " ", "") + this.operatorCharacter();
    };

    AbstractOperator.prototype.postfix = function () {
        return "(" + (this.op).reduce((current, operand) => current += operand.postfix() + " ", "").trim() + " " + this.operatorCharacter() + ")";
    };

    AbstractOperator.prototype.diff = function (x) {
        let dop = [];
        for (let i = 0; i < (this.op).length; i++) {
            dop[i] = (this.op)[i].diff(x);
        }
        return this.typeDiff.apply(null, (this.op).concat(dop));
    };

    let newOperator = function (operator, func, character, diffFunction) {
        operator.prototype = Object.create(AbstractOperator.prototype);
        operator.prototype.makeOperation = func;
        operator.prototype.operatorCharacter = () => character;
        operator.prototype.typeDiff = diffFunction;
    };

    function Const(a) {
        this.getConst = function () {
            return a
        };
    }

    Const.prototype = Object.create(AbstractOperator.prototype);

    Const.prototype.evaluate = function () {
        return this.getConst()
    };

    Const.prototype.toString = function () {
        return this.getConst().toString()
    };

    Const.prototype.postfix = function () {
        return this.getConst().toString()
    };

    Const.ZERO = new Const(0);
    Const.ONE = new Const(1);
    Const.TWO = new Const(2);

    Const.prototype.diff = function (x) {
        return Const.ZERO;
    };

    function Add(a, b) {
        AbstractOperator.call(this, a, b);
    }

    newOperator(Add, (a, b) => a + b, "+", (f, g, df, dg) =>
        new Add(df, dg));

    function Subtract(a, b) {
        AbstractOperator.call(this, a, b);
    }

    newOperator(Subtract, (a, b) => a - b, "-", (f, g, df, dg) =>
        new Subtract(df, dg));

    function Multiply(a, b) {
        AbstractOperator.call(this, a, b);
    }

    newOperator(Multiply, (a, b) => a * b, "*", (f, g, df, dg) =>
        new Add(new Multiply(df, g), new Multiply(f, dg)));

    function Divide(a, b) {
        AbstractOperator.call(this, a, b);
    }

    newOperator(Divide, (a, b) => a / b, "/", (f, g, df, dg) =>
        new Divide(new Subtract(new Multiply(df, g),
            new Multiply(f, dg)),
            new Multiply(g, g)));

    function Negate(a) {
        AbstractOperator.call(this, a);
    }

    newOperator(Negate, (a) => -a, "negate", (f, df) =>
        new Negate(df));

    function Sum(...args) {
        AbstractOperator.apply(this, args);
    }

    newOperator(Sum, (...args) => args.reduce((curSum, x) => curSum += x, 0), "sum");
    Sum.prototype.typeDiff = (...f) => new Sum(...f.slice(f.length / 2));

    function Sumsq(...args) {
        AbstractOperator.apply(this, args);
    }

    newOperator(Sumsq, (...args) => args.reduce((curSum, x) => curSum += x * x, 0), "sumsq",
        (...f) => new Sum(...f.slice(0, f.length / 2).map(
            (x, i) => new Multiply(f[f.length / 2 + i], new Multiply(x, Const.TWO)))));

    function Length(...args) {
        AbstractOperator.apply(this, args);
    }

    newOperator(Length, (...args) => Math.sqrt(args.reduce((curSum, x) => curSum += x * x, 0)), "length",
        (...f) => (f.empty() ? Const.ZERO : new Multiply(new Divide(new Const(0.5), new Length(...f.slice(0, f.length / 2))),
            Sumsq.prototype.typeDiff(...f))));

    function Variable(a) {
        this.getVariable = function () {
            return a
        };
    }

    Variable.prototype = Object.create(AbstractOperator.prototype);

    Variable.prototype.evaluate = function (...args) {
        return args[this.getVariable() === "x" ? 0 : this.getVariable() === "y" ? 1 : 2]
    };

    Variable.prototype.toString = function () {
        return this.getVariable()
    };

    Variable.prototype.postfix = function () {
        return this.getVariable()
    };

    Variable.prototype.diff = function (x) {
        return this.getVariable() === x ? Const.ONE : Const.ZERO;
    };

    return {
        Add: Add,
        Subtract: Subtract,
        Multiply: Multiply,
        Divide: Divide,
        Negate: Negate,
        Const: Const,
        Variable: Variable,
        Sum: Sum,
        Sumsq: Sumsq,
        Length: Length
    }

})();

const Add = operators.Add,
    Subtract = operators.Subtract,
    Multiply = operators.Multiply,
    Divide = operators.Divide,
    Negate = operators.Negate,
    Const = operators.Const,
    Variable = operators.Variable,
    Sum = operators.Sum,
    Avg = operators.Avg,
    Sumsq = operators.Sumsq,
    Length = operators.Length;

const exceptions = (function () {

    function newExceptions(exception, name) {
        exception.prototype = Object.create(Error.prototype);
        exception.prototype.name = name;
        exception.prototype.constructor = exception;
    }

    let ExceptionMessage = (expected, found, position) => "expected " + expected + " ,but found '" + found + "' on position " + (position + 1).toString();

    function MissingOperandException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    function MissingOperatorException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    function WrongNumberOfArgumentsException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    function InvalidEndException(expected, found, position) {
        this.message = ExceptionMessage(expected, found, position);
    }

    newExceptions(MissingOperandException, "MissingOperandException");
    newExceptions(MissingOperatorException, "MissingOperatorException");
    newExceptions(WrongNumberOfArgumentsException, "WrongNumberOfArgumentsException");
    newExceptions(InvalidEndException, "InvalidEndException");

    return {
        MissingOperandException: MissingOperandException,
        MissingOperatorException: MissingOperatorException,
        WrongNumberOfArgumentsException: WrongNumberOfArgumentsException,
        InvalidEndException: InvalidEndException
    }
})();

const MissingOperandException = exceptions.MissingOperandException,
    MissingOperatorException = exceptions.MissingOperatorException,
    WrongNumberOfArgumentsException = exceptions.WrongNumberOfArgumentsException,
    InvalidEndException = exceptions.InvalidEndException;

class Tokenizer {
    constructor(exp) {
        this.exp = exp;
        this.index = 0;
        this.skipWhiteSpaces();
    }

    static tokenCharacter(c) {
        return !/\s/.test(c) && c !== "(" && c !== ")";
    }

    nextToken() {
        let res = "";
        if (this.exp[this.index] === "(") {
            res += "(";
            this.index++;
            this.skipWhiteSpaces();
        }
        while (this.index < this.exp.length && Tokenizer.tokenCharacter(this.exp[this.index])) {
            res += this.exp[this.index];
            this.index++;
        }
        this.skipWhiteSpaces();
        if (this.index < this.exp.length && this.exp[this.index] === ")") {
            res += ")";
            this.index++;
            this.skipWhiteSpaces();
        }
        return res;
    }

    getIndex() {
        return this.index;
    }

    skipWhiteSpaces() {
        while (this.index < this.exp.length && /\s/.test(this.exp[this.index])) {
            this.index++;
        }
    }
}

const parser = (function () {
    let countArgs = [];
    let operations = [];
    let variables = [];
    variables["x"] = variables["y"] = variables["z"] = true;

    let addOperation = function (str, type, cnt) {
        countArgs[str] = cnt;
        operations[str] = type;
    };

    addOperation("+", Add, 2);
    addOperation("-", Subtract, 2);
    addOperation("*", Multiply, 2);
    addOperation("/", Divide, 2);
    addOperation("negate", Negate, 1);
    addOperation("sum", Sum, Infinity);
    addOperation("avg", Avg, Infinity);
    addOperation("sumsq", Sumsq, Infinity);
    addOperation("length ", Length, Infinity);

    let parse = (exp) => exp.trim().split(/\s+/).reduce(functionParse, []).pop();

    let functionParse = (stack, token) => {
        if (isNumber(token)) {
            stack.push(new Const(parseInt(token, 10)));
        } else if (variables[token] === true) {
            stack.push(new Variable(token));
        } else {
            stack.push(new operations[token](...stack.splice(-countArgs[token])));
        }
        return stack;
    };

    let isNumber = function (token) {
        return !isNaN(parseInt(token, 10));
    };

    let parsePostfix = function (exp) {
        let tokens = new Tokenizer(exp);
        let token;
        let args = [];
        let parsedArgs = [];
        while ((token = tokens.nextToken()) !== "") {
            let openBracket = false;
            if (token[0] === "(") {
                openBracket = true;
                token = token.substring(1);
            }
            let closeBracket = false;
            if (token[token.length - 1] === ")") {
                closeBracket = true;
                token = token.substring(0, token.length - 1);
            }
            if (token === "length") {
                token += " ";
            }
            if (openBracket) {
                if (operations[token] !== undefined && !closeBracket) {
                    throw new MissingOperandException("Const, Variable or new SubExpression", token, tokens.getIndex() - token.length);
                }
                parsedArgs.push(0);
            }
            if (closeBracket) {
                if (operations[token] === undefined) {
                    throw new MissingOperatorException("operator before )", token, tokens.getIndex() - token.length);
                }
                if (parsedArgs.empty() || (countArgs[token] !== Infinity && parsedArgs.last() !== countArgs[token])) {
                    throw new WrongNumberOfArgumentsException("more arguments for " + token, token + ")", tokens.getIndex() - token.length);
                }
                let parts = args.splice(-parsedArgs.last());
                args.push(new operations[token](...parts));
                parsedArgs.pop();
                if (!parsedArgs.empty()) {
                    parsedArgs[parsedArgs.length - 1]++;
                }
            }
            if (token !== "") {
                if (!isNaN(Number(token))) {
                    args.push(new Const(parseInt(token, 10)));
                    parsedArgs[parsedArgs.length - 1]++;
                } else if (variables[token] === true) {
                    args.push(new Variable(token));
                    parsedArgs[parsedArgs.length - 1]++;
                } else if (closeBracket === false) {
                    throw new MissingOperandException("Const, Variable or new SubExpression", token, tokens.getIndex() - token.length);
                }
            }
        }
        if (args.length !== 1 || parsedArgs.length > 0) {
            throw new InvalidEndException("fully parsed expression", "a non-parsed arguments: " + args.join(", "), tokens.getIndex() - token.length);
        }
        return args.pop();
    };

    return {
        parse: parse,
        parsePostfix: parsePostfix
    };
})();

const parse = parser.parse,
    parsePostfix = parser.parsePostfix;