package command;

import geometry.Shape;

public interface Command {
    void execute();
    void unexecute();
    String toString();

    static String getShapeLogString(Shape shape) {
        if (shape instanceof geometry.Point) {
            geometry.Point p = (geometry.Point) shape;
            return "Point(" + p.getX() + "," + p.getY() + "," + p.getColor().getRGB() + ")";
        } else if (shape instanceof geometry.Line) {
            geometry.Line l = (geometry.Line) shape;
            return "Line(" + l.getStartPoint().getX() + "," + l.getStartPoint().getY() + "," + l.getEndPoint().getX() + "," + l.getEndPoint().getY() + "," + l.getColor().getRGB() + ")";
        } else if (shape instanceof geometry.Rectangle) {
            geometry.Rectangle r = (geometry.Rectangle) shape;
            return "Rectangle(" + r.getUpperLeftPoint().getX() + "," + r.getUpperLeftPoint().getY() + "," + r.getWidth() + "," + r.getHeight() + "," + r.getBorderColor().getRGB() + "," + r.getInnerColor().getRGB() + ")";
        } else if (shape instanceof geometry.Donut) {
            geometry.Donut d = (geometry.Donut) shape;
            return "Donut(" + d.getCenter().getX() + "," + d.getCenter().getY() + "," + d.getRadius() + "," + d.getInnerRadius() + "," + d.getBorderColor().getRGB() + "," + d.getInnerColor().getRGB() + ")";
        } else if (shape instanceof geometry.Circle) {
            geometry.Circle c = (geometry.Circle) shape;
            return "Circle(" + c.getCenter().getX() + "," + c.getCenter().getY() + "," + c.getRadius() + "," + c.getBorderColor().getRGB() + "," + c.getInnerColor().getRGB() + ")";
        } else if (shape instanceof geometry.HexagonAdapter) {
            geometry.HexagonAdapter h = (geometry.HexagonAdapter) shape;
            return "Hexagon(" + h.getCenter().getX() + "," + h.getCenter().getY() + "," + h.getRadius() + "," + h.getEdgeColor().getRGB() + "," + h.getInnerColor().getRGB() + ")";
        }
        return "";
    }
}
