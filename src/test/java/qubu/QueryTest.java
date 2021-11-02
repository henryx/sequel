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
    public void testGreatThan() {
        String result = "SELECT t1, t2 FROM test WHERE t1 > t2";
        String q = Query.from("test")
                .where(Criterion.gt("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testGreatEqualThan() {
        String result = "SELECT t1, t2 FROM test WHERE t1 >= t2";
        String q = Query.from("test")
                .where(Criterion.gte("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testLessThan() {
        String result = "SELECT t1, t2 FROM test WHERE t1 < t2";
        String q = Query.from("test")
                .where(Criterion.lt("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testLessEqualThan() {
        String result = "SELECT t1, t2 FROM test WHERE t1 <= t2";
        String q = Query.from("test")
                .where(Criterion.lte("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testIn() {
        String result = "SELECT t1, t2 FROM test WHERE t1 IN (1, 2, 3)";
        String q = Query.from("test")
                .where(Criterion.in("t1", "1", "2", "3"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testNotIn() {
        String result = "SELECT t1, t2 FROM test WHERE t1 NOT IN ('a', 'b', 'c')";
        String q = Query.from("test")
                .where(Criterion.nin("t1", "'a'", "'b'", "'c'"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testBetween() {
        String result = "SELECT t1, t2 FROM test WHERE t1 BETWEEN ? AND ?";
        String q = Query.from("test")
                .where(Criterion.between("t1", "?", "?"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testIsNull() {
        String result = "SELECT t1, t2 FROM test WHERE t1 IS NULL";
        String q = Query.from("test")
                .where(Criterion.isNull("t1"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testIsNotNull() {
        String result = "SELECT t1, t2 FROM test WHERE t1 IS NOT NULL";
        String q = Query.from("test")
                .where(Criterion.isNotNull("t1"))
                .select("t1", "t2")
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

    @Test
    public void testMixedFilter() {
        String result = "SELECT t1, t2 FROM test WHERE t1 != t2 AND t1 >= ? OR t2 IS NOT NULL";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .where(Criterion.gte("t1", "?"))
                .where(Criterion.isNotNull("t2").method(Criterion.OR))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testGroupBySum() {
        String result = "SELECT t1, t2, SUM(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.sum("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testGroupByAvg() {
        String result = "SELECT t1, t2, AVG(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.avg("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testGroupByMin() {
        String result = "SELECT t1, t2, MIN(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.min("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testGroupByMax() {
        String result = "SELECT t1, t2, MAX(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.max("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testGroupByCount() {
        String result = "SELECT t1, t2, COUNT(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.count("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testOrderBy() {
        String result = "SELECT t1, t2 FROM test ORDER BY t1";
        String q = Query.from("test")
                .select("t1", "t2")
                .orderBy("t1")
                .getSql();

        Assert.assertEquals(q, result);
    }

    @Test
    public void testGroupByHaving() {
        String result = "SELECT t1, t2, COUNT(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2 HAVING COUNT(t3) >= 1000";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.count("t3").getSql())
                .groupBy("t1", "t2")
                .having(Criterion.gte(Functions.count("t3").getSql(), "1000"))
                .getSql();

        Assert.assertEquals(q, result);
    }
}
