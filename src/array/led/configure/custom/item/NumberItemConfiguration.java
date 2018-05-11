package array.led.configure.custom.item;

public class NumberItemConfiguration {

    private Double min;
    private Double max;
    private Double step;

    public void setMin(Double min) {
        this.min = min;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public void setStep(Double step) {
        this.step = step;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getStep() {
        return step;
    }

    public boolean hasMin() {
        return min != null;
    }

    public boolean hasMax() {
        return max != null;
    }

    public boolean hasStep() {
        return step != null;
    }
}
