package web;

/**
 * @author Dylan
 */
public class Domain {

    private Double value;

    public Domain(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value -= value;
    }
}
