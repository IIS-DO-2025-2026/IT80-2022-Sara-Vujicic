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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DrawingFrame extends JFrame implements PropertyChangeListener {
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
	private final JButton btnToFront;
	private final JButton btnToBack;
	private final JButton btnBringToFront;
	private final JButton btnBringToBack;

	private Color activeEdgeColor = Color.BLACK;
	private Color activeInnerColor = Color.WHITE;
	private final JButton btnActiveEdgeColor;
	private final JButton btnActiveInnerColor;
	private final JButton btnUndo;
	private final JButton btnRedo;
	private final JButton btnSave;
	private final JButton btnLoad;
	private final JButton btnNextCommand;
	private final JTextArea txtAreaLog;

	public DrawingFrame() {
		setTitle("Vujicic Sara IT80/2022");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		setSize(new Dimension(screenSize.width * 3 / 4, screenSize.height * 3 / 4));
		setLocationRelativeTo(null);

		
		view = new DrawingView();
		view.setBorder(BorderFactory.createCompoundBorder(
				new EmptyBorder(10, 10, 10, 10),
				BorderFactory.createLineBorder(new Color(60, 60, 60), 2)
		));
		view.setBackground(Color.WHITE);

		
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
		btnToFront = new JButton("To Front");
		btnToBack = new JButton("To Back");
		btnBringToFront = new JButton("Bring To Front");
		btnBringToBack = new JButton("Bring To Back");

		btnModify.setEnabled(false);
		btnDelete.setEnabled(false);
		btnToFront.setEnabled(false);
		btnToBack.setEnabled(false);
		btnBringToFront.setEnabled(false);
		btnBringToBack.setEnabled(false);

		btnActiveEdgeColor = new JButton("Edge Color");
		btnActiveEdgeColor.setOpaque(true);
		btnActiveEdgeColor.setContentAreaFilled(true);
		btnActiveInnerColor = new JButton("Inner Color");
		btnActiveInnerColor.setOpaque(true);
		btnActiveInnerColor.setContentAreaFilled(true);

		btnUndo = new JButton("Undo");
		btnRedo = new JButton("Redo");
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);

		btnSave = new JButton("Save");
		btnLoad = new JButton("Load");
		btnNextCommand = new JButton("Next Command");
		btnNextCommand.setEnabled(false);

		txtAreaLog = new JTextArea(8, 50);
		txtAreaLog.setEditable(false);

		updateColorButton(btnActiveEdgeColor, activeEdgeColor);
		updateColorButton(btnActiveInnerColor, activeInnerColor);

		
		tglBtnPoint.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnLine.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnRectangle.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnCircle.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnDonut.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnHexagon.putClientProperty("JButton.buttonType", "roundRect");
		tglBtnSelect.putClientProperty("JButton.buttonType", "roundRect");
		btnModify.putClientProperty("JButton.buttonType", "roundRect");
		btnDelete.putClientProperty("JButton.buttonType", "roundRect");
		btnToFront.putClientProperty("JButton.buttonType", "roundRect");
		btnToBack.putClientProperty("JButton.buttonType", "roundRect");
		btnBringToFront.putClientProperty("JButton.buttonType", "roundRect");
		btnBringToBack.putClientProperty("JButton.buttonType", "roundRect");
		btnActiveEdgeColor.putClientProperty("JButton.buttonType", "roundRect");
		btnActiveInnerColor.putClientProperty("JButton.buttonType", "roundRect");
		btnUndo.putClientProperty("JButton.buttonType", "roundRect");
		btnRedo.putClientProperty("JButton.buttonType", "roundRect");
		btnSave.putClientProperty("JButton.buttonType", "roundRect");
		btnLoad.putClientProperty("JButton.buttonType", "roundRect");
		btnNextCommand.putClientProperty("JButton.buttonType", "roundRect");

		tglBtnPoint.setToolTipText("Draw points on click");
		tglBtnLine.setToolTipText("Select two points to draw a line");
		tglBtnRectangle.setToolTipText("Click canvas to specify top-left position and draw rectangle");
		tglBtnCircle.setToolTipText("Click canvas to specify center position and draw circle");
		tglBtnDonut.setToolTipText("Click canvas to specify center position and draw donut");
		tglBtnHexagon.setToolTipText("Click canvas to specify center position and draw hexagon");
		tglBtnSelect.setToolTipText("Click shapes on the canvas to select them");
		btnModify.setToolTipText("Modify properties of the selected shape");
		btnDelete.setToolTipText("Delete the selected shape");
		btnToFront.setToolTipText("Move selected shape one step forward in Z-order");
		btnToBack.setToolTipText("Move selected shape one step backward in Z-order");
		btnBringToFront.setToolTipText("Bring selected shape to the very top in Z-order");
		btnBringToBack.setToolTipText("Bring selected shape to the very bottom in Z-order");
		btnActiveEdgeColor.setToolTipText("Change default outline/border color for new shapes");
		btnActiveInnerColor.setToolTipText("Change default fill/inner color for new shapes");
		btnUndo.setToolTipText("Undo the last operation");
		btnRedo.setToolTipText("Redo the last undone operation");
		btnSave.setToolTipText("Save drawing or text log to a file");
		btnLoad.setToolTipText("Load serialized drawing or text log from a file");
		btnNextCommand.setToolTipText("Execute the next command from the loaded log file");

		
		toolbar.add(tglBtnPoint);
		toolbar.add(tglBtnLine);
		toolbar.add(tglBtnRectangle);
		toolbar.add(tglBtnCircle);
		toolbar.add(tglBtnDonut);
		toolbar.add(tglBtnHexagon);

		toolbar.addSeparator(new Dimension(15, 10));

		
		toolbar.add(tglBtnSelect);
		toolbar.add(btnModify);
		toolbar.add(btnDelete);
		toolbar.add(btnToFront);
		toolbar.add(btnToBack);
		toolbar.add(btnBringToFront);
		toolbar.add(btnBringToBack);

		toolbar.addSeparator(new Dimension(15, 10));

	
		toolbar.add(btnActiveEdgeColor);
		toolbar.add(btnActiveInnerColor);

		toolbar.addSeparator(new Dimension(15, 10));

	
		toolbar.add(btnUndo);
		toolbar.add(btnRedo);

		toolbar.addSeparator(new Dimension(15, 10));

		
		toolbar.add(btnSave);
		toolbar.add(btnLoad);
		toolbar.add(btnNextCommand);

		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(tglBtnPoint);
		btnGroup.add(tglBtnLine);
		btnGroup.add(tglBtnRectangle);
		btnGroup.add(tglBtnCircle);
		btnGroup.add(tglBtnDonut);
		btnGroup.add(tglBtnHexagon);
		btnGroup.add(tglBtnSelect);

		
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.NORTH);
		add(view, BorderLayout.CENTER);
		add(new javax.swing.JScrollPane(txtAreaLog), BorderLayout.SOUTH);
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

	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JButton getBtnSave() {
		return btnSave;
	}

	public JButton getBtnLoad() {
		return btnLoad;
	}

	public JButton getBtnNextCommand() {
		return btnNextCommand;
	}

	public JTextArea getTxtAreaLog() {
		return txtAreaLog;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	private void updateColorButton(JButton button, Color color) {
		button.setBackground(color);
		double luma = 0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue();
		button.setForeground(luma < 128 ? Color.WHITE : Color.BLACK);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("selectedCount".equals(evt.getPropertyName())) {
			int count = (int) evt.getNewValue();
			btnDelete.setEnabled(count >= 1);
			btnModify.setEnabled(count == 1);
		} else if ("toFrontEnabled".equals(evt.getPropertyName())) {
			btnToFront.setEnabled((boolean) evt.getNewValue());
		} else if ("toBackEnabled".equals(evt.getPropertyName())) {
			btnToBack.setEnabled((boolean) evt.getNewValue());
		} else if ("bringToFrontEnabled".equals(evt.getPropertyName())) {
			btnBringToFront.setEnabled((boolean) evt.getNewValue());
		} else if ("bringToBackEnabled".equals(evt.getPropertyName())) {
			btnBringToBack.setEnabled((boolean) evt.getNewValue());
		}
	}
}
