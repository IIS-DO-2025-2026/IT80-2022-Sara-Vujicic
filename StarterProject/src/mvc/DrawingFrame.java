package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

public class DrawingFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private final DrawingView view;
	private DrawingController controller;

	private final JToggleButton tglBtnPoint;
	private final JToggleButton tglBtnLine;
	private final JToggleButton tglBtnRectangle;
	private final JToggleButton tglBtnCircle;
	private final JToggleButton tglBtnDonut;
	private final JToggleButton tglBtnHexagon;
	private final JToggleButton tglBtnSelect;
	private final JButton btnModify;
	private final JButton btnDelete;

	private Color activeEdgeColor = Color.BLACK;
	private Color activeInnerColor = Color.WHITE;
	private final JButton btnActiveEdgeColor;
	private final JButton btnActiveInnerColor;

	public DrawingFrame() {
		setTitle("Vujicic Sara IT80/2022");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Size and position
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		setSize(new Dimension(screenSize.width * 3 / 4, screenSize.height * 3 / 4));
		setLocationRelativeTo(null);

		// Create view (canvas) with a clean modern border and inset margin
		view = new DrawingView();
		view.setBorder(BorderFactory.createCompoundBorder(
				new EmptyBorder(10, 10, 10, 10),
				BorderFactory.createLineBorder(new Color(60, 60, 60), 2)
		));
		view.setBackground(Color.WHITE);

		// Create modern toolbar instead of simple panel
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBorder(new EmptyBorder(8, 8, 8, 8));

		tglBtnPoint = new JToggleButton("Point");
		tglBtnLine = new JToggleButton("Line");
		tglBtnRectangle = new JToggleButton("Rectangle");
		tglBtnCircle = new JToggleButton("Circle");
		tglBtnDonut = new JToggleButton("Donut");
		tglBtnHexagon = new JToggleButton("Hexagon");
		tglBtnSelect = new JToggleButton("Select");
		btnModify = new JButton("Modify");
		btnDelete = new JButton("Delete");

		btnActiveEdgeColor = new JButton("Edge Color");
		btnActiveEdgeColor.setOpaque(true);
		btnActiveEdgeColor.setContentAreaFilled(true);
		btnActiveInnerColor = new JButton("Inner Color");
		btnActiveInnerColor.setOpaque(true);
		btnActiveInnerColor.setContentAreaFilled(true);

		updateColorButton(btnActiveEdgeColor, activeEdgeColor);
		updateColorButton(btnActiveInnerColor, activeInnerColor);

		// Apply modern FlatLaf rounded buttons styling and helpful tooltips
		tglBtnPoint.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnLine.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnRectangle.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnCircle.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnDonut.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnHexagon.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnSelect.putClientProperty("JButton.buttonType", "roundRect");
		btnModify.putClientProperty("JButton.buttonType", "roundRect");
		btnDelete.putClientProperty("JButton.buttonType", "roundRect");
		btnActiveEdgeColor.putClientProperty("JButton.buttonType", "roundRect");
		btnActiveInnerColor.putClientProperty("JButton.buttonType", "roundRect");

		tglBtnPoint.setToolTipText("Draw points on click");
		tglBtnLine.setToolTipText("Select two points to draw a line");
		tglBtnRectangle.setToolTipText("Click canvas to specify top-left position and draw rectangle");
		tglBtnCircle.setToolTipText("Click canvas to specify center position and draw circle");
		tglBtnDonut.setToolTipText("Click canvas to specify center position and draw donut");
		tglBtnHexagon.setToolTipText("Click canvas to specify center position and draw hexagon");
		tglBtnSelect.setToolTipText("Click shapes on the canvas to select them");
		btnModify.setToolTipText("Modify properties of the selected shape");
		btnDelete.setToolTipText("Delete the selected shape");
		btnActiveEdgeColor.setToolTipText("Change default outline/border color for new shapes");
		btnActiveInnerColor.setToolTipText("Change default fill/inner color for new shapes");

		// Group 1: Draw Shapes
		toolbar.add(tglBtnPoint);
		toolbar.add(tglBtnLine);
		toolbar.add(tglBtnRectangle);
		toolbar.add(tglBtnCircle);
		toolbar.add(tglBtnDonut);
		toolbar.add(tglBtnHexagon);

		toolbar.addSeparator(new Dimension(15, 10));

		// Group 2: Operations
		toolbar.add(tglBtnSelect);
		toolbar.add(btnModify);
		toolbar.add(btnDelete);

		toolbar.addSeparator(new Dimension(15, 10));

		// Group 3: Active Colors
		toolbar.add(btnActiveEdgeColor);
		toolbar.add(btnActiveInnerColor);

		// Group the toggle buttons so only one tool is active at a time
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(tglBtnPoint);
		btnGroup.add(tglBtnLine);
		btnGroup.add(tglBtnRectangle);
		btnGroup.add(tglBtnCircle);
		btnGroup.add(tglBtnDonut);
		btnGroup.add(tglBtnHexagon);
		btnGroup.add(tglBtnSelect);

		// Add components to layout
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.NORTH);
		add(view, BorderLayout.CENTER);
	}

	public DrawingView getView() {
		return view;
	}

	public DrawingController getController() {
		return controller;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton getTglBtnPoint() {
		return tglBtnPoint;
	}

	public JToggleButton getTglBtnLine() {
		return tglBtnLine;
	}

	public JToggleButton getTglBtnRectangle() {
		return tglBtnRectangle;
	}

	public JToggleButton getTglBtnCircle() {
		return tglBtnCircle;
	}

	public JToggleButton getTglBtnDonut() {
		return tglBtnDonut;
	}

	public JToggleButton getTglBtnHexagon() {
		return tglBtnHexagon;
	}

	public JToggleButton getTglBtnSelect() {
		return tglBtnSelect;
	}

	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public JButton getBtnActiveEdgeColor() {
		return btnActiveEdgeColor;
	}

	public JButton getBtnActiveInnerColor() {
		return btnActiveInnerColor;
	}

	public Color getActiveEdgeColor() {
		return activeEdgeColor;
	}

	public void setActiveEdgeColor(Color color) {
		this.activeEdgeColor = color;
		updateColorButton(btnActiveEdgeColor, color);
	}

	public Color getActiveInnerColor() {
		return activeInnerColor;
	}

	public void setActiveInnerColor(Color color) {
		this.activeInnerColor = color;
		updateColorButton(btnActiveInnerColor, color);
	}

	private void updateColorButton(JButton button, Color color) {
		button.setBackground(color);
		double luma = 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
		button.setForeground(luma < 128 ? Color.WHITE : Color.BLACK);
	}
}
