package mvc;

import java.util.ArrayList;
import java.util.List;
import geometry.Shape;

public class DrawingModel {
	private final List<Shape> shapes = new ArrayList<>();
	private Shape selectedShape;

	public List<Shape> getShapes() {
		return shapes;
	}

	public void add(Shape shape) {
		shapes.add(shape);
	}

	public void remove(Shape shape) {
		shapes.remove(shape);
		if (selectedShape == shape) {
			selectedShape = null;
		}
	}

	public Shape getSelectedShape() {
		return selectedShape;
	}

	public void setSelectedShape(Shape selectedShape) {
		this.selectedShape = selectedShape;
	}
}
