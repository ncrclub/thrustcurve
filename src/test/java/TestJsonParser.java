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

    @Test
    public void todo() throws Exception {
        //String json= "{\"name\" : 3, \"msg\": \"hello world\", \"age\":31}";

        JsonObject json= new JsonObject();
        JsonObject address= new JsonObject();
        JsonArray array= new JsonArray();
        json.set("name", (Integer)null);
        json.set("age", 31);
        address.set("street", "123 Main St.");
        address.set("city", "Springfield");
        address.set("state", "MA");
        address.set("zip", 80504);
        json.set("address", address);
        json.set("array", array);

        array.add(new JsonPrimitive(1));
        array.add(new JsonPrimitive("wo\"rd"));
        array.add(new JsonPrimitive(3.34));
        array.add(new JsonPrimitive("bl[]ue"));
        array.add(new JsonPrimitive("f \t \n ish"));
        // array.add(new JsonPrimitive(true));


        System.out.println("JSON "+ json);

        String jsonString= "{\"here\":40,\"id\":\"300\",\"name\":\"Test\",\"title\":\"Test\",\"path\":\"/pages/mypath\",\"description\":\"\",\"content\":\"my \n<p>content</p>\n\",\"test\":\"\"}";
        jsonString= "{\"here\":[\"thing1\",\"thing2\"],\"id\":\"300\",\"name\":\"Test\",\"title\":\"Test\",\"path\":\"/pages/mypath\",\"description\":\"\",\"content\":\"my \n<p>content</p>\n\",\"test\":\"\"}";

        JsonObject o= JsonParser.parse(jsonString);

        System.out.println(o);

        System.out.println("id = "+ o.get("id", 30));
        System.out.println("content = "+ o.get("content"));


        String j= "{\"services\":[{\"id\":\"PNWN6R3\",\"name\":\"support.morsecode-inc.com\",\"service_url\":\"/services/PNWN6R3\",\"service_key\":\"bb730aa07f74467c8254e827a0c921c8\",\"auto_resolve_timeout\":14400,\"acknowledgement_timeout\":1800,\"created_at\":\"2015-07-30T23:01:56-06:00\",\"deleted_at\":null,\"status\":\"active\",\"last_incident_timestamp\":null,\"email_incident_creation\":null,\"incident_counts\":{\"triggered\":0,\"resolved\":0,\"acknowledged\":0,\"total\":0},\"email_filter_mode\":\"all-email\",\"type\":\"generic_events_api\",\"description\":\"\"}],\"limit\":25,\"offset\":0,\"total\":1}";

        o= JsonParser.parse(j);
        System.out.println(o);
    }
}
