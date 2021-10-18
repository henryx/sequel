package qubu;

import org.junit.Assert;
import org.junit.Test;

public class QueryTest {

    @Test
    public void testFrom() {
        String result = "SELECT t1, t2 FROM test";
        String q = Query.from("test")
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testEmptyFrom() {
        String result = "";
        String q = Query.from("")
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testEmptySelect() {
        String result = "";
        String q = Query.from("test")
                .select("")
                .getSql();

        Assert.assertEquals(q, result);
    }
}
