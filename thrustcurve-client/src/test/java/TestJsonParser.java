import org.junit.Test;
import org.thrustcurve.api.json.JsonArray;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.json.JsonPrimitive;
import org.thrustcurve.api.json.ex.MalformedJsonException;
import org.thrustcurve.api.JsonParser;

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
