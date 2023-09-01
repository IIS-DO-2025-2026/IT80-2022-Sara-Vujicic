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

import geometry.Line;
import geometry.PnlDrawing;
import geometry.Point;

public class LineDialog extends JDialog {

	/**
	 * 
	 */
	private Color lineChoosedColor = Color.BLACK;
	private static final long serialVersionUID = 1L;

	public LineDialog(Line line, PnlDrawing mainPanel) {

		setTitle("Modify Line");
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Start Point "), gbc);

		gbc.gridy = 1;
		add(new JLabel("X: "), gbc);

		gbc.gridx = 1;
		JTextField startPointXField = new JTextField();
		startPointXField.setText(String.valueOf(line.getStartPoint().getX()));
		startPointXField.setMinimumSize(new Dimension(200, 20));
		startPointXField.setMaximumSize(new Dimension(200, 20));
		startPointXField.setPreferredSize(new Dimension(200, 20));
		add(startPointXField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Y: "), gbc);

		gbc.gridx = 1;
		JTextField startPointYField = new JTextField();
		startPointYField.setText(String.valueOf(line.getStartPoint().getY()));
		startPointYField.setMinimumSize(new Dimension(200, 20));
		startPointYField.setMaximumSize(new Dimension(200, 20));
		startPointYField.setPreferredSize(new Dimension(200, 20));
		add(startPointYField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		add(new JLabel("End Point "), gbc);

		gbc.gridy = 5;
		add(new JLabel("X: "), gbc);
		gbc.gridx = 1;
		JTextField endPointXField = new JTextField();
		endPointXField.setText(String.valueOf(line.getEndPoint().getX()));
		endPointXField.setMinimumSize(new Dimension(200, 20));
		endPointXField.setMaximumSize(new Dimension(200, 20));
		endPointXField.setPreferredSize(new Dimension(200, 20));
		add(endPointXField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		add(new JLabel("Y: "), gbc);
		gbc.gridx = 1;
		JTextField endPointYField = new JTextField();
		endPointYField.setText(String.valueOf(line.getEndPoint().getY()));
		endPointYField.setMinimumSize(new Dimension(200, 20));
		endPointYField.setMaximumSize(new Dimension(200, 20));
		endPointYField.setPreferredSize(new Dimension(200, 20));
		add(endPointYField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 8;
		JButton lineColorChooser = new JButton("Color");
		lineColorChooser.addActionListener(e -> {
			lineChoosedColor = JColorChooser.showDialog(null, "Choose color", line.getColor());
		});
		lineColorChooser.setMinimumSize(new Dimension(200, 20));
		lineColorChooser.setMaximumSize(new Dimension(200, 20));
		lineColorChooser.setPreferredSize(new Dimension(200, 20));
		add(lineColorChooser, gbc);

		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String startPointXFieldText = startPointXField.getText();
				String startPointYFieldText = startPointYField.getText();

				String endPointXFieldText = endPointXField.getText();
				String endPointYFieldText = endPointYField.getText();
				boolean hasError = false;

				if (startPointXFieldText.isBlank() && startPointYFieldText.isBlank() && endPointXFieldText.isBlank()
						&& endPointYFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter start and end point");
					hasError = true;
				} else if (startPointXFieldText.isBlank() && startPointYFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter start point");
					hasError = true;
				} else if (endPointXFieldText.isBlank() && endPointYFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter end point");
					hasError = true;
				} else if (startPointXFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter X for start point");
					hasError = true;
				} else if (startPointYFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter Y for start point");
					hasError = true;
				} else if (endPointXFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter X for end point");
					hasError = true;
				} else if (endPointYFieldText.isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid input! Please enter Y for end point");
					hasError = true;
				}

				if (!hasError) {
					try {
						Integer startPointX = Integer.parseInt(startPointXFieldText);
						Integer startPointY = Integer.parseInt(startPointYFieldText);

						Integer endPointX = Integer.parseInt(endPointXFieldText);
						Integer endPointY = Integer.parseInt(endPointYFieldText);

						if (startPointX < 0) {
							JOptionPane.showMessageDialog(null, "Start point X cannot be negative number!");
						} else if (startPointY < 0) {
							JOptionPane.showMessageDialog(null, "Start point Y cannot be negative number!");
						} else if (endPointX < 0) {
							JOptionPane.showMessageDialog(null, "End point X cannot be negative number!");
						} else if (endPointY < 0) {
							JOptionPane.showMessageDialog(null, "End point Y cannot be negative number!");
						} else if (startPointX == endPointX && startPointY == endPointY) {
							JOptionPane.showMessageDialog(null, "Start and end point cannot be the same");
						} else {
							Point startPoint = new Point(startPointX, startPointY);
							Point endPoint = new Point(endPointX, endPointY);

							line.setStartPoint(startPoint);
							line.setEndPoint(endPoint);
							line.setColor(lineChoosedColor);

							mainPanel.getAllShapesOnPanel().add(line);
							line.draw(mainPanel.getGraphics());

							dispose();
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,
								"Invalid input! X and Y for start and end point must be number");
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
