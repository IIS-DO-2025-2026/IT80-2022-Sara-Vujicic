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
	private Color choosedColor;
	private Color innerChoosedColor;
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
		gbc.gridy = 1;
		add(new JLabel("Height: "), gbc);

		gbc.gridx = 1;
		JTextField heightField = new JTextField();
		heightField.setMinimumSize(new Dimension(200, 20));
		heightField.setMaximumSize(new Dimension(200, 20));
		heightField.setPreferredSize(new Dimension(200, 20));

		add(heightField, gbc);

		setLocationRelativeTo(null);
		setVisible(true);

		JButton saveButton = new JButton("Save");
		ActionListener saveAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int width = Integer.parseInt(widthField.getText());
					int height = Integer.parseInt(heightField.getText());

					Rectangle rectangle = new Rectangle(point, width, height);
					rectangle.draw(mainPanel.getGraphics());
					mainPanel.getAllShapesOnPanel().add(rectangle);
					dispose();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
				}
				dispose();
			}
		};
		saveButton.addActionListener(saveAction);
		gbc.gridx = 1;
		gbc.gridy = 2;
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

		gbc.gridy = 1;
		add(new JLabel("X: "), gbc);

		gbc.gridx = 1;
		JTextField xField = new JTextField();
		xField.setText(String.valueOf(rectangle.getUpperLeftPoint().getX()));
		xField.setMinimumSize(new Dimension(200, 20));
		xField.setMaximumSize(new Dimension(200, 20));
		xField.setPreferredSize(new Dimension(200, 20));

		add(xField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Y: "), gbc);

		gbc.gridx = 1;
		JTextField yField = new JTextField();
		yField.setText(String.valueOf(rectangle.getUpperLeftPoint().getY()));
		yField.setMinimumSize(new Dimension(200, 20));
		yField.setMaximumSize(new Dimension(200, 20));
		yField.setPreferredSize(new Dimension(200, 20));

		add(yField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Width: "), gbc);

		gbc.gridx = 1;
		JTextField widthField = new JTextField();
		widthField.setText(String.valueOf(rectangle.getWidth()));
		widthField.setMinimumSize(new Dimension(200, 20));
		widthField.setMaximumSize(new Dimension(200, 20));
		widthField.setPreferredSize(new Dimension(200, 20));
		add(widthField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("Height: "), gbc);

		gbc.gridx = 1;
		JTextField heightField = new JTextField();
		heightField.setText(String.valueOf(rectangle.getHeight()));
		heightField.setMinimumSize(new Dimension(200, 20));
		heightField.setMaximumSize(new Dimension(200, 20));
		heightField.setPreferredSize(new Dimension(200, 20));
		add(heightField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 5;
		JButton colorChooser = new JButton("Color");
		colorChooser.addActionListener(e -> {
			choosedColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
		});
		add(colorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		JButton innerColorChooser = new JButton("Inner Color");
		innerColorChooser.addActionListener(e -> {
			innerChoosedColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
		});
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
					Point point = new Point(x, y);
					int width = Integer.parseInt(widthField.getText());
					int height = Integer.parseInt(heightField.getText());

					rectangle.setUpperLeftPoint(point);
					rectangle.setWidth(width);
					rectangle.setHeight(height);
					rectangle.setColor(choosedColor);
					rectangle.setInnerColor(innerChoosedColor);

					mainPanel.getAllShapesOnPanel().add(rectangle);
					rectangle.draw(getGraphics());

					dispose();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
				}
				dispose();
			}
		};
		saveButton.addActionListener(saveAction);
		gbc.gridx = 1;
		gbc.gridy = 7;
		add(saveButton, gbc);

	}

}
