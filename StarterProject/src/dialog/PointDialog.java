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
	private Color pointChoosedColor;
	private static final long serialVersionUID = 1L;

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
		gbc.gridy = 1;
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
		gbc.gridy = 2;
		JButton pointColorChooser = new JButton("Color");
		pointColorChooser.addActionListener(e -> {
			pointChoosedColor = JColorChooser.showDialog(null, "Choose color", Color.BLACK);
		});
		add(pointColorChooser, gbc);

		setLocationRelativeTo(null);

		JButton modifyButton = new JButton("Save");
		ActionListener modifyAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer x = Integer.parseInt(xField.getText());
					Integer y = Integer.parseInt(yField.getText());

					point.setX(x);
					point.setY(y);
					point.setColor(pointChoosedColor);
					point.draw(mainPanel.getGraphics());

					mainPanel.getAllShapesOnPanel().add(point);

					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid input!");
				}
			}
		};

		modifyButton.addActionListener(modifyAction);
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(modifyButton, gbc);

		setVisible(true);
	}

}
