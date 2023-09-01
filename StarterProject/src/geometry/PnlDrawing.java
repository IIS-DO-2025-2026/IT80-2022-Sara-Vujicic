package geometry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import dialog.CircleDialog;
import dialog.DonutDialog;
import dialog.LineDialog;
import dialog.PointDialog;
import dialog.RectangleDialog;

public class PnlDrawing extends JPanel implements MouseListener {
	private ArrayList<Shape> allShapesOnPanel;
	private Shape selectedShape;
	private Point startPoint;
	private Point endPoint;
	Shape newAddedShape = null;
	JToggleButton tglBtnPoint;
	JToggleButton tglBtnLine;
	JToggleButton tglBtnRectangle;
	JToggleButton tglBtnCircle;
	JToggleButton tglBtnDonut;
	JToggleButton tglBtnSelect;
	JButton btnModify;
	JButton btnDelete;

	public PnlDrawing() {
		allShapesOnPanel = new ArrayList<>();
		tglBtnPoint = new JToggleButton("Point");
		tglBtnLine = new JToggleButton("Line");
		tglBtnRectangle = new JToggleButton("Rectangle");
		tglBtnCircle = new JToggleButton("Circle");
		tglBtnDonut = new JToggleButton("Donut");
		tglBtnSelect = new JToggleButton("Select");
		btnModify = new JButton("Modify");
		btnDelete = new JButton("Delete");

		add(tglBtnPoint);
		add(tglBtnLine);
		add(tglBtnRectangle);
		add(tglBtnCircle);
		add(tglBtnDonut);
		add(btnDelete);
		add(btnModify);
		add(tglBtnSelect);

		tglBtnPoint.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tglBtnPoint.setSelected(true);
				tglBtnLine.setSelected(false);
				tglBtnRectangle.setSelected(false);
				tglBtnCircle.setSelected(false);
				tglBtnDonut.setSelected(false);

				tglBtnSelect.setSelected(false);

			}
		});

		tglBtnLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tglBtnPoint.setSelected(false);
				tglBtnLine.setSelected(true);
				tglBtnRectangle.setSelected(false);
				tglBtnCircle.setSelected(false);
				tglBtnDonut.setSelected(false);

				tglBtnSelect.setSelected(false);
			}
		});

		tglBtnCircle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tglBtnPoint.setSelected(false);
				tglBtnLine.setSelected(false);
				tglBtnRectangle.setSelected(false);
				tglBtnCircle.setSelected(true);
				tglBtnDonut.setSelected(false);

				tglBtnSelect.setSelected(false);
			}
		});

		tglBtnRectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tglBtnPoint.setSelected(false);
				tglBtnLine.setSelected(false);
				tglBtnRectangle.setSelected(true);
				tglBtnCircle.setSelected(false);
				tglBtnDonut.setSelected(false);

				tglBtnSelect.setSelected(false);
			}
		});

		tglBtnDonut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tglBtnPoint.setSelected(false);
				tglBtnLine.setSelected(false);
				tglBtnRectangle.setSelected(false);
				tglBtnCircle.setSelected(false);
				tglBtnDonut.setSelected(true);

				tglBtnSelect.setSelected(false);
			}
		});

		tglBtnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tglBtnSelect.setSelected(true);

				tglBtnPoint.setSelected(false);
				tglBtnLine.setSelected(false);
				tglBtnRectangle.setSelected(false);
				tglBtnCircle.setSelected(false);
				tglBtnDonut.setSelected(false);

			}
		});

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(tglBtnRectangle);
		btnGroup.add(tglBtnDonut);
		btnGroup.add(tglBtnCircle);
		btnGroup.add(tglBtnLine);
		btnGroup.add(tglBtnPoint);

		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (getSelectedShape() != null) {

					modify();
					selectedShape.setSelected(false);
					tglBtnSelect.setSelected(false);

				} else {
					JOptionPane.showMessageDialog(null, "Please select what you want to modify!", "Error",
							JOptionPane.ERROR_MESSAGE);
					tglBtnSelect.setSelected(true);

				}
				setSelectedShape(null);

			}

		});
		btnGroup.add(btnModify);

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
				tglBtnSelect.setSelected(false);
				setSelectedShape(null);
			}

		});
		btnGroup.add(btnDelete);

		btnGroup.add(tglBtnSelect);

		PnlDrawing tmpDrawing = this;
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Point clickedPoint = new Point(x, y);
				if (getTglBtnPoint().isSelected()) {
					clickedPoint.draw(getGraphics());
					newAddedShape = clickedPoint;
				} else if (getTglBtnLine().isSelected()) {
					if (startPoint == null) {
						startPoint = new Point(x, y);
					} else if (endPoint == null) {
						endPoint = new Point(x, y);
					}

					if (startPoint != null && endPoint != null) {
						Line clickedLine = new Line(startPoint, endPoint);
						clickedLine.draw(getGraphics());
						newAddedShape = clickedLine;
						startPoint = null;
						endPoint = null;
					}
				} else if (getTglBtnRectangle().isSelected()) {
					Point point = new Point(x, y);
					RectangleDialog rectangleDialog = new RectangleDialog(point, tmpDrawing);
				} else if (getTglBtnCircle().isSelected()) {
					Point point = new Point(x, y);
					CircleDialog dialog = new CircleDialog(point, tmpDrawing);
				} else if (getTglBtnDonut().isSelected()) {
					Point point = new Point(x, y);
					DonutDialog donutDialog = new DonutDialog(point, tmpDrawing);
				} else if (getTglBtnSelect().isSelected()) {
					selectedShape = null;
					newAddedShape = null;
					Iterator<Shape> iterator = allShapesOnPanel.iterator();
					while (iterator.hasNext()) {
						Shape shape = iterator.next();
						shape.setSelected(false);
						if (shape.contains(clickedPoint.getX(), clickedPoint.getY()))
							selectedShape = shape;
					}
					if (selectedShape != null)
						selectedShape.setSelected(true);
				}
				if (newAddedShape != null) {
					allShapesOnPanel.add(newAddedShape);
				}
			}
		});

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7293514602652333768L;
	/*
	 * @Override public void paint(Graphics g) { super.paint(g);
	 * 
	 * }
	 */

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void setSelectedShape(Object object) {
		this.selectedShape = (Shape) object;

	}

	public Shape getSelectedShape() {
		return selectedShape;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public ArrayList<Shape> getAllShapesOnPanel() {
		return allShapesOnPanel;
	}

	protected void modify() {
		PnlDrawing tmpDrawing = this;
		Shape selectedShape = (Shape) getSelectedShape();
		if (selectedShape != null) {
			if (selectedShape instanceof Point) {
				Point p = (Point) selectedShape;
				PointDialog dialog = new PointDialog(p, tmpDrawing);
				getAllShapesOnPanel().remove(p);
				tglBtnSelect.setSelected(false);
				repaint();

			} else if (selectedShape instanceof Line) {
				Line line = (Line) selectedShape;
				LineDialog dialog = new LineDialog(line, tmpDrawing);
				getAllShapesOnPanel().remove(selectedShape);
				tglBtnSelect.setSelected(false);
				repaint();

			} else if (selectedShape instanceof Rectangle) {
				Rectangle rect = (Rectangle) selectedShape;
				RectangleDialog dialog = new RectangleDialog(rect, tmpDrawing);
				getAllShapesOnPanel().remove(selectedShape);
				tglBtnSelect.setSelected(false);
				repaint();

			} else if (selectedShape instanceof Donut) {
				Donut donut = (Donut) selectedShape;
				DonutDialog dialog = new DonutDialog(donut, tmpDrawing);
				getAllShapesOnPanel().remove(selectedShape);
				tglBtnSelect.setSelected(false);
				repaint();

			} else if (selectedShape instanceof Circle && (selectedShape instanceof Donut) == false) {
				Circle circle = (Circle) selectedShape;
				CircleDialog dialog = new CircleDialog(circle, tmpDrawing);
				getAllShapesOnPanel().remove(selectedShape);
				tglBtnSelect.setSelected(false);
				repaint();
			}
		}

	}

	protected void delete() {
		Shape selectedShape = (Shape) getSelectedShape();
		if (selectedShape != null) {
			int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this shape?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				getAllShapesOnPanel().remove(selectedShape);
				repaint();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please select what you want to delete!", "Error",
					JOptionPane.ERROR_MESSAGE);
			tglBtnSelect.setSelected(true);
		}
	}

	public JToggleButton getTglBtnPoint() {
		return tglBtnPoint;
	}

	public void setTglBtnPoint(JToggleButton tglBtnPoint) {
		this.tglBtnPoint = tglBtnPoint;
	}

	public JToggleButton getTglBtnLine() {
		return tglBtnLine;
	}

	public void setTglBtnLine(JToggleButton tglBtnLine) {
		this.tglBtnLine = tglBtnLine;
	}

	public JToggleButton getTglBtnRectangle() {
		return tglBtnRectangle;
	}

	public void setTglBtnRectangle(JToggleButton tglBtnRectangle) {
		this.tglBtnRectangle = tglBtnRectangle;
	}

	public JToggleButton getTglBtnCircle() {
		return tglBtnCircle;
	}

	public void setTglBtnCircle(JToggleButton tglBtnCircle) {
		this.tglBtnCircle = tglBtnCircle;
	}

	public JToggleButton getTglBtnDonut() {
		return tglBtnDonut;
	}

	public void setTglBtnDonut(JToggleButton tglBtnDonut) {
		this.tglBtnDonut = tglBtnDonut;
	}

	public JToggleButton getTglBtnSelect() {
		return tglBtnSelect;
	}

	public void setTglBtnSelect(JToggleButton tglBtnSelect) {
		this.tglBtnSelect = tglBtnSelect;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(JButton btnModify) {
		this.btnModify = btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(JButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public Shape getNewAddedShape() {
		return newAddedShape;
	}

	public void setNewAddedShape(Shape newAddedShape) {
		this.newAddedShape = newAddedShape;
	}

}
