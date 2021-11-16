package com.github.henryx.qubu;

import org.junit.Assert;
import org.junit.Test;

public class QueryTest {

    @Test
    public void testFrom() {
        String expected = "SELECT t1, t2 FROM test";
        String q = Query.from("test")
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testFromLimit() {
        String expected = "SELECT t1, t2 FROM test FETCH FIRST 10 ROWS ONLY";
        String q = Query.from("test")
                .select("t1", "t2")
                .limit(10)
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testFromLimitOffset() {
        String expected = "SELECT t1, t2 FROM test OFFSET 3 ROWS FETCH FIRST 10 ROWS ONLY";
        String q = Query.from("test")
                .select("t1", "t2")
                .limit(10)
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testEmptyFrom() {
        String expected = "";
        String q = Query.from("")
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testEmptySelect() {
        String expected = "";
        String q = Query.from("test")
                .select("")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testMultipleTables() {
        String expected = "SELECT a.t1, b.t2 FROM test1 a, test2 b";
        String q = Query.from("test1 a", "test2 b")
                .select("a.t1", "b.t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testEqualityFilter() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 = 1";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.eq("t1", "1"))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testEqualityFilterSubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 = (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.eq("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testMultipleEqualityFilter() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 = 1 AND t2 = 2";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.eq("t1", "1"))
                .where(Criterion.eq("t2", "2"))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testMultipleEqualityFilterOR() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 = 1 OR t2 = 2";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.eq("t1", "1"))
                .where(Criterion.eq("t2", "2").method(Criterion.OR))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGreatThan() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 > t2";
        String q = Query.from("test")
                .where(Criterion.gt("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGreatThanSubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 > (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.gt("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGreatEqualThan() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 >= t2";
        String q = Query.from("test")
                .where(Criterion.gte("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGreatEqualThanSubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 >= (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.gte("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testLessThan() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 < t2";
        String q = Query.from("test")
                .where(Criterion.lt("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testLessThanSubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 < (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.lt("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testLessEqualThan() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 <= t2";
        String q = Query.from("test")
                .where(Criterion.lte("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testLessEqualThanSubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 <= (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .select("t1", "t2")
                .where(Criterion.lte("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testIn() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 IN (1, 2, 3)";
        String q = Query.from("test")
                .where(Criterion.in("t1", "1", "2", "3"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testInSubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 IN (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .where(Criterion.in("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testNotIn() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 NOT IN ('a', 'b', 'c')";
        String q = Query.from("test")
                .where(Criterion.nin("t1", "'a'", "'b'", "'c'"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testNotInSubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 NOT IN (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .where(Criterion.nin("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testBetween() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 BETWEEN ? AND ?";
        String q = Query.from("test")
                .where(Criterion.between("t1", "?", "?"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testIsNull() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 IS NULL";
        String q = Query.from("test")
                .where(Criterion.isNull("t1"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testIsNotNull() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 IS NOT NULL";
        String q = Query.from("test")
                .where(Criterion.isNotNull("t1"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testNotEquality() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 != t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testNotEqualitySubQuery() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 != (SELECT a2 FROM test WHERE a1 != 3)";
        String q = Query.from("test")
                .where(Criterion.neq("t1", Query.from("test").select("a2").where(Criterion.neq("a1", "3"))))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testMixedFilter() {
        String expected = "SELECT t1, t2 FROM test WHERE t1 != t2 AND t1 >= ? OR t2 IS NOT NULL";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .where(Criterion.gte("t1", "?"))
                .where(Criterion.isNotNull("t2").method(Criterion.OR))
                .select("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGroupBySum() {
        String expected = "SELECT t1, t2, SUM(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.sum("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGroupByAvg() {
        String expected = "SELECT t1, t2, AVG(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.avg("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGroupByMin() {
        String expected = "SELECT t1, t2, MIN(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.min("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGroupByMax() {
        String expected = "SELECT t1, t2, MAX(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.max("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGroupByCount() {
        String expected = "SELECT t1, t2, COUNT(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.count("t3").getSql())
                .groupBy("t1", "t2")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testOrderBy() {
        String expected = "SELECT t1, t2 FROM test ORDER BY t1";
        String q = Query.from("test")
                .select("t1", "t2")
                .orderBy("t1")
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGroupByHaving() {
        String expected = "SELECT t1, t2, COUNT(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2 HAVING COUNT(t3) >= 1000";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.count("t3").getSql())
                .groupBy("t1", "t2")
                .having(Criterion.gte(Functions.count("t3").getSql(), "1000"))
                .getSql();

        Assert.assertEquals(expected, q);
    }

    @Test
    public void testGroupByHavingMultiple() {
        String expected = "SELECT t1, t2, COUNT(t3) FROM test WHERE t1 != t2 GROUP BY t1, t2 HAVING COUNT(t3) >= 1000 AND COUNT(t1) < 10";
        String q = Query.from("test")
                .where(Criterion.neq("t1", "t2"))
                .select("t1", "t2", Functions.count("t3").getSql())
                .groupBy("t1", "t2")
                .having(Criterion.gte(Functions.count("t3").getSql(), "1000"))
                .having(Criterion.lt(Functions.count("t1").getSql(), "10").method(Criterion.AND))
                .getSql();

        Assert.assertEquals(expected, q);
    }
}
