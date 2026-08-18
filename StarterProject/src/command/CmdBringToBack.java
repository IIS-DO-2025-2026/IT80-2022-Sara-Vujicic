package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdBringToBack implements Command {
    private final DrawingModel model;
    private final Shape shape;
    private int originalIndex = -1;

    public CmdBringToBack(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        int index = model.getShapes().indexOf(shape);
        if (index >= 0) {
            originalIndex = index;
            model.getShapes().remove(index);
            model.getShapes().add(0, shape);
        }
    }

    @Override
    public void unexecute() {
        if (originalIndex >= 0) {
            model.getShapes().remove(0);
            model.getShapes().add(originalIndex, shape);
        }
    }

    @Override
    public String toString() {
        return "Brought " + shape.getClass().getSimpleName() + " to Back (very bottom)";
    }
}
