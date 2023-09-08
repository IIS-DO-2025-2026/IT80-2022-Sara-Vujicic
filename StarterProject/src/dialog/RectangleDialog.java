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
import geometry.Rectangle;

public class RectangleDialog extends JDialog {

	/**
	 * 
	 */
	private Color borderChoosedColor = Color.BLACK;
	private Color innerChoosedColor = Color.WHITE;
	private static final long serialVersionUID = 1L;

	public RectangleDialog(Point point, PnlDrawing mainPanel) {
		setTitle("Add Rectangle");
		setSize(new Dimension(600, 400));

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Width: "), gbc);

		gbc.gridx = 1;
		JTextField widthField = new JTextField();
		widthField.setMinimumSize(new Dimension(200, 20));
		widthField.setMaximumSize(new Dimension(200, 20));
		widthField.setPreferredSize(new Dimension(200, 20));

		add(widthField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Height: "), gbc);

		gbc.gridx = 1;
		JTextField heightField = new JTextField();
		heightField.setMinimumSize(new Dimension(200, 20));
		heightField.setMaximumSize(new Dimension(200, 20));
		heightField.setPreferredSize(new Dimension(200, 20));

		add(heightField, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		JButton borderColorChooser = new JButton("Border Color");
		borderColorChooser.addActionListener(e -> {
			borderChoosedColor = JColorChooser.showDialog(null, "Choose border color", point.getColor());
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy =4;
		JButton innerColorChooser = new JButton("Inner Color");
		innerColorChooser.addActionListener(e -> {
			innerChoosedColor = JColorChooser.showDialog(null, "Choose inner color", point.getColor());
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
				String widthFieldText = widthField.getText();
				String heightFieldText = heightField.getText();
				boolean hasError = false;
				if (widthFieldText.isBlank() && heightFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Width and height cannot be blank!");
					hasError = true;
				} else if (widthFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Width cannot be blank!");
					hasError = true;
				} else if (heightFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Height cannot be blank!");
					hasError = true;
				}
				if (!hasError) {
					try {
						int width = Integer.parseInt(widthFieldText);
						int height = Integer.parseInt(heightFieldText);

						if (width < 0) {
							JOptionPane.showMessageDialog(null, "Width cannot be negative number!");
						} else if (width == 0) {
							JOptionPane.showMessageDialog(null, "Width cannot be zero!");
						} else if (height < 0) {
							JOptionPane.showMessageDialog(null, "Height cannot be negative number!");
						} else if (height == 0) {
							JOptionPane.showMessageDialog(null, "Height cannot be zero!");
						} else {

							Rectangle rectangle = new Rectangle(point, width, height);
							rectangle.setBorderColor(borderChoosedColor);
							rectangle.setInnerColor(innerChoosedColor);
							rectangle.draw(mainPanel.getGraphics());
							mainPanel.getAllShapesOnPanel().add(rectangle);
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

	public RectangleDialog(Rectangle rectangle, PnlDrawing mainPanel) {
		setTitle("Modify Rectangle");
		setSize(new Dimension(600, 400));

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Upper Left Point: "), gbc);

		gbc.gridy = 2;
		add(new JLabel("X: "), gbc);

		gbc.gridx = 1;
		JTextField xField = new JTextField();
		xField.setText(String.valueOf(rectangle.getUpperLeftPoint().getX()));
		xField.setMinimumSize(new Dimension(200, 20));
		xField.setMaximumSize(new Dimension(200, 20));
		xField.setPreferredSize(new Dimension(200, 20));

		add(xField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Y: "), gbc);

		gbc.gridx = 1;
		JTextField yField = new JTextField();
		yField.setText(String.valueOf(rectangle.getUpperLeftPoint().getY()));
		yField.setMinimumSize(new Dimension(200, 20));
		yField.setMaximumSize(new Dimension(200, 20));
		yField.setPreferredSize(new Dimension(200, 20));

		add(yField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		add(new JLabel("Width: "), gbc);

		gbc.gridx = 1;
		JTextField widthField = new JTextField();
		widthField.setText(String.valueOf(rectangle.getWidth()));
		widthField.setMinimumSize(new Dimension(200, 20));
		widthField.setMaximumSize(new Dimension(200, 20));
		widthField.setPreferredSize(new Dimension(200, 20));
		add(widthField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		add(new JLabel("Height: "), gbc);

		gbc.gridx = 1;
		JTextField heightField = new JTextField();
		heightField.setText(String.valueOf(rectangle.getHeight()));
		heightField.setMinimumSize(new Dimension(200, 20));
		heightField.setMaximumSize(new Dimension(200, 20));
		heightField.setPreferredSize(new Dimension(200, 20));
		add(heightField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 9;
		JButton borderColorChooser = new JButton("Border Color");
		borderColorChooser.addActionListener(e -> {
			borderChoosedColor = JColorChooser.showDialog(null, "Choose border color", rectangle.getBorderColor());
		});
		borderColorChooser.setMinimumSize(new Dimension(200, 20));
		borderColorChooser.setMaximumSize(new Dimension(200, 20));
		borderColorChooser.setPreferredSize(new Dimension(200, 20));
		add(borderColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 11;
		JButton innerColorChooser = new JButton("Inner Color");
		innerColorChooser.addActionListener(e -> {
			innerChoosedColor = JColorChooser.showDialog(null, "Choose inner color", rectangle.getInnerColor());
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
				String widthFieldText = widthField.getText();
				String heightFieldText = heightField.getText();
				String xFieldText = xField.getText();
				String yFieldText = yField.getText();
				boolean hasError = false;
				if (xFieldText.isBlank() && yFieldText.isBlank() && widthFieldText.isBlank()
						&& heightFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Width and height, x and y cannot be blank!");
					hasError = true;
				} else if (xFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "X field cannot be blank!");
					hasError = true;
				} else if (yFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Y field cannot be blank!");
					hasError = true;
				} else if (widthFieldText.isBlank() && heightFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Width and height cannot be blank!");
					hasError = true;
				} else if (widthFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Width cannot be blank!");
					hasError = true;
				} else if (heightFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Height cannot be blank!");
					hasError = true;
				}
				if (!hasError) {
					try {
						int x = Integer.parseInt(xField.getText());
						int y = Integer.parseInt(yField.getText());
						Point point = new Point(x, y);
						int width = Integer.parseInt(widthField.getText());
						int height = Integer.parseInt(heightField.getText());

						if (x < 0) {
							JOptionPane.showMessageDialog(null, "X cannot be negative number!");
						} else if (y < 0) {
							JOptionPane.showMessageDialog(null, "Y cannot be negative number!");
						} else if (width < 0) {
							JOptionPane.showMessageDialog(null, "Width cannot be negative number!");
						} else if (width == 0) {
							JOptionPane.showMessageDialog(null, "Width cannot be zero!");
						} else if (height < 0) {
							JOptionPane.showMessageDialog(null, "Height cannot be negative number!");
						} else if (height == 0) {
							JOptionPane.showMessageDialog(null, "Height cannot be zero!");
						} else {
							mainPanel.getAllShapesOnPanel().remove(rectangle);

							rectangle.setUpperLeftPoint(point);
							rectangle.setWidth(width);
							rectangle.setHeight(height);
							rectangle.setBorderColor(borderChoosedColor);
							rectangle.setInnerColor(innerChoosedColor);

							mainPanel.getAllShapesOnPanel().add(rectangle);
							rectangle.draw(mainPanel.getGraphics());

							mainPanel.paint(mainPanel.getGraphics());
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
		gbc.gridy = 13;
		saveButton.setMinimumSize(new Dimension(200, 20));
		saveButton.setMaximumSize(new Dimension(200, 20));
		saveButton.setPreferredSize(new Dimension(200, 20));
		add(saveButton, gbc);

	}

}
