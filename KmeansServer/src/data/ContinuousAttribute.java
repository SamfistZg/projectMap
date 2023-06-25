package data;

class ContinuousAttribute extends Attribute {
    
    private double max;
    private double min;

    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    public double getMax() {
        return max;
    }
    public double getMin() {
        return min;
    }
    public double getScaledValue(double v) {
        double vScld = (v-min)/(max-min);
        return vScld;
    }
}
