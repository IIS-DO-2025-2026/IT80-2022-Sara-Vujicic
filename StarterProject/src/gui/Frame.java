package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import geometry.PnlDrawing;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PnlDrawing pnlDrawing = new PnlDrawing();

	public Frame() {
		setVisible(true);

		setTitle("Vujicic Sara IT80/2022");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 734, 471);
		setResizable(true);

		pnlDrawing.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlDrawing);
		pnlDrawing.setBackground(Color.WHITE);

		/*
		 * GroupLayout gl_contentPane = new GroupLayout(pnlDrawing);
		 * gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(
		 * Alignment.LEADING).addGroup(gl_contentPane .createSequentialGroup()
		 * .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(
		 * gl_contentPane .createSequentialGroup() .addComponent(pnlDrawing,
		 * GroupLayout.PREFERRED_SIZE, 603, GroupLayout.PREFERRED_SIZE)
		 * .addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
		 * .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
		 * .addComponent(pnlDrawing.getBtnDelete(), GroupLayout.DEFAULT_SIZE, 93,
		 * Short.MAX_VALUE) .addComponent(pnlDrawing.getBtnModify(),
		 * GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
		 * .addComponent(pnlDrawing.getTglBtnSelect(), GroupLayout.DEFAULT_SIZE, 93,
		 * Short.MAX_VALUE)))
		 * .addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
		 * .addComponent(pnlDrawing.getTglBtnSelect(), GroupLayout.PREFERRED_SIZE, 88,
		 * GroupLayout.PREFERRED_SIZE) .addGap(18)
		 * .addComponent(pnlDrawing.getTglBtnLine(), GroupLayout.PREFERRED_SIZE, 99,
		 * GroupLayout.PREFERRED_SIZE) .addGap(27)
		 * .addComponent(pnlDrawing.getTglBtnRectangle(), GroupLayout.PREFERRED_SIZE,
		 * 133, GroupLayout.PREFERRED_SIZE) .addGap(18)
		 * .addComponent(pnlDrawing.getTglBtnCircle(), GroupLayout.PREFERRED_SIZE, 103,
		 * GroupLayout.PREFERRED_SIZE)
		 * .addGap(18).addComponent(pnlDrawing.getTglBtnDonut(),
		 * GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)))
		 * .addContainerGap()));
		 * gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.
		 * LEADING) .addGroup(gl_contentPane.createSequentialGroup()
		 * .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
		 * .addGroup(Alignment.LEADING,
		 * gl_contentPane.createSequentialGroup().addContainerGap()
		 * .addComponent(pnlDrawing.getTglBtnSelect(), GroupLayout.PREFERRED_SIZE, 49,
		 * GroupLayout.PREFERRED_SIZE) .addPreferredGap(ComponentPlacement.UNRELATED)
		 * .addComponent(pnlDrawing.getBtnModify(), GroupLayout.PREFERRED_SIZE, 50,
		 * GroupLayout.PREFERRED_SIZE)
		 * .addGap(18).addComponent(pnlDrawing.getBtnDelete(),
		 * GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
		 * .addComponent(pnlDrawing, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
		 * .addPreferredGap(ComponentPlacement.RELATED)
		 * .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
		 * .addComponent(pnlDrawing.getTglBtnPoint(), GroupLayout.PREFERRED_SIZE, 44,
		 * GroupLayout.PREFERRED_SIZE) .addComponent(pnlDrawing.getTglBtnLine(),
		 * GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
		 * .addComponent(pnlDrawing.getTglBtnRectangle(), GroupLayout.DEFAULT_SIZE, 44,
		 * Short.MAX_VALUE) .addComponent(pnlDrawing.getTglBtnCircle(),
		 * GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
		 * .addComponent(pnlDrawing.getTglBtnDonut(), GroupLayout.PREFERRED_SIZE, 45,
		 * GroupLayout.PREFERRED_SIZE)) .addContainerGap()));
		 * pnlDrawing.setLayout(gl_contentPane); pnlDrawing.repaint();
		 */
	}

	/*
	 * @SuppressWarnings("unlikely-arg-type") protected void delete() { Shape
	 * selectedShape = (Shape) pnlDrawing.getSelectedShape();
	 * 
	 * if (selectedShape != null) { int selectedOption =
	 * JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?",
	 * "Warning message", JOptionPane.YES_NO_OPTION); if (selectedOption ==
	 * JOptionPane.YES_OPTION) {
	 * pnlDrawing.getAllShapesOnPanel().remove(selectedShape); } } else {
	 * JOptionPane.showMessageDialog(null, "You haven't selected any shape!",
	 * "Error", JOptionPane.WARNING_MESSAGE); } pnlDrawing.setSelectedShape(null);
	 * pnlDrawing.repaint();
	 * 
	 * }
	 */

}
