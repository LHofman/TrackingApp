package domain;

/**
 * Custom Tuple class
 * @param <X>
 * @param <Y>
 */
public class Tuple<X, Y> {

    public X x;
    public Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
}
