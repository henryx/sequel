package qubu;

import java.util.Arrays;
import java.util.List;

public class Query {
    private final List<String> from;

    private Query(String... tables) {
        this.from = Arrays.asList(tables);
    }

    private String build() {
        return "";
    }

    /**
     * Sets the table where we select data. This is the entry point
     *
     * @param tables Sets a list of table names used to generate the query
     * @return a builder instance of the class
     */
    public static Query from(String... tables) {
        return new Query(tables);
    }

    /**
     * Select add columns in SELECT query
     * @param columns Sets the columns used to select data
     * @return a builder instance of the class
     */
    public Query select(String... columns) {

        return this;
    }

    /**
     * getSql returns generated criterion
     *
     * @return a String that represents the generated query
     */
    public String getSql() {
        return this.build();
    }

    @Override
    public String toString() {
        return this.build();
    }
}