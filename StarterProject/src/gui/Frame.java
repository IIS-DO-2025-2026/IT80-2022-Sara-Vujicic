package gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import geometry.PnlDrawing;
import geometry.Shape;

public class Frame extends JFrame {
	PnlDrawing pnlDrawing = new PnlDrawing();

    public Frame() {
        setVisible(true);

        setTitle("Vujicic Sara IT80/2022");
        setBounds(100, 100, 734, 471);

        pnlDrawing.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(pnlDrawing);
        pnlDrawing.setBackground(Color.WHITE);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		pnlDrawing.redraw();
	}
}
