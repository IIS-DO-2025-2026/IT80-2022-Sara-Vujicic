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
import javax.swing.WindowConstants;
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

		// Create view (canvas)
		view = new DrawingView();
		view.setBorder(new EmptyBorder(5, 5, 5, 5));
		view.setBackground(Color.WHITE);

		// Create control panel
		JPanel pnlControls = new JPanel();
		pnlControls.setBackground(Color.LIGHT_GRAY);

		tglBtnPoint = new JToggleButton("Point");
		tglBtnLine = new JToggleButton("Line");
		tglBtnRectangle = new JToggleButton("Rectangle");
		tglBtnCircle = new JToggleButton("Circle");
		tglBtnDonut = new JToggleButton("Donut");
		tglBtnHexagon = new JToggleButton("Hexagon");
		tglBtnSelect = new JToggleButton("Select");
		btnModify = new JButton("Modify");
		btnDelete = new JButton("Delete");

		pnlControls.add(tglBtnPoint);
		pnlControls.add(tglBtnLine);
		pnlControls.add(tglBtnRectangle);
		pnlControls.add(tglBtnCircle);
		pnlControls.add(tglBtnDonut);
		pnlControls.add(tglBtnHexagon);
		pnlControls.add(btnDelete);
		pnlControls.add(btnModify);
		pnlControls.add(tglBtnSelect);

		btnActiveEdgeColor = new JButton("Edge Color");
		btnActiveEdgeColor.setOpaque(true);
		btnActiveEdgeColor.setContentAreaFilled(true);
		btnActiveInnerColor = new JButton("Inner Color");
		btnActiveInnerColor.setOpaque(true);
		btnActiveInnerColor.setContentAreaFilled(true);

		updateColorButton(btnActiveEdgeColor, activeEdgeColor);
		updateColorButton(btnActiveInnerColor, activeInnerColor);

		pnlControls.add(btnActiveEdgeColor);
		pnlControls.add(btnActiveInnerColor);

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
		add(pnlControls, BorderLayout.NORTH);
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
