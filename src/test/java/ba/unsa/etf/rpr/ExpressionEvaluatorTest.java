package ba.unsa.etf.rpr;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** A few simple tests for class ExpressionEvaluator
 */
public class ExpressionEvaluatorTest {

    @Test
    void Test1(){
        // iz zadace
        assertEquals(101D, ExpressionEvaluator.evaluate("( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )"));
    }

    @Test
    void Test2(){
        //postoje izrazi koji nisu unutar zagrada
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( 10 + 2 - ( 3 * 4 ) )"));
    }

    @Test
    void Test3(){
        //pravilan broj zagrada ali fali operacija operacija izmedju
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( 4 / 2 ) ( 6 * 3 )"));
    }

    @Test
    void Test4(){
        //prazan string
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( )"));
    }

    @Test
    void Test5(){
        //egzoticna op sqrt
        assertEquals(3D, ExpressionEvaluator.evaluate("( sqrt ( 1 ) + ( sqrt ( 36 ) - 4 ) )"));
    }

    void Test6(){
        //sqrt bez zagrada
        assertThrows(RuntimeException.class, () -> ExpressionEvaluator.evaluate("( sqrt 4 )"));
    }

    @Test
    void Test7(){
        //nepodrzan format broj pored broja
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate("( 1.1 2 + 3 )"));
    }

    @Test
    void Test8(){
        //zagrade su obrnute
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate(") ) 1.5 + 2.3 ( + ) 3 - 2.7 ( ("));
    }

    @Test
    void Test9(){
        //nepodrzano operacija pored operacije
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate("( 2 + - 3)"));
    }

    @Test
    void Test10(){
        //nonsense
        assertThrows(RuntimeException.class,()-> ExpressionEvaluator.evaluate("( # + ? ! )"));
    }
}
