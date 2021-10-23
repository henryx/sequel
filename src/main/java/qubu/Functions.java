package qubu;

import java.util.StringJoiner;

/**
 * Functions class builds aggregate functions in standard SQL language
 */
public class Functions {

    private final String function;

    private Functions(String function) {
        this.function = function;
    }

    /**
     * SUM returns an SQL construct used to sum the column in aggregate query
     *
     * @param column the column name
     * @return a builder instance of the class
     */
    public static Functions sum(String column) {
        StringJoiner joiner = new StringJoiner("", "SUM(", ")");
        joiner.add(column);

        return new Functions(joiner.toString());
    }

    /**
     * AVG returns an SQL construct used to calculate the average in the column in aggregate query
     *
     * @param column the column name
     * @return a builder instance of the class
     */
    public static Functions avg(String column) {
        StringJoiner joiner = new StringJoiner("", "AVG(", ")");
        joiner.add(column);

        return new Functions(joiner.toString());
    }

    /**
     * MIN returns an SQL construct used to calculate the minimum value in the column in aggregate query
     *
     * @param column the column name
     * @return a builder instance of the class
     */
    public static Functions min(String column) {
        StringJoiner joiner = new StringJoiner("", "MIN(", ")");
        joiner.add(column);

        return new Functions(joiner.toString());
    }


    /**
     * MAX returns an SQL construct used to calculate the maximum value in the column in aggregate query
     *
     * @param column the column name
     * @return a builder instance of the class
     */
    public static Functions max(String column) {
        StringJoiner joiner = new StringJoiner("", "MAX(", ")");
        joiner.add(column);

        return new Functions(joiner.toString());
    }

    /**
     * getSql returns generated criterion
     *
     * @return a String that represents the generated criterion
     */
    public String getSql() {
        return this.function;
    }

    @Override
    public String toString() {
        return this.getSql();
    }
}
