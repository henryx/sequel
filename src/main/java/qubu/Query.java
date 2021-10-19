package qubu;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Query {
    private final List<String> from;
    private List<String> columns;

    private Query(List<String> tables) {
        this.from = tables;
    }

    private String build() {
        if (this.from.isEmpty() || this.columns.isEmpty()) {
            return "";
        }

        String query = this.columns.stream().collect(Collectors.joining(", ", "SELECT ", " FROM "));
        query += String.join(", ", this.from);

        return query;
    }

    /**
     * Sets the table where we select data. This is the entry point
     *
     * @param tables Sets a list of table names used to generate the query
     * @return a builder instance of the class
     */
    public static Query from(String... tables) {
        List<String> tableList = Arrays.stream(tables).filter(e -> !Objects.equals(e, ""))
                .collect(Collectors.toList());

        return new Query(tableList);
    }

    /**
     * Where sets the query filters conditions
     *
     * @return a builder instance of the class
     */
    public Query where() {
        return this;
    }

    /**
     * Select add columns in SELECT query
     *
     * @param columns Sets the columns used to select data
     * @return a builder instance of the class
     */
    public Query select(String... columns) {
        this.columns = Arrays.stream(columns).filter(e -> !Objects.equals(e, ""))
                .collect(Collectors.toList());

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