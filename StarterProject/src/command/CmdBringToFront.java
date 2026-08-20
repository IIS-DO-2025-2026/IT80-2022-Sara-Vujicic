package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdBringToFront implements Command {
    private final DrawingModel model;
    private final Shape shape;
    private int originalIndex = -1;

    public CmdBringToFront(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        int index = model.getShapes().indexOf(shape);
        if (index >= 0) {
            originalIndex = index;
            model.getShapes().remove(index);
            model.getShapes().add(shape);
        }
    }

    @Override
    public void unexecute() {
        if (originalIndex >= 0) {
            model.getShapes().remove(model.getShapes().size() - 1);
            model.getShapes().add(originalIndex, shape);
        }
    }

    @Override
    public String toString() {
        return "BroughtToFront:" + Command.getShapeLogString(shape);
    }
}
