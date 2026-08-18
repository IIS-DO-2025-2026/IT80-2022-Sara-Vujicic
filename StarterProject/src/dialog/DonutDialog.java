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
import geometry.Point;

public class DonutDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private Color innerChosenColor = Color.WHITE;
	private Color borderChosenColor = Color.BLACK;
	private boolean confirmed = false;
	private Donut donut;

	public DonutDialog(java.awt.Frame parent, Point point) {
		super(parent, "Add Donut", true);
		if (parent instanceof mvc.DrawingFrame) {
			this.borderChosenColor = ((mvc.DrawingFrame) parent).getActiveEdgeColor();
			this.innerChosenColor = ((mvc.DrawingFrame) parent).getActiveInnerColor();
		}
		setSize(new Dimension(600, 450));
		setLocationRelativeTo(parent);

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Center Point: "), gbc);

		gbc.gridy = 1;
		add(new JLabel("X: "), gbc);
		gbc.gridx = 1;
		JTextField xField = new JTextField();
		xField.setText(String.valueOf(point.getX()));
		xField.setMinimumSize(new Dimension(200, 20));
		xField.setMaximumSize(new Dimension(200, 20));
		xField.setPreferredSize(new Dimension(200, 20));
		add(xField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Y: "), gbc);
		gbc.gridx = 1;
		JTextField yField = new JTextField();
		yField.setText(String.valueOf(point.getY()));
		yField.setMinimumSize(new Dimension(200, 20));
		yField.setMaximumSize(new Dimension(200, 20));
		yField.setPreferredSize(new Dimension(200, 20));
		add(yField, gbc);

		
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Inner radius: "), gbc);
		gbc.gridx = 1;
		JTextField innerRadiusField = new JTextField();
		innerRadiusField.setMinimumSize(new Dimension(200, 20));
		innerRadiusField.setMaximumSize(new Dimension(200, 20));
		innerRadiusField.setPreferredSize(new Dimension(200, 20));
		add(innerRadiusField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("Out Radius: "), gbc);
		gbc.gridx = 1;
		JTextField outRadiusField = new JTextField();
		outRadiusField.setMinimumSize(new Dimension(200, 20));
		outRadiusField.setMaximumSize(new Dimension(200, 20));
		outRadiusField.setPreferredSize(new Dimension(200, 20));
		add(outRadiusField, gbc);
		
		gbc.gridy = 5;
		gbc.gridx = 1;
		JButton borderColorChooser = new JButton("Border color");
		borderColorChooser.addActionListener(e -> {
			Color c = JColorChooser.showDialog(null, "Choose your border color", borderChosenColor);
			if (c != null) {
				borderChosenColor = c;
			}
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			Color c = JColorChooser.showDialog(null, "Choose your inner color", innerChosenColor);
			if (c != null) {
				innerChosenColor = c;
			}
		});
		innerColorChooser.setMinimumSize(new Dimension(200, 20));
		innerColorChooser.setMaximumSize(new Dimension(200, 20));
		innerColorChooser.setPreferredSize(new Dimension(200, 20));
		add(innerColorChooser, gbc);

		JButton saveButton = new JButton("Save");
		ActionListener saveAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String xFieldText = xField.getText();
				String yFieldText = yField.getText();
				String innerRadiusFieldText = innerRadiusField.getText();
				String outRadiusFieldText = outRadiusField.getText();
				boolean hasError = false;
				if (xFieldText.isBlank() || yFieldText.isBlank() || innerRadiusFieldText.isBlank() || outRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "All fields (X, Y, Inner radius, Out radius) must be filled!");
					hasError = true;
				}
				if (!hasError) {
					try {
						int x = Integer.parseInt(xFieldText);
						int y = Integer.parseInt(yFieldText);
						int innerRadius = Integer.parseInt(innerRadiusFieldText);
						int outRadius = Integer.parseInt(outRadiusFieldText);
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
							Point center = new Point(x, y);
							Donut d = new Donut(center, outRadius, innerRadius);
							d.setInnerColor(innerChosenColor);
							d.setEdgeColor(borderChosenColor);
							DonutDialog.this.donut = d;
							DonutDialog.this.confirmed = true;
							dispose();
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Invalid input! Fields must be valid integers.");
					}
				}
			}
		};
		saveButton.addActionListener(saveAction);
		gbc.gridx = 1;
		gbc.gridy = 7;
		saveButton.setMinimumSize(new Dimension(200, 20));
		saveButton.setMaximumSize(new Dimension(200, 20));
		saveButton.setPreferredSize(new Dimension(200, 20));
		add(saveButton, gbc);
	}

	public DonutDialog(java.awt.Frame parent, Donut donut) {
		super(parent, "Modify Donut", true);
		this.innerChosenColor = donut.getInnerColor();
		this.borderChosenColor = donut.getEdgeColor();
		setSize(new Dimension(600, 450));
		setLocationRelativeTo(parent);

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
			Color c = JColorChooser.showDialog(null, "Choose your border color", borderChosenColor);
			if (c != null) {
				borderChosenColor = c;
			}
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 10;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			Color c = JColorChooser.showDialog(null, "Choose your inner color", innerChosenColor);
			if (c != null) {
				innerChosenColor = c;
			}
		});
		innerColorChooser.setMinimumSize(new Dimension(200, 20));
		innerColorChooser.setMaximumSize(new Dimension(200, 20));
		innerColorChooser.setPreferredSize(new Dimension(200, 20));
		add(innerColorChooser, gbc);

		JButton saveButton = new JButton("Save");
		ActionListener saveAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String xFieldText = xField.getText();
				String yFieldText = yField.getText();
				String innerRadiusFieldText = innerRadiusField.getText();
				String outRadiusFieldText = outRadiusField.getText();
				boolean hasError = false;
				if (xFieldText.isBlank() || yFieldText.isBlank() || innerRadiusFieldText.isBlank() || outRadiusFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "All fields (X, Y, Inner radius, Out radius) must be filled!");
					hasError = true;
				}
				if (!hasError) {
					try {
						int x = Integer.parseInt(xFieldText);
						int y = Integer.parseInt(yFieldText);
						int innerRadius = Integer.parseInt(innerRadiusFieldText);
						int outRadius = Integer.parseInt(outRadiusFieldText);

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
							Point center = new Point(x, y);
							Donut d = new Donut(center, outRadius, innerRadius);
							d.setInnerColor(innerChosenColor);
							d.setEdgeColor(borderChosenColor);
							DonutDialog.this.donut = d;
							DonutDialog.this.confirmed = true;
							dispose();
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Invalid input! Fields must be valid integers.");
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

	public boolean isConfirmed() {
		return confirmed;
	}

	public Donut getDonut() {
		return donut;
	}
}
