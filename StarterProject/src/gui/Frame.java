package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
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

		pnlDrawing.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlDrawing);
		pnlDrawing.setBackground(Color.WHITE);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);   

	}
}
