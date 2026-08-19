package mvc;

import java.util.List;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
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
import command.*;
import java.beans.PropertyChangeSupport;

public class DrawingController {
	private final DrawingModel model;
	private final DrawingFrame frame;
	private final DrawingView view;

	
	private Point startPoint;
	private Point endPoint;

	private final PropertyChangeSupport propertyChangeSupport;
	private final java.util.Stack<Command> undoStack = new java.util.Stack<>();
	private final java.util.Stack<Command> redoStack = new java.util.Stack<>();

	public void executeCommand(Command cmd) {
		cmd.execute();
		undoStack.push(cmd);
		redoStack.clear();
		updateUndoRedoButtons();
		updateButtonsState();
		view.repaint();
	}

	private void updateUndoRedoButtons() {
		frame.getBtnUndo().setEnabled(!undoStack.isEmpty());
		frame.getBtnRedo().setEnabled(!redoStack.isEmpty());
	}

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		this.view = frame.getView();
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.propertyChangeSupport.addPropertyChangeListener(frame);

		initListeners();
		updateUndoRedoButtons();
		updateButtonsState();
	}

	private void initListeners() {
		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleCanvasClicked(e);
			}
		});

		
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

		frame.getBtnActiveEdgeColor().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(frame, "Choose Edge Color", frame.getActiveEdgeColor());
				if (newColor != null) {
					frame.setActiveEdgeColor(newColor);
					Shape selected = model.getSelectedShape();
					if (selected != null) {
						updateShapeEdgeColor(selected, newColor);
						view.repaint();
					}
				}
			}
		});

		frame.getBtnActiveInnerColor().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(frame, "Choose Inner Color", frame.getActiveInnerColor());
				if (newColor != null) {
					frame.setActiveInnerColor(newColor);
					Shape selected = model.getSelectedShape();
					if (selected != null) {
						updateShapeInnerColor(selected, newColor);
						view.repaint();
					}
				}
			}
		});

		
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

		frame.getBtnUndo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!undoStack.isEmpty()) {
					Command cmd = undoStack.pop();
					cmd.unexecute();
					redoStack.push(cmd);
					updateUndoRedoButtons();
					updateButtonsState();
					view.repaint();
				}
			}
		});

		frame.getBtnRedo().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!redoStack.isEmpty()) {
					Command cmd = redoStack.pop();
					cmd.execute();
					undoStack.push(cmd);
					updateUndoRedoButtons();
					updateButtonsState();
					view.repaint();
				}
			}
		});

		frame.getBtnToFront().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Shape selected = getSingleSelectedShape();
				if (selected != null) {
					int index = model.getShapes().indexOf(selected);
					if (index >= 0 && index < model.getShapes().size() - 1) {
						executeCommand(new CmdToFront(model, selected));
					} else {
						JOptionPane.showMessageDialog(frame, "The shape is already at the front!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select exactly one shape!");
				}
			}
		});

		frame.getBtnToBack().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Shape selected = getSingleSelectedShape();
				if (selected != null) {
					int index = model.getShapes().indexOf(selected);
					if (index > 0) {
						executeCommand(new CmdToBack(model, selected));
					} else {
						JOptionPane.showMessageDialog(frame, "The shape is already at the back!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select exactly one shape!");
				}
			}
		});

		frame.getBtnBringToFront().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Shape selected = getSingleSelectedShape();
				if (selected != null) {
					int index = model.getShapes().indexOf(selected);
					if (index >= 0 && index < model.getShapes().size() - 1) {
						executeCommand(new CmdBringToFront(model, selected));
					} else {
						JOptionPane.showMessageDialog(frame, "The shape is already at the front!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select exactly one shape!");
				}
			}
		});

		frame.getBtnBringToBack().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Shape selected = getSingleSelectedShape();
				if (selected != null) {
					int index = model.getShapes().indexOf(selected);
					if (index > 0) {
						executeCommand(new CmdBringToBack(model, selected));
					} else {
						JOptionPane.showMessageDialog(frame, "The shape is already at the back!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, "Please select exactly one shape!");
				}
			}
		});
	}

	private void handleCanvasClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (frame.getTglBtnPoint().isSelected()) {
			PointDialog dialog = new PointDialog(frame, x, y);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				Point p = dialog.getPoint();
				executeCommand(new CmdAddShape(model, p));
				frame.setActiveEdgeColor(p.getColor());
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
					Line l = dialog.getLine();
					executeCommand(new CmdAddShape(model, l));
					frame.setActiveEdgeColor(l.getColor());
				}
				startPoint = null;
				endPoint = null;
			}
		} else if (frame.getTglBtnRectangle().isSelected()) {
			Point point = new Point(x, y);
			RectangleDialog dialog = new RectangleDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				Rectangle r = dialog.getRectangle();
				executeCommand(new CmdAddShape(model, r));
				frame.setActiveEdgeColor(r.getBorderColor());
				frame.setActiveInnerColor(r.getInnerColor());
			}
		} else if (frame.getTglBtnCircle().isSelected()) {
			Point point = new Point(x, y);
			CircleDialog dialog = new CircleDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				Circle c = dialog.getCircle();
				executeCommand(new CmdAddShape(model, c));
				frame.setActiveEdgeColor(c.getBorderColor());
				frame.setActiveInnerColor(c.getInnerColor());
			}
		} else if (frame.getTglBtnDonut().isSelected()) {
			Point point = new Point(x, y);
			DonutDialog dialog = new DonutDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				Donut d = dialog.getDonut();
				executeCommand(new CmdAddShape(model, d));
				frame.setActiveEdgeColor(d.getEdgeColor());
				frame.setActiveInnerColor(d.getInnerColor());
			}
		} else if (frame.getTglBtnHexagon().isSelected()) {
			Point point = new Point(x, y);
			HexagonDialog dialog = new HexagonDialog(frame, point);
			dialog.setVisible(true);
			if (dialog.isConfirmed()) {
				HexagonAdapter h = dialog.getHexagon();
				executeCommand(new CmdAddShape(model, h));
				frame.setActiveEdgeColor(h.getEdgeColor());
				frame.setActiveInnerColor(h.getInnerColor());
			}
		} else if (frame.getTglBtnSelect().isSelected()) {
			Shape clickedShape = null;
			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				Shape shape = model.getShapes().get(i);
				if (shape.contains(x, y)) {
					clickedShape = shape;
					break;
				}
			}

			if (clickedShape != null) {
				if (clickedShape.isSelected()) {
					executeCommand(new CmdDeselectShape(model, clickedShape));
				} else {
					executeCommand(new CmdSelectShape(model, clickedShape));
				}
			} else {
				List<Shape> toDeselect = new java.util.ArrayList<>();
				for (Shape s : model.getShapes()) {
					if (s.isSelected()) {
						toDeselect.add(s);
					}
				}
				for (Shape s : toDeselect) {
					executeCommand(new CmdDeselectShape(model, s));
				}
			}
		}
	}

	private void handleModify() {
		Shape selected = getSingleSelectedShape();
		if (selected != null) {
			Shape oldState = selected.clone();
			Shape newState = selected.clone();

			if (selected instanceof Point) {
				Point p = (Point) newState;
				PointDialog dialog = new PointDialog(frame, p);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Line) {
				Line l = (Line) newState;
				LineDialog dialog = new LineDialog(frame, l);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Rectangle) {
				Rectangle r = (Rectangle) newState;
				RectangleDialog dialog = new RectangleDialog(frame, r);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Donut) {
				Donut d = (Donut) newState;
				DonutDialog dialog = new DonutDialog(frame, d);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Circle) {
				Circle c = (Circle) newState;
				CircleDialog dialog = new CircleDialog(frame, c);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof HexagonAdapter) {
				HexagonAdapter h = (HexagonAdapter) newState;
				HexagonDialog dialog = new HexagonDialog(frame, h);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			}
			
			selected.setSelected(true);
			model.setSelectedShape(selected);
			view.repaint();
		} else {
			JOptionPane.showMessageDialog(frame, "Please select exactly one shape to modify!", "Error",
					JOptionPane.ERROR_MESSAGE);
			frame.getTglBtnSelect().setSelected(true);
		}
	}

	private void handleDelete() {
		List<Shape> selected = new java.util.ArrayList<>();
		for (Shape s : model.getShapes()) {
			if (s.isSelected()) {
				selected.add(s);
			}
		}
		if (!selected.isEmpty()) {
			int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the selected shape(s)?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				for (Shape s : selected) {
					executeCommand(new CmdRemoveShape(model, s));
				}
			}
			frame.getTglBtnSelect().setSelected(false);
		} else {
			JOptionPane.showMessageDialog(frame, "Please select what you want to delete!", "Error",
					JOptionPane.ERROR_MESSAGE);
			frame.getTglBtnSelect().setSelected(true);
		}
	}

	private Shape getSingleSelectedShape() {
		Shape selected = null;
		for (Shape s : model.getShapes()) {
			if (s.isSelected()) {
				if (selected != null) {
					return null;
				}
				selected = s;
			}
		}
		return selected;
	}

	private void updateButtonsState() {
		int count = 0;
		Shape single = null;
		for (Shape s : model.getShapes()) {
			if (s.isSelected()) {
				count++;
				single = s;
			}
		}

		if (count == 1) {
			model.setSelectedShape(single);
		} else {
			model.setSelectedShape(null);
		}

		propertyChangeSupport.firePropertyChange("selectedCount", -1, count);

		boolean toFront = false;
		boolean toBack = false;
		boolean bringToFront = false;
		boolean bringToBack = false;

		if (count == 1 && single != null) {
			int index = model.getShapes().indexOf(single);
			if (index >= 0) {
				toFront = (index < model.getShapes().size() - 1);
				bringToFront = toFront;
				toBack = (index > 0);
				bringToBack = toBack;
			}
		}

		propertyChangeSupport.firePropertyChange("toFrontEnabled", null, toFront);
		propertyChangeSupport.firePropertyChange("toBackEnabled", null, toBack);
		propertyChangeSupport.firePropertyChange("bringToFrontEnabled", null, bringToFront);
		propertyChangeSupport.firePropertyChange("bringToBackEnabled", null, bringToBack);
	}

	private void updateShapeEdgeColor(Shape shape, Color color) {
		if (shape instanceof Point) {
			((Point) shape).setColor(color);
		} else if (shape instanceof Line) {
			((Line) shape).setColor(color);
		} else if (shape instanceof Rectangle) {
			((Rectangle) shape).setBorderColor(color);
		} else if (shape instanceof Donut) {
			((Donut) shape).setEdgeColor(color);
		} else if (shape instanceof Circle) {
			((Circle) shape).setEdgeColor(color);
		} else if (shape instanceof HexagonAdapter) {
			((HexagonAdapter) shape).setEdgeColor(color);
		}
	}

	private void updateShapeInnerColor(Shape shape, Color color) {
		if (shape instanceof Rectangle) {
			((Rectangle) shape).setInnerColor(color);
		} else if (shape instanceof Donut) {
			((Donut) shape).setInnerColor(color);
		} else if (shape instanceof Circle) {
			((Circle) shape).setInnerColor(color);
		} else if (shape instanceof HexagonAdapter) {
			((HexagonAdapter) shape).setInnerColor(color);
		}
	}
}
