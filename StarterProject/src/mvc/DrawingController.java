package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import dialog.CircleDialog;
import dialog.DonutDialog;
import dialog.LineDialog;
import dialog.PointDialog;
import dialog.RectangleDialog;
import dialog.HexagonDialog;
import geometry.HexagonAdapter;

public class DrawingController {
	private final DrawingModel model;
	private final DrawingFrame frame;
	private final DrawingView view;

	// Transient state for drawing lines
	private Point startPoint;
	private Point endPoint;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		this.view = frame.getView();

		initListeners();
	}

	private void initListeners() {
		// Mouse clicks on canvas
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleCanvasClicked(e);
			}
		});

		// Controls (Modify / Delete buttons)
		frame.getBtnModify().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleModify();
			}
		});

		frame.getBtnDelete().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleDelete();
			}
		});

		// Tool switching: reset line drawing coordinates
		ActionListener toolSwitchListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startPoint = null;
				endPoint = null;
			}
		};
		frame.getTglBtnPoint().addActionListener(toolSwitchListener);
		frame.getTglBtnLine().addActionListener(toolSwitchListener);
		frame.getTglBtnRectangle().addActionListener(toolSwitchListener);
		frame.getTglBtnCircle().addActionListener(toolSwitchListener);
		frame.getTglBtnDonut().addActionListener(toolSwitchListener);
		frame.getTglBtnHexagon().addActionListener(toolSwitchListener);
		frame.getTglBtnSelect().addActionListener(toolSwitchListener);
	}

	private void handleCanvasClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (frame.getTglBtnPoint().isSelected()) {
			PointDialog dialog = new PointDialog(frame, x, y);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				model.add(dialog.getPoint());
				view.repaint();
			}
		} else if (frame.getTglBtnLine().isSelected()) {
			if (startPoint == null) {
				startPoint = new Point(x, y);
			} else if (endPoint == null) {
				endPoint = new Point(x, y);
			}

			if (startPoint != null && endPoint != null) {
				LineDialog dialog = new LineDialog(frame, startPoint, endPoint);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					model.add(dialog.getLine());
					view.repaint();
				}
				startPoint = null;
				endPoint = null;
			}
		} else if (frame.getTglBtnRectangle().isSelected()) {
			Point point = new Point(x, y);
			RectangleDialog dialog = new RectangleDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				model.add(dialog.getRectangle());
				view.repaint();
			}
		} else if (frame.getTglBtnCircle().isSelected()) {
			Point point = new Point(x, y);
			CircleDialog dialog = new CircleDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				model.add(dialog.getCircle());
				view.repaint();
			}
		} else if (frame.getTglBtnDonut().isSelected()) {
			Point point = new Point(x, y);
			DonutDialog dialog = new DonutDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				model.add(dialog.getDonut());
				view.repaint();
			}
		} else if (frame.getTglBtnHexagon().isSelected()) {
			Point point = new Point(x, y);
			HexagonDialog dialog = new HexagonDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				model.add(dialog.getHexagon());
				view.repaint();
			}
		} else if (frame.getTglBtnSelect().isSelected()) {
			model.setSelectedShape(null);
			for (Shape shape : model.getShapes()) {
				shape.setSelected(false);
				if (shape.contains(x, y)) {
					model.setSelectedShape(shape);
				}
			}
			if (model.getSelectedShape() != null) {
				model.getSelectedShape().setSelected(true);
			}
			view.repaint();
		}
	}

	private void handleModify() {
		Shape selected = model.getSelectedShape();
		if (selected != null) {
			selected.setSelected(false);
			frame.getTglBtnSelect().setSelected(false);

			if (selected instanceof Point) {
				Point p = (Point) selected;
				PointDialog dialog = new PointDialog(frame, p);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					p.setX(dialog.getPoint().getX());
					p.setY(dialog.getPoint().getY());
					p.setColor(dialog.getPoint().getColor());
				}
			} else if (selected instanceof Line) {
				Line l = (Line) selected;
				LineDialog dialog = new LineDialog(frame, l);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					l.setStartPoint(dialog.getLine().getStartPoint());
					l.setEndPoint(dialog.getLine().getEndPoint());
					l.setColor(dialog.getLine().getColor());
				}
			} else if (selected instanceof Rectangle) {
				Rectangle r = (Rectangle) selected;
				RectangleDialog dialog = new RectangleDialog(frame, r);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					r.setUpperLeftPoint(dialog.getRectangle().getUpperLeftPoint());
					r.setWidth(dialog.getRectangle().getWidth());
					r.setHeight(dialog.getRectangle().getHeight());
					r.setInnerColor(dialog.getRectangle().getInnerColor());
					r.setBorderColor(dialog.getRectangle().getBorderColor());
				}
			} else if (selected instanceof Donut) {
				Donut d = (Donut) selected;
				DonutDialog dialog = new DonutDialog(frame, d);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					try {
						d.setCenter(dialog.getDonut().getCenter());
						d.setRadius(dialog.getDonut().getRadius());
						d.setInnerRadius(dialog.getDonut().getInnerRadius());
						d.setInnerColor(dialog.getDonut().getInnerColor());
						d.setBorderColor(dialog.getDonut().getBorderColor());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else if (selected instanceof Circle) {
				Circle c = (Circle) selected;
				CircleDialog dialog = new CircleDialog(frame, c);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					try {
						c.setCenter(dialog.getCircle().getCenter());
						c.setRadius(dialog.getCircle().getRadius());
						c.setInnerColor(dialog.getCircle().getInnerColor());
						c.setBorderColor(dialog.getCircle().getBorderColor());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else if (selected instanceof HexagonAdapter) {
				HexagonAdapter h = (HexagonAdapter) selected;
				HexagonDialog dialog = new HexagonDialog(frame, h);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					try {
						h.setCenter(dialog.getHexagon().getCenter());
						h.setRadius(dialog.getHexagon().getRadius());
						h.setInnerColor(dialog.getHexagon().getInnerColor());
						h.setEdgeColor(dialog.getHexagon().getEdgeColor());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			model.setSelectedShape(null);
			view.repaint();
		} else {
			JOptionPane.showMessageDialog(frame, "Please select what you want to modify!", "Error",
					JOptionPane.ERROR_MESSAGE);
			frame.getTglBtnSelect().setSelected(true);
		}
	}

	private void handleDelete() {
		Shape selected = model.getSelectedShape();
		if (selected != null) {
			int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this shape?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				model.remove(selected);
				model.setSelectedShape(null);
				view.repaint();
			}
			frame.getTglBtnSelect().setSelected(false);
		} else {
			JOptionPane.showMessageDialog(frame, "Please select what you want to delete!", "Error",
					JOptionPane.ERROR_MESSAGE);
			frame.getTglBtnSelect().setSelected(true);
		}
	}
}
