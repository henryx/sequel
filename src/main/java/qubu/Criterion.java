package qubu;

/**
 * Criterion class set criterion used for filtering data
 */
public class Criterion {
    private final String criterion;

    private Criterion(String criterion) {
        this.criterion = criterion;
    }

    private static String build(String... pieces) {
        return String.join(" ", pieces);
    }

    /**
     * Equality filter
     *
     * @param col1 Column at left of the filter
     * @param col2 Column at the right of the filter
     * @return a builder instance of the class
     */
    public static Criterion eq(String col1, String col2) {
        return new Criterion(build(col1, "=", col2));
    }

    /**
     * getSql returns generated criterion
     *
     * @return a String that represents the generated criterion
     */
    public String getSql() {
        return this.criterion;
    }

    @Override
    public String toString() {
        return this.getSql();
    }
}
