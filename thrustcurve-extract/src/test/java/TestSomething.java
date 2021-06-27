import club.ncr.dto.PropellantDTO;
import org.junit.Assert;
import org.junit.Test;

public class TestSomething {

    @Test
    public void testSomething() {

        PropellantDTO dto = new PropellantDTO("White Thunder", "AP");

        Assert.assertEquals("White Thunder", dto.name);
        Assert.assertEquals("AP", dto.type);
    }
}
