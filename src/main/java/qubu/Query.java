package qubu;

import java.util.*;
import java.util.stream.Collectors;

public class Query {
    private final List<String> from;
    private final List<Criterion> criteria;
    private final List<String> groupBy;
    private List<String> columns;

    private Query(List<String> tables) {
        this.from = tables;
        this.criteria = new ArrayList<>();
        this.groupBy = new ArrayList<>();
    }

    private String build() {
        if (this.from.isEmpty() || this.columns.isEmpty()) {
            return "";
        }

        String query = this.columns.stream().collect(Collectors.joining(", ", "SELECT ", " FROM "));
        query += String.join(", ", this.from);

        if (!this.criteria.isEmpty()) {
            StringJoiner joiner = new StringJoiner(" ");
            this.criteria.forEach(criterion -> {
                if (joiner.length() == 0) {
                    joiner.add(" WHERE");
                } else {
                    joiner.add(criterion.getMethod());
                }

                joiner.add(criterion.getSql());
            });

            query += joiner.toString();
        }

        if (!this.groupBy.isEmpty()) {
            StringJoiner joiner = new StringJoiner(", ", " GROUP BY ", "");
            this.groupBy.forEach(joiner::add);
            query += joiner.toString();
        }


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
    public Query where(Criterion criterion) {
        this.criteria.add(criterion);

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
     * GroropBy sets columns used to aggregate data
     *
     * @param columns sets columns used to aggregate data
     * @return a builder instance of the class
     */
    public Query groupBy(String... columns) {
        Collections.addAll(this.groupBy, columns);

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