package geometry;

import java.awt.Color;
import java.awt.Graphics;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape {
    private Hexagon hexagon;

    public HexagonAdapter(Point center, int radius) {
        this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
    }

    public HexagonAdapter(Hexagon hexagon) {
        this.hexagon = hexagon;
    }

    @Override
    public void draw(Graphics g) {
        hexagon.paint(g);
    }

    @Override
    public boolean contains(int x, int y) {
        return hexagon.contains(x, y);
    }

    @Override
    public boolean isSelected() {
        return hexagon.isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        hexagon.setSelected(selected);
        super.setSelected(selected);
    }

    @Override
    public void moveBy(int byX, int byY) {
        hexagon.setX(hexagon.getX() + byX);
        hexagon.setY(hexagon.getY() + byY);
    }

    @Override
    public void moveTo(int x, int y) {
        hexagon.setX(x);
        hexagon.setY(y);
    }

    public Point getCenter() {
        return new Point(hexagon.getX(), hexagon.getY());
    }

    public void setCenter(Point center) {
        hexagon.setX(center.getX());
        hexagon.setY(center.getY());
    }

    public int getRadius() {
        return hexagon.getR();
    }

    public void setRadius(int radius) {
        hexagon.setR(radius);
    }

    public Color getBorderColor() {
        return hexagon.getBorderColor();
    }

    public void setBorderColor(Color color) {
        hexagon.setBorderColor(color);
    }

    public Color getAreaColor() {
        return hexagon.getAreaColor();
    }

    public void setAreaColor(Color color) {
        hexagon.setAreaColor(color);
    }

    
    public Color getEdgeColor() {
        return getBorderColor();
    }

    public void setEdgeColor(Color color) {
        setBorderColor(color);
    }

    public Color getInnerColor() {
        return getAreaColor();
    }

    public void setInnerColor(Color color) {
        setAreaColor(color);
    }

    public double area(int r) {
        return (3.0 * Math.sqrt(3.0) / 2.0) * r * r;
    }

    @Override
    public String toString() {
        return "Hexagon: center=(" + getCenter().getX() + ", " + getCenter().getY() + "), radius=" + getRadius() 
                + ", edge color=" + getEdgeColor() + ", inner color=" + getInnerColor();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HexagonAdapter) {
            HexagonAdapter other = (HexagonAdapter) obj;
            return getCenter().equals(other.getCenter()) && getRadius() == other.getRadius();
        }
        return false;
    }

    @Override
    public HexagonAdapter clone() {
        Hexagon newHexagon = new Hexagon(hexagon.getX(), hexagon.getY(), hexagon.getR());
        newHexagon.setBorderColor(hexagon.getBorderColor());
        newHexagon.setAreaColor(hexagon.getAreaColor());
        newHexagon.setSelected(hexagon.isSelected());
        return new HexagonAdapter(newHexagon);
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof HexagonAdapter) {
            HexagonAdapter other = (HexagonAdapter) o;
            return (int) (this.area(this.getRadius()) - other.area(other.getRadius()));
        } else {
            return 0;
        }
    }
}
