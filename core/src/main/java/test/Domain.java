package test;

/**
 * @author ChNan
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
