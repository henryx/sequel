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
     * Sum returns a sum of all values in column
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
