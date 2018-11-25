import com.nss.parser.ItemParser;
import com.nss.vo.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestItemParser {

    //@Test
    public void parseItems() throws Exception {
        String s = "1 chocolate bar at 0.85";

        Item item = ItemParser.parse(s);

        Assertions.assertEquals("chocolate bar", item.getName(), "Name should be Choclate Bar");
        Assertions.assertEquals(1, ItemParser.getCount(s), "Count should be 1");
        Assertions.assertEquals(14.99, item.getPrice(), "Price should be same");

        s = "3 music CD at 14.99";

        item = ItemParser.parse(s);

        Assertions.assertEquals("music CD", item.getName(), "Name should be Music CD");
        Assertions.assertEquals(3, ItemParser.getCount(s), "Count should be same");
        Assertions.assertEquals(0.85, item.getPrice(), "Price should be same");


        s = "2 imported bottle of perfume at 27.99";
        item = ItemParser.parse(s);

        Assertions.assertEquals("imported bottle of perfume", item.getName(), "Name should be Same");
        Assertions.assertEquals(2, ItemParser.getCount(s), "Count should be same");
        Assertions.assertEquals(27.99, item.getPrice(), "Price should be same");
    }

    @Test
    public void testInvalidItemParse() {
        String s = "chocolate bar at 0.85";
        Assertions.assertThrows(IllegalStateException.class, () -> {
            ItemParser.parse(s);
        });
    }

}
