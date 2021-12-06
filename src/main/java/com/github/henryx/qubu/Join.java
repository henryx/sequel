package com.github.henryx.qubu;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Join {
    private final String table;
    private final List<Criterion> criteria;

    private Join(String table) {
        this.criteria = new ArrayList<>();

        this.table = table;
    }

    private String build() {
        StringJoiner joiner = new StringJoiner(" ");
        int counter = 0;

        joiner.add("JOIN").add(this.table).add("ON");
        for (Criterion criterion : this.criteria) {
            if (counter != 0) {
                joiner.add(criterion.getMethod());
            }
            joiner.add(criterion.getSql());
            counter++;
        }

        return joiner.toString();
    }

    /**
     * on sets criterion used in JOIN to compare data
     *
     * @param criterion sets a criterion used in JOIN clause
     * @return a builder instance of the class
     */
    public Join on(Criterion criterion) {
        this.criteria.add(criterion);

        return this;
    }

    /**
     * join sets table used in JOIN operation. This is the entry point
     *
     * @param table Sets the table name
     * @return a builder instance of the class
     */
    public static Join join(String table) {
        return new Join(table);
    }

    /**
     * getSql returns the generated join
     *
     * @return a String that represents the generated query
     */
    public String getSql() {
        return this.build();
    }

    @Override
    public String toString() {
        return this.getSql();
    }
}
