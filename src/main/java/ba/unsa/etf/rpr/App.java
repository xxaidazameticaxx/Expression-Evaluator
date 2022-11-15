package ba.unsa.etf.rpr;

/** Parses the console input from the args parameter and validates it
 * @author Aida Zametica
 */
public class App
{
    /** prints the calculated Double value or the exception message depending on the validation
     * @param args is an array of Strings
     */
    public static void main( String[] args )
    {
        try{
            Double solution = ExpressionEvaluator.evaluate(args[0].trim());
            System.out.println("The solution for the input value is : " + solution);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
