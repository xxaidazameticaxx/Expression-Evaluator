package ba.unsa.etf.rpr;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** A few simple tests for class ExpressionEvaluator
 */
public class ExpressionEvaluatorTest {

    @Test
    void Test1(){
        // from the assignment
        assertEquals(101D, ExpressionEvaluator.evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }

    @Test
    void Test2(){
        //expressions that are not in parentheses
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( 10 + 2 - ( 3 * 4 ) )"));
    }

    @Test
    void Test3(){
        // there is no operation between parentheses
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( 4 / 2 ) ( 6 * 3 )"));
    }

    @Test
    void Test4(){
        //an empty string
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( )"));
    }

    @Test
    void Test5(){
        //checking the unique sqrt operation
        assertEquals(3D, ExpressionEvaluator.evaluate("( sqrt ( 1 ) + ( sqrt ( 36 ) - 4 ) )"));
    }

    @Test
    void Test6(){
        //sqrt without parentheses
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( sqrt 4 )"));
    }

    @Test
    void Test7(){
        //a number next to a number without an operation
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate("( 1.1 2 + 3 )"));
    }

    @Test
    void Test8(){
        //not properly closed parentheses
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate(") ) 1.5 + 2.3 ( + ) 3 - 2.7 ( ("));
    }

    @Test
    void Test9(){
        //operation next to a operation
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate("( 2 + - 3)"));
    }

    @Test
    void Test10(){
        //nonsense
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate("( # + ? ! )"));
    }
    @Test
    void Test11(){
        // operation and sqrt next to each other
        assertEquals(2,ExpressionEvaluator.evaluate("( 1 * sqrt ( 4 ) )"));
    }
}
