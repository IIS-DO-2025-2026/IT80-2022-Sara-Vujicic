package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdSelectShape implements Command {
    private final DrawingModel model;
    private final Shape shape;

    public CmdSelectShape(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        shape.setSelected(true);
        model.setSelectedShape(shape);
    }

    @Override
    public void unexecute() {
        shape.setSelected(false);
        if (model.getSelectedShape() == shape) {
            model.setSelectedShape(null);
        }
    }

    @Override
    public String toString() {
        return "Selected " + shape.getClass().getSimpleName() + " at " + shape.toString();
    }
}
