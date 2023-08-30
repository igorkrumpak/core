package si.iitech.util;

import java.util.List;
import java.awt.Color;
import java.awt.Shape;

public class ChartObject {

    private List<Double> yValues;
    private Color color;
    private Shape shape;
    private String name;

    public ChartObject(String name, Color color, List<Double> yValues) {
        this(name, color, null, yValues);
    }

    public ChartObject(String name, Color color, Shape shape, List<Double> yValues) {
        this.name = name;
        this.color = color;
        this.shape = shape;
        this.yValues = yValues;
    }

    public String getName() {
        return name;
    }

    public List<Double> getYValues() {
        return yValues;
    }

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    public Double getMinYValue() {
        return yValues.stream().filter(each -> each != null).min(Double::compareTo).get();
    }

    public Double getMaxYValue() {
        return yValues.stream().filter(each -> each != null).max(Double::compareTo).get();
    }

}
