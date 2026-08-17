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
import geometry.Point;

public class PointDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private Color pointChosenColor = Color.BLACK;
	private boolean confirmed = false;
	private Point point;

	public PointDialog(java.awt.Frame parent, int x, int y) {
		super(parent, "Add Point", true);
		if (parent instanceof mvc.DrawingFrame) {
			this.pointChosenColor = ((mvc.DrawingFrame) parent).getActiveEdgeColor();
		}
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(parent);

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
			Color newColor = JColorChooser.showDialog(null, "Choose color", pointChosenColor);
			if (newColor != null) {
				pointChosenColor = newColor;
			}
		});
		pointColorChooser.setMinimumSize(new Dimension(200, 20));
		pointColorChooser.setMaximumSize(new Dimension(200, 20));
		pointColorChooser.setPreferredSize(new Dimension(200, 20));
		add(pointColorChooser, gbc);

		setLocationRelativeTo(parent);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point p = new Point(x, y);
				p.setColor(pointChosenColor);
				PointDialog.this.point = p;
				PointDialog.this.confirmed = true;
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
	}

	public PointDialog(java.awt.Frame parent, Point point) {
		super(parent, "Modify Point", true);
		this.pointChosenColor = point.getColor();
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(parent);

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
			Color newColor = JColorChooser.showDialog(null, "Choose color", pointChosenColor);
			if (newColor != null) {
				pointChosenColor = newColor;
			}
		});
		pointColorChooser.setMinimumSize(new Dimension(200, 20));
		pointColorChooser.setMaximumSize(new Dimension(200, 20));
		pointColorChooser.setPreferredSize(new Dimension(200, 20));
		add(pointColorChooser, gbc);

		setLocationRelativeTo(parent);

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
							Point p = new Point(x, y);
							p.setColor(pointChosenColor);
							PointDialog.this.point = p;
							PointDialog.this.confirmed = true;
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
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public Point getPoint() {
		return point;
	}
}
