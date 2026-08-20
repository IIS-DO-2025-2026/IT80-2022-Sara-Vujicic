package mvc;

import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
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
import strategy.*;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Files;

public class DrawingController {
	private final DrawingModel model;
	private final DrawingFrame frame;
	private final DrawingView view;

	private Point startPoint;
	private Point endPoint;

	private final PropertyChangeSupport propertyChangeSupport;
	private final java.util.Stack<Command> undoStack = new java.util.Stack<>();
	private final java.util.Stack<Command> redoStack = new java.util.Stack<>();

	private List<String> logLines = new ArrayList<>();
	private int logLineIndex = 0;

	public void executeCommand(Command cmd) {
		cmd.execute();
		undoStack.push(cmd);
		redoStack.clear();
		updateUndoRedoButtons();
		updateButtonsState();
		frame.getTxtAreaLog().append(cmd.toString() + "\n");
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
					frame.getTxtAreaLog().append("Undo:" + cmd.toString() + "\n");
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
					frame.getTxtAreaLog().append("Redo:" + cmd.toString() + "\n");
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

		frame.getBtnSave().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save File");
				fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Serialized Drawing (*.bin)", "bin"));
				fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Log (*.txt)", "txt"));
				if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					String path = file.getAbsolutePath();
					javax.swing.filechooser.FileFilter filter = fileChooser.getFileFilter();
					String description = filter.getDescription();
					
					if (description.contains(".bin") && !path.toLowerCase().endsWith(".bin")) {
						file = new File(path + ".bin");
					} else if (description.contains(".txt") && !path.toLowerCase().endsWith(".txt")) {
						file = new File(path + ".txt");
					}
					
					try {
						FileStrategy strategy;
						if (file.getName().toLowerCase().endsWith(".bin")) {
							strategy = new DrawingFileStrategy();
							strategy.save(model.getShapes(), file);
							JOptionPane.showMessageDialog(frame, "Drawing saved successfully!");
						} else {
							strategy = new LogFileStrategy();
							strategy.save(frame.getTxtAreaLog().getText(), file);
							JOptionPane.showMessageDialog(frame, "Log saved successfully!");
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		frame.getBtnLoad().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Open File");
				fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Serialized Drawing (*.bin)", "bin"));
				fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Log (*.txt)", "txt"));
				if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					
					// Detect file type using Java Serialization magic bytes
					boolean isSerialized = false;
					try (InputStream is = new FileInputStream(file)) {
						int b1 = is.read();
						int b2 = is.read();
						isSerialized = (b1 == 0xAC && b2 == 0xED);
					} catch (Exception ex) {
						// Ignore, default to text log load
					}
					
					if (isSerialized) {
						try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
							List<Shape> shapes = (List<Shape>) ois.readObject();
							model.getShapes().clear();
							for (Shape s : shapes) {
								model.add(s);
							}
							undoStack.clear();
							redoStack.clear();
							updateUndoRedoButtons();
							updateButtonsState();
							frame.getTxtAreaLog().setText("Drawing loaded successfully.\n");
							frame.getBtnNextCommand().setEnabled(false);
							view.repaint();
							JOptionPane.showMessageDialog(frame, "Drawing loaded successfully!");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(frame, "Error loading drawing: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						try {
							logLines = Files.readAllLines(file.toPath());
							logLineIndex = 0;
							model.getShapes().clear();
							undoStack.clear();
							redoStack.clear();
							updateUndoRedoButtons();
							updateButtonsState();
							frame.getTxtAreaLog().setText("--- Log Loaded. Click 'Next Command' to step through ---\n");
							frame.getBtnNextCommand().setEnabled(logLineIndex < logLines.size());
							view.repaint();
							JOptionPane.showMessageDialog(frame, "Log loaded successfully! Use 'Next Command' to replay.");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(frame, "Error loading log: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});

		frame.getBtnNextCommand().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (logLineIndex < logLines.size()) {
					String line = logLines.get(logLineIndex).trim();
					logLineIndex++;
					
					if (line.isEmpty() || line.startsWith("---")) {
						frame.getBtnNextCommand().setEnabled(logLineIndex < logLines.size());
						return;
					}
					
					try {
						if (line.startsWith("Undo:")) {
							if (!undoStack.isEmpty()) {
								Command cmd = undoStack.pop();
								cmd.unexecute();
								redoStack.push(cmd);
								updateUndoRedoButtons();
								updateButtonsState();
								frame.getTxtAreaLog().append(line + "\n");
							}
						} else if (line.startsWith("Redo:")) {
							if (!redoStack.isEmpty()) {
								Command cmd = redoStack.pop();
								cmd.execute();
								undoStack.push(cmd);
								updateUndoRedoButtons();
								updateButtonsState();
								frame.getTxtAreaLog().append(line + "\n");
							}
						} else {
							Command cmd = parseCommandLine(line);
							if (cmd != null) {
								cmd.execute();
								undoStack.push(cmd);
								redoStack.clear();
								updateUndoRedoButtons();
								updateButtonsState();
								frame.getTxtAreaLog().append(line + "\n");
							} else {
								frame.getTxtAreaLog().append("Skipped/Failed to parse: " + line + "\n");
							}
						}
					} catch (Exception ex) {
						frame.getTxtAreaLog().append("Error executing command line: " + line + " (" + ex.getMessage() + ")\n");
					}
					
					frame.getBtnNextCommand().setEnabled(logLineIndex < logLines.size());
					view.repaint();
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
					newState = dialog.getPoint();
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Line) {
				Line l = (Line) newState;
				LineDialog dialog = new LineDialog(frame, l);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					newState = dialog.getLine();
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Rectangle) {
				Rectangle r = (Rectangle) newState;
				RectangleDialog dialog = new RectangleDialog(frame, r);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					newState = dialog.getRectangle();
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Donut) {
				Donut d = (Donut) newState;
				DonutDialog dialog = new DonutDialog(frame, d);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					newState = dialog.getDonut();
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof Circle) {
				Circle c = (Circle) newState;
				CircleDialog dialog = new CircleDialog(frame, c);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					newState = dialog.getCircle();
					CmdUpdateShape cmd = new CmdUpdateShape(selected, oldState, newState);
					executeCommand(cmd);
				}
			} else if (selected instanceof HexagonAdapter) {
				HexagonAdapter h = (HexagonAdapter) newState;
				HexagonDialog dialog = new HexagonDialog(frame, h);
				dialog.setVisible(true);
				if (dialog.isConfirmed()) {
					newState = dialog.getHexagon();
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

	public Shape parseShape(String str) {
		if (str.startsWith("Point(")) {
			String data = str.substring(6, str.length() - 1);
			String[] parts = data.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			Color c = new Color(Integer.parseInt(parts[2]));
			Point p = new Point(x, y);
			p.setColor(c);
			return p;
		} else if (str.startsWith("Line(")) {
			String data = str.substring(5, str.length() - 1);
			String[] parts = data.split(",");
			int sx = Integer.parseInt(parts[0]);
			int sy = Integer.parseInt(parts[1]);
			int ex = Integer.parseInt(parts[2]);
			int ey = Integer.parseInt(parts[3]);
			Color c = new Color(Integer.parseInt(parts[4]));
			Line l = new Line(new Point(sx, sy), new Point(ex, ey));
			l.setColor(c);
			return l;
		} else if (str.startsWith("Rectangle(")) {
			String data = str.substring(10, str.length() - 1);
			String[] parts = data.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int w = Integer.parseInt(parts[2]);
			int h = Integer.parseInt(parts[3]);
			Color border = new Color(Integer.parseInt(parts[4]));
			Color inner = new Color(Integer.parseInt(parts[5]));
			Rectangle r = new Rectangle(new Point(x, y), w, h);
			r.setBorderColor(border);
			r.setInnerColor(inner);
			return r;
		} else if (str.startsWith("Donut(")) {
			String data = str.substring(6, str.length() - 1);
			String[] parts = data.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int r = Integer.parseInt(parts[2]);
			int ir = Integer.parseInt(parts[3]);
			Color border = new Color(Integer.parseInt(parts[4]));
			Color inner = new Color(Integer.parseInt(parts[5]));
			Donut d = new Donut(new Point(x, y), r, ir);
			d.setBorderColor(border);
			d.setInnerColor(inner);
			return d;
		} else if (str.startsWith("Circle(")) {
			String data = str.substring(7, str.length() - 1);
			String[] parts = data.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int r = Integer.parseInt(parts[2]);
			Color border = new Color(Integer.parseInt(parts[3]));
			Color inner = new Color(Integer.parseInt(parts[4]));
			Circle c = new Circle(new Point(x, y), r);
			c.setBorderColor(border);
			c.setInnerColor(inner);
			return c;
		} else if (str.startsWith("Hexagon(")) {
			String data = str.substring(8, str.length() - 1);
			String[] parts = data.split(",");
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int r = Integer.parseInt(parts[2]);
			Color border = new Color(Integer.parseInt(parts[3]));
			Color inner = new Color(Integer.parseInt(parts[4]));
			HexagonAdapter h = new HexagonAdapter(new Point(x, y), r);
			h.setEdgeColor(border);
			h.setInnerColor(inner);
			return h;
		}
		return null;
	}

	public Shape findMatchingShape(Shape pattern) {
		for (Shape s : model.getShapes()) {
			if (s.getClass().equals(pattern.getClass())) {
				if (s instanceof Point && pattern instanceof Point) {
					Point p1 = (Point) s;
					Point p2 = (Point) pattern;
					if (p1.getX() == p2.getX() && p1.getY() == p2.getY() && p1.getColor().getRGB() == p2.getColor().getRGB()) {
						return s;
					}
				} else if (s instanceof Line && pattern instanceof Line) {
					Line l1 = (Line) s;
					Line l2 = (Line) pattern;
					if (l1.getStartPoint().getX() == l2.getStartPoint().getX() && l1.getStartPoint().getY() == l2.getStartPoint().getY() &&
						l1.getEndPoint().getX() == l2.getEndPoint().getX() && l1.getEndPoint().getY() == l2.getEndPoint().getY() &&
						l1.getColor().getRGB() == l2.getColor().getRGB()) {
						return s;
					}
				} else if (s instanceof Rectangle && pattern instanceof Rectangle) {
					Rectangle r1 = (Rectangle) s;
					Rectangle r2 = (Rectangle) pattern;
					if (r1.getUpperLeftPoint().getX() == r2.getUpperLeftPoint().getX() && r1.getUpperLeftPoint().getY() == r2.getUpperLeftPoint().getY() &&
						r1.getWidth() == r2.getWidth() && r1.getHeight() == r2.getHeight() &&
						r1.getBorderColor().getRGB() == r2.getBorderColor().getRGB() && r1.getInnerColor().getRGB() == r2.getInnerColor().getRGB()) {
						return s;
					}
				} else if (s instanceof Donut && pattern instanceof Donut) {
					Donut d1 = (Donut) s;
					Donut d2 = (Donut) pattern;
					if (d1.getCenter().getX() == d2.getCenter().getX() && d1.getCenter().getY() == d2.getCenter().getY() &&
						d1.getRadius() == d2.getRadius() && d1.getInnerRadius() == d2.getInnerRadius() &&
						d1.getBorderColor().getRGB() == d2.getBorderColor().getRGB() && d1.getInnerColor().getRGB() == d2.getInnerColor().getRGB()) {
						return s;
					}
				} else if (s instanceof Circle && pattern instanceof Circle) {
					Circle c1 = (Circle) s;
					Circle c2 = (Circle) pattern;
					if (c1.getCenter().getX() == c2.getCenter().getX() && c1.getCenter().getY() == c2.getCenter().getY() &&
						c1.getRadius() == c2.getRadius() &&
						c1.getBorderColor().getRGB() == c2.getBorderColor().getRGB() && c1.getInnerColor().getRGB() == c2.getInnerColor().getRGB()) {
						return s;
					}
				} else if (s instanceof HexagonAdapter && pattern instanceof HexagonAdapter) {
					HexagonAdapter h1 = (HexagonAdapter) s;
					HexagonAdapter h2 = (HexagonAdapter) pattern;
					if (h1.getCenter().getX() == h2.getCenter().getX() && h1.getCenter().getY() == h2.getCenter().getY() &&
						h1.getRadius() == h2.getRadius() &&
						h1.getEdgeColor().getRGB() == h2.getEdgeColor().getRGB() && h1.getInnerColor().getRGB() == h2.getInnerColor().getRGB()) {
						return s;
					}
				}
			}
		}
		return null;
	}

	public Command parseCommandLine(String line) {
		if (line.startsWith("Added:")) {
			Shape shape = parseShape(line.substring(6));
			if (shape != null) {
				return new CmdAddShape(model, shape);
			}
		} else if (line.startsWith("Removed:")) {
			Shape pattern = parseShape(line.substring(8));
			if (pattern != null) {
				Shape target = findMatchingShape(pattern);
				if (target != null) {
					return new CmdRemoveShape(model, target);
				}
			}
		} else if (line.startsWith("Selected:")) {
			Shape pattern = parseShape(line.substring(9));
			if (pattern != null) {
				Shape target = findMatchingShape(pattern);
				if (target != null) {
					return new CmdSelectShape(model, target);
				}
			}
		} else if (line.startsWith("Deselected:")) {
			Shape pattern = parseShape(line.substring(11));
			if (pattern != null) {
				Shape target = findMatchingShape(pattern);
				if (target != null) {
					return new CmdDeselectShape(model, target);
				}
			}
		} else if (line.startsWith("Updated:")) {
			String data = line.substring(8);
			String[] parts = data.split("->");
			Shape patternOld = parseShape(parts[0]);
			Shape newState = parseShape(parts[1]);
			if (patternOld != null && newState != null) {
				Shape target = findMatchingShape(patternOld);
				if (target != null) {
					return new CmdUpdateShape(target, patternOld, newState);
				}
			}
		} else if (line.startsWith("MovedToFront:")) {
			Shape pattern = parseShape(line.substring(13));
			if (pattern != null) {
				Shape target = findMatchingShape(pattern);
				if (target != null) {
					return new CmdToFront(model, target);
				}
			}
		} else if (line.startsWith("MovedToBack:")) {
			Shape pattern = parseShape(line.substring(12));
			if (pattern != null) {
				Shape target = findMatchingShape(pattern);
				if (target != null) {
					return new CmdToBack(model, target);
				}
			}
		} else if (line.startsWith("BroughtToFront:")) {
			Shape pattern = parseShape(line.substring(15));
			if (pattern != null) {
				Shape target = findMatchingShape(pattern);
				if (target != null) {
					return new CmdBringToFront(model, target);
				}
			}
		} else if (line.startsWith("BroughtToBack:")) {
			Shape pattern = parseShape(line.substring(14));
			if (pattern != null) {
				Shape target = findMatchingShape(pattern);
				if (target != null) {
					return new CmdBringToBack(model, target);
				}
			}
		}
		return null;
	}
}
