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

import geometry.Donut;
import geometry.PnlDrawing;
import geometry.Point;

public class DonutDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color innerChosedColor = Color.WHITE;
	private Color borderChosedColor = Color.BLACK;

	public DonutDialog(Point point, PnlDrawing mainPanel) {
		setTitle("Add Donut");
		setSize(new Dimension(600, 400));

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Inner radius: "), gbc);

		gbc.gridx = 1;
		JTextField innerRadiusField = new JTextField();
		innerRadiusField.setMinimumSize(new Dimension(200, 20));
		innerRadiusField.setMaximumSize(new Dimension(200, 20));
		innerRadiusField.setPreferredSize(new Dimension(200, 20));

		add(innerRadiusField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Out Radius: "), gbc);

		gbc.gridx = 1;
		JTextField outRadiusField = new JTextField();
		outRadiusField.setMinimumSize(new Dimension(200, 20));
		outRadiusField.setMaximumSize(new Dimension(200, 20));
		outRadiusField.setPreferredSize(new Dimension(200, 20));

		add(outRadiusField, gbc);
		
		gbc.gridy = 3;
		gbc.gridx = 1;
		JButton borderColorChooser = new JButton("Border color");
		borderColorChooser.addActionListener(e -> {
			borderChosedColor = JColorChooser.showDialog(null, "Choose your border color", point.getColor());
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			innerChosedColor = JColorChooser.showDialog(null, "Choose your inner color", point.getColor());
		});
		innerColorChooser.setMinimumSize(new Dimension(200, 20));
		innerColorChooser.setMaximumSize(new Dimension(200, 20));
		innerColorChooser.setPreferredSize(new Dimension(200, 20));
		add(innerColorChooser, gbc);


		setLocationRelativeTo(null);
		setVisible(true);

		JButton saveButton = new JButton("Save");
		ActionListener saveAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String innerRadiusFieldText = innerRadiusField.getText();
				String outRadiusFieldText = outRadiusField.getText();
				boolean hasError = false;
				if (innerRadiusFieldText.isBlank() && outRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Inner and out radius cannot be blank!");
					hasError = true;
				} else if (innerRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Inner radius cannot be blank!");
					hasError = true;
				} else if (outRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Out radius cannot be blank!");
					hasError = true;
				}
				if (!hasError) {
					try {
						int innerRadius = Integer.parseInt(innerRadiusFieldText);
						int outRadius = Integer.parseInt(outRadiusFieldText);
						if (innerRadius < 0) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be negative number!");
						} else if (innerRadius == 0) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be zero!");
						} else if (outRadius < 0) {
							JOptionPane.showMessageDialog(null, "Out radius cannot be negative number!");
						} else if (outRadius == 0) {
							JOptionPane.showMessageDialog(null, "Out radius cannot be zero!");
						} else if (innerRadius > outRadius) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be larger than outer!");
						} else if (innerRadius == outRadius) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be same as outer!");
						} else {
							Donut donut = new Donut(point, outRadius, innerRadius);
							donut.setInnerColor(innerChosedColor);
							donut.setBorderColor(borderChosedColor);
							donut.draw(mainPanel.getGraphics());

							mainPanel.getAllShapesOnPanel().add(donut);
							dispose();
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Invalid input!");
					}
				}
			}
		};
		saveButton.addActionListener(saveAction);
		gbc.gridx = 1;
		gbc.gridy = 5;
		saveButton.setMinimumSize(new Dimension(200, 20));
		saveButton.setMaximumSize(new Dimension(200, 20));
		saveButton.setPreferredSize(new Dimension(200, 20));
		add(saveButton, gbc);

	}

	public DonutDialog(Donut donut, PnlDrawing mainPanel) {
		setTitle("Modify Donut");
		setSize(new Dimension(600, 400));

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Center Point: "), gbc);

		gbc.gridy = 1;
		add(new JLabel("X: "), gbc);
		gbc.gridx = 1;
		JTextField xField = new JTextField();
		xField.setText(String.valueOf(donut.getCenter().getX()));
		xField.setMinimumSize(new Dimension(200, 20));
		xField.setMaximumSize(new Dimension(200, 20));
		xField.setPreferredSize(new Dimension(200, 20));

		add(xField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Y: "), gbc);

		gbc.gridx = 1;
		JTextField yField = new JTextField();
		yField.setText(String.valueOf(donut.getCenter().getY()));
		yField.setMinimumSize(new Dimension(200, 20));
		yField.setMaximumSize(new Dimension(200, 20));
		yField.setPreferredSize(new Dimension(200, 20));

		add(yField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("Out Radius: "), gbc);

		gbc.gridx = 1;
		JTextField outRadiusField = new JTextField();
		outRadiusField.setText(String.valueOf(donut.getRadius()));
		outRadiusField.setMinimumSize(new Dimension(200, 20));
		outRadiusField.setMaximumSize(new Dimension(200, 20));
		outRadiusField.setPreferredSize(new Dimension(200, 20));

		add(outRadiusField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		add(new JLabel("Inner Radius: "), gbc);

		gbc.gridx = 1;
		JTextField innerRadiusField = new JTextField();
		innerRadiusField.setText(String.valueOf(donut.getInnerRadius()));
		innerRadiusField.setMinimumSize(new Dimension(200, 20));
		innerRadiusField.setMaximumSize(new Dimension(200, 20));
		innerRadiusField.setPreferredSize(new Dimension(200, 20));

		add(innerRadiusField, gbc);

		gbc.gridy = 8;
		gbc.gridx = 1;
		JButton borderColorChooser = new JButton("Border color");
		borderColorChooser.addActionListener(e -> {
			borderChosedColor = JColorChooser.showDialog(null, "Choose your border color", donut.getBorderColor());
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 10;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			innerChosedColor = JColorChooser.showDialog(null, "Choose your inner color", donut.getInnerColor());
		});
		innerColorChooser.setMinimumSize(new Dimension(200, 20));
		innerColorChooser.setMaximumSize(new Dimension(200, 20));
		innerColorChooser.setPreferredSize(new Dimension(200, 20));
		add(innerColorChooser, gbc);

		setLocationRelativeTo(null);
		setVisible(true);

		JButton saveButton = new JButton("Save");
		ActionListener saveAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String xFieldText = xField.getText();
				String yFieldText = yField.getText();
				String innerRadiusFieldText = innerRadiusField.getText();
				String outRadiusFieldText = outRadiusField.getText();
				boolean hasError = false;
				if (xFieldText.isBlank() && yFieldText.isBlank() && innerRadiusFieldText.isBlank()
						&& outRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Inner and out radius and x,y cannot be blank!");
					hasError = true;
				} else if (innerRadiusFieldText.isBlank() && outRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Inner and out radius cannot be blank!");
					hasError = true;
				} else if (innerRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Inner radius cannot be blank!");
					hasError = true;
				} else if (outRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Out radius cannot be blank!");
					hasError = true;
				} else if (xFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "X field cannot be blank!");
					hasError = true;
				} else if (yFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Y field cannot be blank!");
					hasError = true;
				}
				if (!hasError) {
					try {
						int x = Integer.parseInt(xFieldText);
						int y = Integer.parseInt(yFieldText);
						Point center = new Point(x, y);
						int innerRadius = Integer.parseInt(innerRadiusFieldText);
						int outRadius = Integer.parseInt(outRadiusFieldText);
						Color innerColor = innerChosedColor;
						Color borderColor = borderChosedColor;

						if (x < 0) {
							JOptionPane.showMessageDialog(null, "X cannot be negative number!");
						} else if (y < 0) {
							JOptionPane.showMessageDialog(null, "Y cannot be negative number!");
						} else if (innerRadius < 0) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be negative number!");
						} else if (innerRadius == 0) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be zero!");
						} else if (outRadius < 0) {
							JOptionPane.showMessageDialog(null, "Out radius cannot be negative number!");
						} else if (outRadius == 0) {
							JOptionPane.showMessageDialog(null, "Out radius cannot be zero!");
						} else if (innerRadius > outRadius) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be larger than outer!");
						} else if (innerRadius == outRadius) {
							JOptionPane.showMessageDialog(null, "Inner radius cannot be same as outer!");
						} else {
							mainPanel.getAllShapesOnPanel().remove(donut);

							donut.setCenter(center);
							donut.setRadius(outRadius);
							donut.setInnerRadius(innerRadius);

							donut.setInnerColor(innerColor);
							donut.setBorderColor(borderColor);
							mainPanel.getAllShapesOnPanel().add(donut);
							donut.draw(mainPanel.getGraphics());

							mainPanel.paint(mainPanel.getGraphics());
							dispose();
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid input! Cannot convert x, y, inner radius, out radius to integer!");
					}
				}
			}
		};
		saveButton.addActionListener(saveAction);
		gbc.gridx = 1;
		gbc.gridy = 12;
		saveButton.setMinimumSize(new Dimension(200, 20));
		saveButton.setMaximumSize(new Dimension(200, 20));
		saveButton.setPreferredSize(new Dimension(200, 20));
		add(saveButton, gbc);

	}

}
