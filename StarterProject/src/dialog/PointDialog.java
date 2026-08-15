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

import geometry.PnlDrawing;
import geometry.Point;

public class PointDialog extends JDialog {

	/**
	 * 
	 */
	private Color pointChoosedColor = Color.BLACK;
	private static final long serialVersionUID = 1L;

	public PointDialog(int x, int y, PnlDrawing mainPanel) {

		setTitle("Add Point");
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("X: "), gbc);

		gbc.gridx = 1;
		JTextField xField = new JTextField();
		xField.setText(String.valueOf(x));
		xField.setMinimumSize(new Dimension(200, 20));
		xField.setMaximumSize(new Dimension(200, 20));
		xField.setPreferredSize(new Dimension(200, 20));
		xField.setEditable(false);
		add(xField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		add(new JLabel("Y: "), gbc);

		gbc.gridx = 1;
		JTextField yField = new JTextField();
		yField.setText(String.valueOf(y));
		yField.setMinimumSize(new Dimension(200, 20));
		yField.setMaximumSize(new Dimension(200, 20));
		yField.setPreferredSize(new Dimension(200, 20));
		yField.setEditable(false);
		add(yField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		JButton pointColorChooser = new JButton("Color");
		pointColorChooser.addActionListener(e -> {
			pointChoosedColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
		});
		pointColorChooser.setMinimumSize(new Dimension(200, 20));
		pointColorChooser.setMaximumSize(new Dimension(200, 20));
		pointColorChooser.setPreferredSize(new Dimension(200, 20));
		add(pointColorChooser, gbc);

		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Point point = new Point(x, y);

				point.setColor(pointChoosedColor);

				mainPanel.getAllShapesOnPanel().add(point);

				mainPanel.paint(mainPanel.getGraphics());
				dispose();
			}
		};

		modifyButton.addActionListener(modifyAction);
		gbc.gridx = 1;
		gbc.gridy = 6;
		modifyButton.setMinimumSize(new Dimension(200, 20));
		modifyButton.setMaximumSize(new Dimension(200, 20));
		modifyButton.setPreferredSize(new Dimension(200, 20));
		add(modifyButton, gbc);

		setVisible(true);

	}

	public PointDialog(Point point, PnlDrawing mainPanel) {

		setTitle("Modify Point");
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
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
		gbc.weightx = 1;
		add(new JLabel("Y: "), gbc);

		gbc.gridx = 1;
		JTextField yField = new JTextField();
		yField.setText(String.valueOf(point.getY()));
		yField.setMinimumSize(new Dimension(200, 20));
		yField.setMaximumSize(new Dimension(200, 20));
		yField.setPreferredSize(new Dimension(200, 20));
		add(yField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		JButton pointColorChooser = new JButton("Color");
		pointColorChooser.addActionListener(e -> {
			pointChoosedColor = JColorChooser.showDialog(null, "Choose color", point.getColor());
		});
		pointColorChooser.setMinimumSize(new Dimension(200, 20));
		pointColorChooser.setMaximumSize(new Dimension(200, 20));
		pointColorChooser.setPreferredSize(new Dimension(200, 20));
		add(pointColorChooser, gbc);

		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String xFieldText = xField.getText();
				String yFieldText = yField.getText();
				boolean hasError = false;

				if (xFieldText.isBlank() && yFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! X Field and Y Field cannot be blank!");
					hasError = true;
				} else if (xFieldText.isEmpty() || xFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! X Field cannot be blank!");
					hasError = true;
				} else if (yFieldText.isEmpty() || yFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Y Field cannot be blank!");
					hasError = true;
				}
				if (!hasError) {
					try {
						Integer x = Integer.parseInt(xFieldText);
						Integer y = Integer.parseInt(yFieldText);

						if (x < 0) {
							JOptionPane.showMessageDialog(null, "X cannot be negative number!");
						} else if (y < 0) {
							JOptionPane.showMessageDialog(null, "Y cannot be negative number!");
						} else {
							mainPanel.getAllShapesOnPanel().remove(point);

							point.setX(x);
							point.setY(y);
							point.setColor(pointChoosedColor);
							point.draw(mainPanel.getGraphics());

							mainPanel.getAllShapesOnPanel().add(point);

							mainPanel.paint(mainPanel.getGraphics());
							dispose();
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Invalid input! X and Y must be number");
					}
				}
			}
		};

		modifyButton.addActionListener(modifyAction);
		gbc.gridx = 1;
		gbc.gridy = 6;
		modifyButton.setMinimumSize(new Dimension(200, 20));
		modifyButton.setMaximumSize(new Dimension(200, 20));
		modifyButton.setPreferredSize(new Dimension(200, 20));
		add(modifyButton, gbc);

		setVisible(true);

	}

}
