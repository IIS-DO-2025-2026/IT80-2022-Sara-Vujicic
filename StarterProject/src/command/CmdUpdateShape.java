package command;

import geometry.*;
import java.awt.Color;

public class CmdUpdateShape implements Command {
    private final Shape target;
    private final Shape oldState;
    private final Shape newState;

    public CmdUpdateShape(Shape target, Shape oldState, Shape newState) {
        this.target = target;
        this.oldState = oldState;
        this.newState = newState;
    }

    @Override
    public void execute() {
        copyState(newState, target);
    }

    @Override
    public void unexecute() {
        copyState(oldState, target);
    }

    private void copyState(Shape from, Shape to) {
        if (from instanceof Point && to instanceof Point) {
            Point f = (Point) from;
            Point t = (Point) to;
            t.setX(f.getX());
            t.setY(f.getY());
            t.setColor(f.getColor());
        } else if (from instanceof Line && to instanceof Line) {
            Line f = (Line) from;
            Line t = (Line) to;
            t.setStartPoint(f.getStartPoint().clone());
            t.setEndPoint(f.getEndPoint().clone());
            t.setColor(f.getColor());
        } else if (from instanceof Rectangle && to instanceof Rectangle) {
            Rectangle f = (Rectangle) from;
            Rectangle t = (Rectangle) to;
            t.setUpperLeftPoint(f.getUpperLeftPoint().clone());
            t.setWidth(f.getWidth());
            t.setHeight(f.getHeight());
            t.setBorderColor(f.getBorderColor());
            t.setInnerColor(f.getInnerColor());
        } else if (from instanceof Donut && to instanceof Donut) {
            Donut f = (Donut) from;
            Donut t = (Donut) to;
            t.setCenter(f.getCenter().clone());
            try {
                t.setRadius(f.getRadius());
            } catch (Exception e) {}
            t.setInnerRadius(f.getInnerRadius());
            t.setBorderColor(f.getBorderColor());
            t.setInnerColor(f.getInnerColor());
        } else if (from instanceof Circle && to instanceof Circle) {
            Circle f = (Circle) from;
            Circle t = (Circle) to;
            t.setCenter(f.getCenter().clone());
            try {
                t.setRadius(f.getRadius());
            } catch (Exception e) {}
            t.setBorderColor(f.getBorderColor());
            t.setInnerColor(f.getInnerColor());
        } else if (from instanceof HexagonAdapter && to instanceof HexagonAdapter) {
            HexagonAdapter f = (HexagonAdapter) from;
            HexagonAdapter t = (HexagonAdapter) to;
            t.setCenter(f.getCenter().clone());
            t.setRadius(f.getRadius());
            t.setEdgeColor(f.getEdgeColor());
            t.setInnerColor(f.getInnerColor());
        }
        to.setSelected(from.isSelected());
    }

    @Override
    public String toString() {
        return "Updated:" + Command.getShapeLogString(oldState) + "->" + Command.getShapeLogString(newState);
    }
}
