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

    @Test
    public void testEqualityFilter() {
        String result = "SELECT t1, t2 FROM test WHERE t1 = 1";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.eq("t1", "1"))
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testMultipleEqualityFilter() {
        String result = "SELECT t1, t2 FROM test WHERE t1 = 1 AND t2 = 2";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.eq("t1", "1"))
                .where(Criterion.eq("t2", "2"))
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testMultipleEqualityFilterOR() {
        String result = "SELECT t1, t2 FROM test WHERE t1 = 1 OR t2 = 2";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.eq("t1", "1"))
                .where(Criterion.eq("t2", "2").method(Criterion.OR))
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testNotEquality() {
        String result = "SELECT t1, t2 FROM test WHERE t1 != t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }
}
