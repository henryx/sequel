package com.github.henryx.qubu;

import java.util.*;
import java.util.stream.Collectors;

public class Query {
    private final List<String> from;
    private final List<Criterion> whereCriteria;
    private final List<Criterion> havingCriteria;
    private final List<String> groupBy;
    private final List<String> orderBy;
    private List<String> columns;
    private Integer limit;
    private Integer offset;


    private Query(List<String> tables) {
        this.from = tables;
        this.whereCriteria = new ArrayList<>();
        this.havingCriteria = new ArrayList<>();
        this.groupBy = new ArrayList<>();
        this.orderBy = new ArrayList<>();
    }

    private String build() {
        if (this.from.isEmpty() || this.columns.isEmpty()) {
            return "";
        }

        String query = this.columns.stream().collect(Collectors.joining(", ", "SELECT ", " FROM "));
        query += String.join(", ", this.from);

        if (!this.whereCriteria.isEmpty()) {
            StringJoiner joiner = new StringJoiner(" ");
            this.whereCriteria.forEach(criterion -> {
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

        if (!this.havingCriteria.isEmpty()) {
            StringJoiner joiner = new StringJoiner(" ");
            this.havingCriteria.forEach(criterion -> {
                if (joiner.length() == 0) {
                    joiner.add(" HAVING");
                } else {
                    joiner.add(criterion.getMethod());
                }
                joiner.add(criterion.getSql());
            });

            query += joiner.toString();
        }

        if (!this.orderBy.isEmpty()) {
            StringJoiner joiner = new StringJoiner(", ", " ORDER BY ", "");
            this.orderBy.forEach(joiner::add);
            query += joiner.toString();
        }

        if (Objects.nonNull(this.limit) && this.limit > 0) {
            StringJoiner joiner = new StringJoiner("", " FETCH FIRST ", " ROWS ONLY");
            joiner.add(this.limit.toString());
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
     * Where sets the query filters conditions
     *
     * @return a builder instance of the class
     */
    public Query where(Criterion criterion) {
        this.whereCriteria.add(criterion);

        return this;
    }

    /**
     * GroupBy sets columns used to aggregate data
     *
     * @param columns sets columns used to aggregate data
     * @return a builder instance of the class
     */
    public Query groupBy(String... columns) {
        Collections.addAll(this.groupBy, columns);

        return this;
    }

    /**
     * Having sets criteria for HAVING clause
     *
     * @param criterion sets a criverion used in HAVING clause
     * @return a builder instance of the class
     */
    public Query having(Criterion criterion) {
        this.havingCriteria.add(criterion);

        return this;
    }

    /**
     * OrderBy sets columns used to order result data
     *
     * @param columns sets columns used to aggregate data
     * @return a builder instance of the class
     */
    public Query orderBy(String... columns) {
        Collections.addAll(this.orderBy, columns);

        return this;
    }

    /**
     * Limit fetch n rows from the result data.
     * According to {@code SQL:2008}, clause of limit is:
     * <p>
     * {@code FETCH FIRST { row_count } ROWS ONLY}
     *
     * @param rows sets the number of the rows to be fetched
     * @return a builder instance of the class
     */
    public Query limit(Integer rows) {
        this.limit = rows;

        return this;
    }

    /**
     * Offset sets the start how result data are fetched
     *
     * @param rows sets the number of the rows to be fetched
     * @return a builder instance of the class
     */
    public Query offset(Integer rows) {
        this.offset = rows;

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