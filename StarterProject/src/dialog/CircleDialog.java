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
	private Color innerChosenColor = Color.WHITE;
	private Color borderChosenColor = Color.BLACK;

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
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			innerChosenColor = JColorChooser.showDialog(null, "Choose your inner color", point.getColor());
		});
		innerColorChooser.setMinimumSize(new Dimension(200, 20));
		innerColorChooser.setMaximumSize(new Dimension(200, 20));
		innerColorChooser.setPreferredSize(new Dimension(200, 20));
		add(innerColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		JButton borderColorChooser = new JButton("Border color");
		borderColorChooser.addActionListener(e -> {
			borderChosenColor = JColorChooser.showDialog(null, "Choose your border color", point.getColor());
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);


		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String radiusFieldText = radiusField.getText();
				boolean hasError = false;

				if (radiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Radius field cannot be blank.");
					hasError = true;
				}
				if (!hasError) {
					try {
						Integer radius = Integer.parseInt(radiusFieldText);
						if (radius < 0) {
							JOptionPane.showMessageDialog(null, "Radius cannot be negative number!");
						} else if (radius == 0) {
							JOptionPane.showMessageDialog(null, "Radius cannot be zero!");
						} else {
						
							Circle circle = new Circle(point, radius);
							circle.setInnerColor(innerChosenColor);
							circle.setBorderColor(borderChosenColor);
							circle.draw(mainPanel.getGraphics());
							mainPanel.getAllShapesOnPanel().add(circle);
							dispose();
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Invalid input. Radius filed cannot convert to integer.");
					}
				}
			}
		};

		modifyButton.addActionListener(modifyAction);
		gbc.gridx = 1;
		gbc.gridy = 4;
		modifyButton.setMinimumSize(new Dimension(200, 20));
		modifyButton.setMaximumSize(new Dimension(200, 20));
		modifyButton.setPreferredSize(new Dimension(200, 20));
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
		gbc.gridy = 2;
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
		gbc.gridy = 4;
		add(new JLabel("Radius: "), gbc);
		gbc.gridx = 1;
		JTextField radiusField = new JTextField();
		radiusField.setText(String.valueOf(circle.getRadius()));
		radiusField.setMinimumSize(new Dimension(200, 20));
		radiusField.setMaximumSize(new Dimension(200, 20));
		radiusField.setPreferredSize(new Dimension(200, 20));
		add(radiusField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			innerChosenColor = JColorChooser.showDialog(null, "Choose your inner color", circle.getInnerColor());
		});
		innerColorChooser.setMinimumSize(new Dimension(200, 20));
		innerColorChooser.setMaximumSize(new Dimension(200, 20));
		innerColorChooser.setPreferredSize(new Dimension(200, 20));
		add(innerColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 8;
		JButton borderColorChooser = new JButton("Border color");
		borderColorChooser.addActionListener(e -> {
			borderChosenColor = JColorChooser.showDialog(null, "Choose your border color", circle.getBorderColor());
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);

		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String xFieldText = xField.getText();
				String yFieldText = yField.getText();
				String radiusFieldText = radiusField.getText();
				boolean hasError = false;
				if (xFieldText.isBlank() && yFieldText.isBlank() && radiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "X, Y and radius for circle cannot be blank.");
					hasError = true;
				} else if (xFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "X field cannot be blank.");
					hasError = true;
				} else if (yFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Y field cannot be blank.");
					hasError = true;
				} else if (radiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Radius field cannot be blank.");
					hasError = true;
				}
				if (!hasError) {
					try {
						Integer x = Integer.parseInt(xField.getText());
						Integer y = Integer.parseInt(yField.getText());
						Integer radius = Integer.parseInt(radiusField.getText());

						if (x < 0) {
							JOptionPane.showMessageDialog(null, "X cannot be negative number!");
						} else if (y < 0) {
							JOptionPane.showMessageDialog(null, "Y cannot be negative number!");
						} else if (radius < 0) {
							JOptionPane.showMessageDialog(null, "Radius cannot be negative number!");
						} else if (radius == 0) {
							JOptionPane.showMessageDialog(null, "Radius cannot be zero!");
						} else {
							Color innerColor = innerChosenColor;
							Color borderColor = borderChosenColor;

							Point point = new Point(x, y);

							mainPanel.getAllShapesOnPanel().remove(circle);

							circle.setCenter(point);
							circle.setRadius(radius);
							circle.setInnerColor(innerColor);
							circle.setBorderColor(borderColor);
							mainPanel.getAllShapesOnPanel().add(circle);
							circle.draw(mainPanel.getGraphics());

							mainPanel.paint(mainPanel.getGraphics());
							dispose();
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Invalid input! Cannot convert X, Y and radius to integer");
					}
				}
			}
		};

		modifyButton.addActionListener(modifyAction);
		gbc.gridx = 1;
		gbc.gridy = 10;
		modifyButton.setMinimumSize(new Dimension(200, 20));
		modifyButton.setMaximumSize(new Dimension(200, 20));
		modifyButton.setPreferredSize(new Dimension(200, 20));
		add(modifyButton, gbc);

		setVisible(true);
	}

}
