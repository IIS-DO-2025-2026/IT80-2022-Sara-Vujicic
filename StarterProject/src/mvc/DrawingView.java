package mvc;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import geometry.Shape;

public class DrawingView extends JPanel {
	private static final long serialVersionUID = 1L;
	private DrawingModel model;

	public DrawingView() {
		setBackground(Color.WHITE);
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (model != null) {
			for (Shape shape : model.getShapes()) {
				shape.draw(g);
			}
		}
	}
}
