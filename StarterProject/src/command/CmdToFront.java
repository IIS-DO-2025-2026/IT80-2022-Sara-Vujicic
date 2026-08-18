package command;

import mvc.DrawingModel;
import geometry.Shape;
import java.util.Collections;

public class CmdToFront implements Command {
    private final DrawingModel model;
    private final Shape shape;
    private int originalIndex = -1;

    public CmdToFront(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        int index = model.getShapes().indexOf(shape);
        if (index >= 0 && index < model.getShapes().size() - 1) {
            originalIndex = index;
            Collections.swap(model.getShapes(), index, index + 1);
        }
    }

    @Override
    public void unexecute() {
        if (originalIndex >= 0) {
            Collections.swap(model.getShapes(), originalIndex + 1, originalIndex);
        }
    }

    @Override
    public String toString() {
        return "Moved " + shape.getClass().getSimpleName() + " to Front (forward one position)";
    }
}
