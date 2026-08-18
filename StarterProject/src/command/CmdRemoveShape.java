package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdRemoveShape implements Command {
    private final DrawingModel model;
    private final Shape shape;

    public CmdRemoveShape(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.remove(shape);
    }

    @Override
    public void unexecute() {
        model.add(shape);
    }

    @Override
    public String toString() {
        return "Removed " + shape.getClass().getSimpleName() + " at " + shape.toString();
    }
}
