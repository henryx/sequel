package qubu;

/**
 * Functions class builds aggregate functions in standard SQL language
 */
public class Functions {

    private Functions() {
    }

    /**
     * Sum returns a sum of all values in column
     *
     * @param column the column name
     * @return a builder instance of the class
     */
    public static Functions sum(String column) {
        return new Functions();
    }
}
