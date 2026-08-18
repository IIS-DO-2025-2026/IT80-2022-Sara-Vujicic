package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdAddShape implements Command {
    private final DrawingModel model;
    private final Shape shape;

    public CmdAddShape(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        model.add(shape);
    }

    @Override
    public void unexecute() {
        model.remove(shape);
    }

    @Override
    public String toString() {
        return "Added " + shape.getClass().getSimpleName() + " at " + shape.toString();
    }
}
