package command;

import mvc.DrawingModel;
import geometry.Shape;

public class CmdDeselectShape implements Command {
    private final DrawingModel model;
    private final Shape shape;

    public CmdDeselectShape(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        shape.setSelected(false);
        if (model.getSelectedShape() == shape) {
            model.setSelectedShape(null);
        }
    }

    @Override
    public void unexecute() {
        shape.setSelected(true);
        model.setSelectedShape(shape);
    }

    @Override
    public String toString() {
        return "Deselected:" + Command.getShapeLogString(shape);
    }
}
