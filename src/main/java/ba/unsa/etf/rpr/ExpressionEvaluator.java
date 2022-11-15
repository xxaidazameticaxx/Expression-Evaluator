package ba.unsa.etf.rpr;
import java.util.Stack;
/** Implements the Dijkstra algorithm for arithmetic expressions whose validity is thoroughly checked
 * @author Aida Zametica
 */
public class ExpressionEvaluator {
    /** Validates an arithmetic expression by not throwing or throwing an exception for a large number of possible cases of irregularity
     * @param str is a String
     * @throws RuntimeException
     */
    private static void IsThisExpressionValid(String str) throws RuntimeException {

        // The string received in this method can not be empty else we throw an exception
        if(str.isEmpty()) throw new RuntimeException("Empty string");

        // First character of a string must be "(" and last character of a string must be ")" else we throw an exception
        if(str.charAt(0) != '(' || str.charAt(str.length() - 1) != ')') throw new RuntimeException("This arithmetic expression does not start and end with a parenthesis");

        int numberOfRightParenthesis = 0;
        int numberOfLeftParenthesis = 0;
        int numberOfOperations = 0;

        String[] arrOfStr = str.split(" ");
        for(String s : arrOfStr) {
            if (s.equals("(")) numberOfLeftParenthesis++;
            else if (s.equals(")")) numberOfRightParenthesis++;
            else if (IsItAValidOperation(s)) numberOfOperations++;
        }

        // Number of right and left parenthesis and operations must be the same else we throw an  RuntimeException
        if(numberOfLeftParenthesis != numberOfRightParenthesis || numberOfLeftParenthesis != numberOfOperations) throw new RuntimeException("incorrect number of paranthesis");

        for(int i = 1 ; i < arrOfStr.length - 2; i++){

            if(IsItAValidOperation(arrOfStr[i])){
                if(arrOfStr[i].equals("sqrt")) {
                    //pokrili slučaj kada lijevo od sqrt nije ( a ni operacija te kada desno od ove zagrade nije digit te kada desno od digita nije ) treba uvećati i odmah
                    if ((!arrOfStr[i - 1].equals("(") && !IsItAValidOperation(arrOfStr[i - 1])) || (!arrOfStr[i + 1].equals("(") || !IsItParsable(arrOfStr[i + 2])  || !arrOfStr[i + 3].equals(")")))
                        throw new RuntimeException("incorrect arithmetic expression");
                    else i = i + 3;
                }
                //pokrili slučaj kada je lijevo od operacije nešto što nije operand a ni ) te kada desno od operacije nije ni operand a nije ni zagrada otvorena
                else if((!IsItParsable(arrOfStr[i-1]) && !arrOfStr[i - 1].equals(")")) || (!arrOfStr[i + 1].equals("(") && !IsItParsable(arrOfStr[i+1]) ))
                        throw new RuntimeException("incorrect arithmetic expression");

            }
            else if(arrOfStr[i].equals("(")){
                //pokrili slučaj kada je lijevo nešto što nije ( a nije ni operacija i kada je desno nešto što nije operand niti operacija sqrt niti (
                if((!IsItAValidOperation(arrOfStr[i - 1]) && !arrOfStr[i - 1].equals("(")) || (!IsItParsable(arrOfStr[i + 1])  && !arrOfStr[i + 1].equals("(") && !arrOfStr[i + 1].equals("sqrt")))
                    throw new RuntimeException("incorrect arithmetic expression");
            }
            else if(arrOfStr[i].equals(")")){
                //pokrili slučaj kada je lijevo nešto što nije operand a ni ) a desno nešto što nije ) a nije ni operacija
                if((!(IsItAValidOperation(arrOfStr[i + 1]) && !arrOfStr[i + 1].equals("sqrt")) && !arrOfStr[i + 1].equals(")")) || (!IsItParsable(arrOfStr[i-1])  && !arrOfStr[i - 1].equals(")")))
                    throw new RuntimeException("incorrect arithmetic expression");
            }
            else if(IsItParsable(arrOfStr[i]) ){
                //pokrili slučaj kada je lijevo od operanda nešto što nije operacija a ni ( te kada desno od operanda nije ni ) a  ni operacija
                if((!IsItAValidOperation(arrOfStr[i - 1]) && !arrOfStr[i - 1].equals("(") )  ||  (!IsItAValidOperation(arrOfStr[i + 1]) && !arrOfStr[i + 1].equals(")")))
                    throw new RuntimeException("incorrect arithmetic expression");
            }
            else throw new RuntimeException("incorrect arithmetic expression");
        }
    }

    /** Checks if the parameter s is a number or not
     * @param s is a String
     * @return true if parameter s is parsable else false
     */
    private static boolean IsItParsable(String s){
        try{
            Double doub = Double.valueOf(s);
            return true;
        }
        catch(NumberFormatException ex){
            return false;
        }
    }

    /** Checks if the parameter s is an operation which we consider correct (plus, minus , asterisk, division slash, square root)
     * @param s is a String
     * @return true if the parameter s is a valid operation else false
     */
    private static boolean IsItAValidOperation(String s) {
        return (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("sqrt"));
    }

    private static final Stack<String>ops = new Stack<String>();
    private static final Stack<Double>vals = new Stack<Double>();

    /** implementation of the Dijkstra algorithm described in detail in the assignment
     * @see <a href="https://docs.google.com/document/d/1l1T_Ozv6km9FbHUR1c_SFSRLAUBiMGXUOi5Rg1AEo34/edit">...</a>
     * @param str is a String
     * @return Double value of a fully parenthesized arithmetic expression
     */
    public static Double evaluate(String str) {

        //void method that checks if the expression is valid else it throws an exception for various problems that can occur in validity checking
        IsThisExpressionValid(str);

        //if the given expession is valid it implements the Dijkstra algorithm using to stacks
        String[] arrOfStr = str.split(" ");
        for (String s : arrOfStr) {
            if (s.equals("(")) ;
            else if (s.equals("+")) ops.push(s);
            else if (s.equals("-")) ops.push(s);
            else if (s.equals("*")) ops.push(s);
            else if (s.equals("/")) ops.push(s);
            else if (s.equals("sqrt")) ops.push(s);
            else if (s.equals(")")) {
                String op = ops.pop();
                double v = vals.pop();
                if (op.equals("+")) v = vals.pop() + v;
                else if (op.equals("-")) v = vals.pop() - v;
                else if (op.equals("*")) v = vals.pop() * v;
                else if (op.equals("/")) v = vals.pop() / v;
                else if (op.equals("sqrt")) v = Math.sqrt(v);
                vals.push(v);
            }
            else vals.push(Double.parseDouble(s));
        }
        return vals.pop();
    }
}
