package operation;

import exception.DivisionByZeroException;
import exception.OverflowException;

public class IntegerBinaryOperation implements BinaryOperation<Integer> {
    private boolean checkedOperation;

    public IntegerBinaryOperation(boolean checkedOperation) {
        this.checkedOperation = checkedOperation;
    }

    public Integer add(Integer firstOperand, Integer secondOperand) throws OverflowException {
        if (checkedOperation) {
            checkAdd(firstOperand, secondOperand);
        }
        return firstOperand + secondOperand;
    }

    private void checkAdd(Integer firstOperand, Integer secondOperand) throws OverflowException {
        if (firstOperand > 0 && secondOperand > Integer.MAX_VALUE - firstOperand) {
            throw new OverflowException();
        }
        if (firstOperand < 0 && secondOperand < Integer.MIN_VALUE - firstOperand) {
            throw new OverflowException();
        }
    }

    public Integer sub(Integer firstOperand, Integer secondOperand) throws DivisionByZeroException, OverflowException {
        if (checkedOperation) {
            checkSub(firstOperand, secondOperand);
        }
        return firstOperand - secondOperand;
    }

    private void checkSub(Integer firstOperand, Integer secondOperand) throws DivisionByZeroException, OverflowException {
        if (firstOperand == Integer.MIN_VALUE && secondOperand == -1) {
            throw new OverflowException();
        }
        if (secondOperand == 0) {
            throw new DivisionByZeroException();
        }
    }

    public Integer mul(Integer firstOperand, Integer secondOperand) throws OverflowException {
        if (checkedOperation) {
            checkMul(firstOperand, secondOperand);
        }
        return firstOperand * secondOperand;
    }

    private void checkMul(Integer firstOperand, Integer secondOperand) throws OverflowException {
        if (secondOperand < 0) {
            if (firstOperand > 0 && secondOperand < Integer.MIN_VALUE / firstOperand) {
                throw new OverflowException();
            }
            if (firstOperand < 0 && secondOperand < Integer.MAX_VALUE / firstOperand) {
                throw new OverflowException();
            }
        }
        if (secondOperand > 0) {
            if (firstOperand > 0 && firstOperand > Integer.MAX_VALUE / secondOperand) {
                throw new OverflowException();
            }
            if (firstOperand < 0 && firstOperand < Integer.MIN_VALUE / secondOperand) {
                throw new OverflowException();
            }
        }
    }

    public Integer div(Integer firstOperand, Integer secondOperand) throws DivisionByZeroException, OverflowException {
        if (secondOperand == 0) {
            throw new DivisionByZeroException();
        }
        if (checkedOperation) {
            checkDiv(firstOperand, secondOperand);
        }
        return firstOperand / secondOperand;
    }

    private void checkDiv(Integer firstOperand, Integer secondOperand) throws OverflowException {
        if (firstOperand == Integer.MIN_VALUE && secondOperand == -1) {
            throw new OverflowException();
        }
    }

    public Integer parseValue(String value) throws NumberFormatException {
        return Integer.parseInt(value);
    }
}
