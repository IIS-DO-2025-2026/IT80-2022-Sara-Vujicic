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
	private Color innerChosedColor;
	private Color outerChosedColor;

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

		setLocationRelativeTo(null);
		setVisible(true);

		JButton saveButton = new JButton("Save");
		ActionListener saveAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int innerRadius = Integer.parseInt(innerRadiusField.getText());
					int outRadius = Integer.parseInt(outRadiusField.getText());
					if (innerRadius > outRadius) {
						JOptionPane.showMessageDialog(null, "Inner radius cannot be larger than outer!");
					} else {
						Donut donut = new Donut(point, outRadius, innerRadius);
						donut.setBorderColor(innerChosedColor);
						donut.setOuterColor(outerChosedColor);
						donut.draw(mainPanel.getGraphics());

						mainPanel.getAllShapesOnPanel().add(donut);
						dispose();
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
				}
			}
		};
		saveButton.addActionListener(saveAction);
		gbc.gridx = 1;
		gbc.gridy = 4;
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
		JButton outerColorChooser = new JButton("Outer color");
		outerColorChooser.addActionListener(e -> {
			outerChosedColor = JColorChooser.showDialog(null, "Choose your outer color", Color.GRAY);
		});
		outerColorChooser.setMinimumSize(new Dimension(200, 20));
		outerColorChooser.setMaximumSize(new Dimension(200, 20));
		outerColorChooser.setPreferredSize(new Dimension(200, 20));
		add(outerColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 10;
		JButton innerColorChooser = new JButton("Inner color");
		innerColorChooser.addActionListener(e -> {
			innerChosedColor = JColorChooser.showDialog(null, "Choose your inner color", Color.BLACK);
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
				try {
					int x = Integer.parseInt(xField.getText());
					int y = Integer.parseInt(yField.getText());
					Point center = new Point(x, y);
					int innerRadius = Integer.parseInt(innerRadiusField.getText());
					int outRadius = Integer.parseInt(outRadiusField.getText());
					Color outlerColor = outerChosedColor;
					Color borderColor = innerChosedColor;

					donut.setCenter(center);
					donut.setRadius(outRadius);
					donut.setInnerRadius(innerRadius);

					donut.setOuterColor(outlerColor);
					donut.setBorderColor(borderColor);
					mainPanel.getAllShapesOnPanel().add(donut);
					donut.draw(mainPanel.getGraphics());
					dispose();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
				}
				dispose();
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
