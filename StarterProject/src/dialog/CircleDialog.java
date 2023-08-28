package dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import geometry.Circle;
import geometry.PnlDrawing;
import geometry.Point;

public class CircleDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color innerChosedColor;
	private Color outerChosedColor;

	public CircleDialog(Point point, PnlDrawing mainPanel) {

		setTitle("Add Circle");
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Radius: "), gbc);

		gbc.gridx = 1;

		JTextField radiusField = new JTextField();
		radiusField.setMinimumSize(new Dimension(200, 20));
		radiusField.setMaximumSize(new Dimension(200, 20));
		radiusField.setPreferredSize(new Dimension(200, 20));
		add(radiusField, gbc);

		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer radius = Integer.parseInt(radiusField.getText());

					Circle circle = new Circle(point, radius);
					circle.draw(mainPanel.getGraphics());
					mainPanel.getAllShapesOnPanel().add(circle);
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
				}
			}
		};

		modifyButton.addActionListener(modifyAction);
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(modifyButton, gbc);

		setVisible(true);
	}

	public CircleDialog(Circle circle, PnlDrawing mainPanel) {

		setTitle("Modify Circle");
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("X: "), gbc);

		gbc.gridx = 1;

		JTextField xField = new JTextField();
		xField.setText(String.valueOf(circle.getCenter().getX()));
		xField.setMinimumSize(new Dimension(200, 20));
		xField.setMaximumSize(new Dimension(200, 20));
		xField.setPreferredSize(new Dimension(200, 20));
		add(xField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		add(new JLabel("Y: "), gbc);

		gbc.gridx = 1;
		JTextField yField = new JTextField();
		yField.setText(String.valueOf(circle.getCenter().getY()));
		yField.setMinimumSize(new Dimension(200, 20));
		yField.setMaximumSize(new Dimension(200, 20));
		yField.setPreferredSize(new Dimension(200, 20));
		add(yField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Radius: "), gbc);
		gbc.gridx = 1;
		JTextField radiusField = new JTextField();
		radiusField.setText(String.valueOf(circle.getRadius()));
		radiusField.setMinimumSize(new Dimension(200, 20));
		radiusField.setMaximumSize(new Dimension(200, 20));
		radiusField.setPreferredSize(new Dimension(200, 20));
		add(radiusField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			innerChosedColor = JColorChooser.showDialog(null, "Choose your inner color", Color.BLACK);
		});
		add(innerColorChooser, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		JButton outerColorChooser = new JButton("Outer color");
		outerColorChooser.addActionListener(e -> {
			outerChosedColor = JColorChooser.showDialog(null, "Choose your outer color", Color.BLACK);
		});
		add(innerColorChooser, gbc);

		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer x = Integer.parseInt(xField.getText());
					Integer y = Integer.parseInt(yField.getText());
					Integer radius = Integer.parseInt(radiusField.getText());
					Color innerColor = innerChosedColor;
					Color outlineColor = outerChosedColor;

					Point point = new Point(x, y);

					circle.setCenter(point);
					circle.setRadius(radius);
					circle.setColor(innerColor);
					circle.setBorderColor(outlineColor);
					mainPanel.getAllShapesOnPanel().add(circle);
					circle.draw(mainPanel.getGraphics());

					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
				}
			}
		};

		modifyButton.addActionListener(modifyAction);
		gbc.gridx = 2;
		gbc.gridy = 5;
		add(modifyButton, gbc);

		setVisible(true);
	}

}
