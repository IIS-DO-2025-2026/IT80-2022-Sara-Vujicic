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
	private Color lineChoosedColor;
	private static final long serialVersionUID = 1L;

	public LineDialog(Line line, PnlDrawing mainPanel) {

		setTitle("Modify Line");
		setSize(new Dimension(400, 200));
		setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("First Point "), gbc);

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
			lineChoosedColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
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
				try {
					Integer startPointX = Integer.parseInt(startPointXField.getText());
					Integer startPointY = Integer.parseInt(startPointYField.getText());

					Integer endPointX = Integer.parseInt(endPointXField.getText());
					Integer endPointY = Integer.parseInt(endPointYField.getText());

					Point startPoint = new Point(startPointX, startPointY);
					Point endPoint = new Point(endPointX, endPointY);

					line.setStartPoint(startPoint);
					line.setEndPoint(endPoint);
					line.setColor(lineChoosedColor);

					mainPanel.getAllShapesOnPanel().add(line);
					line.draw(mainPanel.getGraphics());

					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
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
