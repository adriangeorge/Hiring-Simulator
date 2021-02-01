public class Constraint {
    private Double lowerLimit;
    private Double upperLimit;

    public Constraint(Double lowerLimit, Double upperLimit){
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public Double getLowerLimit() {
        return lowerLimit;
    }

    public Double getUpperLimit() {
        return upperLimit;
    }

}
