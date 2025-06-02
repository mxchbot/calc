import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        //for (;;) {
            System.out.println("Input:");
            String input = (new Scanner(System.in)).nextLine();
            System.out.println("Output:\n" + calc(input));
        //}
    }

    public static String calc(String input)
    {
       try
       {
           return String.valueOf((new Parser()).doExpression(input));
       }
       catch (Exception e)
       {
           return "throws Exception";
       }
    }
}

class Parser
{
    public int doExpression(String expression) throws Exception
    {
        parseExpression(expression);

        return switch (operator)
        {
            case '+' -> (firstOperand + secondOperand);
            case '-' -> (firstOperand - secondOperand);
            case '*' -> (firstOperand * secondOperand);
            case '/' -> (firstOperand / secondOperand);
            default -> throw new Exception();
        };
    }

    private void parseExpression(String expression) throws Exception
    {
        // bad argument
        if ((expression == null) || (expression.isEmpty()))
            throw new Exception();

        // init parser
        currentChar         = 0;
        currentCharIndex    = 0;
        operator            = 0;
        firstOperand        = 0;
        secondOperand       = 0;

        // get first operand
        firstOperand = parseOperand(expression, 0);
        checkOperand(firstOperand);

        // get arithmetic operator
        operator = currentChar;
        checkOperator(operator);

        // get second operand
        secondOperand = parseOperand(expression, (currentCharIndex + 1));
        checkOperand(secondOperand);

        // there must be the end of the expression
        if (currentCharIndex < expression.length())
            throw new Exception();
    }

    private int parseOperand(String expression, int beginIndex) throws Exception
    {
        // look for the end of the operand
        for (currentCharIndex = beginIndex; currentCharIndex < expression.length(); currentCharIndex++)
        {
            currentChar = expression.charAt(currentCharIndex);
            if (!Character.isDigit(currentChar))
                break;
        }

        // there is no digits
        if (currentCharIndex == beginIndex)
            throw new Exception();

        // the expression contains only digits
        if ((currentCharIndex >= expression.length()) && (beginIndex == 0))
            throw new Exception();

        return Integer.parseInt(expression.substring(beginIndex, currentCharIndex));
    }

    private static void checkOperand(int operand) throws Exception
    {
        if ((operand < 1) || (operand > 10))
            throw new Exception();
    }

    private static void checkOperator(char operator) throws Exception
    {
        if (!"+-*/".contains(String.valueOf(operator)))
            throw new Exception();
    }

    private char    currentChar;
    private int     currentCharIndex;
    private char    operator;
    private int     firstOperand;
    private int     secondOperand;
}
