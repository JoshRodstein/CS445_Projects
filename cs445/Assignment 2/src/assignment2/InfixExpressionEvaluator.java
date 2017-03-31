package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.EmptyStackException;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream.
 * Assignment 2
 * By: Joshua Rodstein
 * CS445 m/w
 */
public class InfixExpressionEvaluator {

    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operators;
    StackInterface<Double> operands;

    /**
     * Initializes the solver to read an infix expression from input.
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                new InputStreamReader(input)));

        // Declare that - and / are regular characters (ignore their regex
        // meaning)
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operators = new ArrayStack<Character>();
        operands = new ArrayStack<Double>();
    }

    /**
     * A type of runtime exception thrown when the given expression is found to
     * be invalid
     */
    class ExpressionError extends RuntimeException {

        ExpressionError(String msg) {
            super(msg);
        }
    }

    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     */
    public static void main(String[] args) {
        InfixExpressionEvaluator solver
                = new InfixExpressionEvaluator(System.in);
        Double value = solver.evaluate();
        if (value != null) {
            System.out.println(value);
        }
    }

    /**
     * Evaluates the expression parsed by the tokenizer and returns the
     * resulting value.
     */
    public Double evaluate() throws ExpressionError {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    processOperand((double) tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // If the token is any of the above characters, process it
                    // is an operator
                    processOperator((char) tokenizer.ttype);
                    break;
                case '(':
                case '[':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processOpenBracket((char) tokenizer.ttype);
                    break;
                case ')':
                case ']':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processCloseBracket((char) tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new ExpressionError("Unrecognized token: "
                            + tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: "
                            + String.valueOf((char) tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        processRemainingOperators();

        // Return the result of the evaluation
        // TODO: Fix this return statement
        if (!operands.isEmpty()) {
            return operands.peek();
        } else {
            return 0.0;
        }
    }

    /**
     * Processes an operand.
     */
    void processOperand(double operand) {
        operands.push(operand);
    }

    /**
     * Processes an operator.
     */
    void processOperator(char operator) {
        Character topOperator;
        double operandOne, operandTwo;

        while (!operators.isEmpty() && precOf(operator)
                <= precOf(operators.peek())) {
            topOperator = operators.pop();
            operandTwo = operands.pop();
            operandOne = operands.pop();
            operands.push(performOp(topOperator, operandOne, operandTwo));
        }
        operators.push(operator);

    }

    /**
     * Processes an open bracket.
     */
    void processOpenBracket(char openBracket) {
        operators.push(openBracket);
    }

    /**
     * Processes a close bracket.
     */
    void processCloseBracket(char closeBracket) throws ExpressionError {
        Character topOperator = operators.pop();
        double operandOne = 0, operandTwo;
        switch (closeBracket) {
            case ')':
                while (topOperator != '(') {
                    if (topOperator == '[') {
                        throw new ExpressionError("Unbalanced expression");
                    }
                    operandTwo = operands.pop();
                    operandOne = operands.pop();
                    operands.push(performOp(topOperator, operandOne, 
                            operandTwo));
                    if (!operators.isEmpty()) {
                        topOperator = operators.pop();
                    } else {
                        break;
                    }
                }
                break;
            case ']':
                while (topOperator != '[') {
                    if (topOperator == '(') {
                        throw new ExpressionError("Unbalanced expression");
                    }
                    operandTwo = operands.pop();
                    operandOne = operands.pop();
                    operands.push(performOp(topOperator, operandOne, 
                            operandTwo));
                    if (!operators.isEmpty()) {
                        topOperator = operators.pop();
                    } else {
                        break;
                    }
                }
                break;
        }
    }

    /**
     * Processes any remaining operators leftover on the operators stack
     */
    void processRemainingOperators() {
        Character topOperator;
        double operandTwo;
        double operandOne;
        while (!operators.isEmpty()) {
            topOperator = operators.pop();
            operandTwo = operands.pop();
            operandOne = operands.pop();
            if (operands.isEmpty() && !operators.isEmpty()) {
                operands.push(performOp(topOperator, operandOne, operandTwo));
                return;
            } else {
                operands.push(performOp(topOperator, operandOne, operandTwo));
            }
        }
    }

    private int precOf(Character entry) {
        switch (entry) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            case '^':
                return 2;
            case '(':
            case '[':
                return -1;
            default:
                return 0;
        }
    }

    private double performOp(Character operator, double op1, double op2) {
        switch (operator) {
            case '+':
                return op1 + op2;
            case '-':
                return op1 - op2;
            case '*':
                return op1 * op2;
            case '/':
                return op1 / op2;
            case '^':
                return Math.pow(op1, op2);
            default:
                return 0.0;
        }
    }

}
