import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MainTest {
    @Test
    public void returnsOne() {
        System.out.println("lelijke dubbele aanhalingstekens");
        assertEquals(1, Main.one());
    }
}