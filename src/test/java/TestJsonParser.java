import org.junit.Test;
import util.json.JsonObject;
import util.json.ex.MalformedJsonException;
import util.kits.JsonParser;

// notice: static imports for assertion methods
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestJsonParser {

    @Test
    public void testJsonParser() {
        try {
            JsonObject json = JsonParser.parse("{\"hello\": \"world\"}");
            assertEquals("world", json.get("hello"));
        } catch (MalformedJsonException mjx) {
            fail(mjx.getMessage());
        }
    }
}
