package stack;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Dialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField xField;
	private JTextField yField;
	private JTextField radiusField;
	private Circle circle;
	private JLabel areaLabel;

	public Dialog(Frame owner) {
		super(owner, "Add Circle", true);
		init();
	}

	public Dialog(Frame owner, Circle circle) {
		super(owner, "Edit Circle", true);
		this.circle = circle;
		init();
		populateFields();
	}

	public Dialog(StackApp owner, Circle circle2) {

	}

	private void init() {
		xField = new JTextField(10);
		yField = new JTextField(10);
		radiusField = new JTextField(10);

		JPanel inputPanel = new JPanel(new GridLayout(3, 2));
		inputPanel.add(new JLabel("X:"));
		inputPanel.add(xField);
		inputPanel.add(new JLabel("Y:"));
		inputPanel.add(yField);
		inputPanel.add(new JLabel("Radius:"));
		inputPanel.add(radiusField);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (validateInput()) {
					saveCircle();
					dispose();
				}
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(getOwner());
	}

	private void populateFields() {
		xField.setText(String.valueOf(circle.getX()));
		yField.setText(String.valueOf(circle.getY()));
		radiusField.setText(String.valueOf(circle.getRadius()));
		updateAreaLabel();
	}

	private boolean validateInput() {
		try {
			int x = Integer.parseInt(xField.getText());
			int y = Integer.parseInt(yField.getText());
			int radius = Integer.parseInt(radiusField.getText());

			if (radius <= 0) {
				JOptionPane.showMessageDialog(this, "Radius mora biti veci od 0.", " Greska!",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			circle = new Circle(x, y, radius);
			return true;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Uneseni brojevi moraju biti cijeli.", "Greska!",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void saveCircle() {
		// Circle is already set in validateInput() method.
	}

	private void updateAreaLabel() {
		areaLabel.setText(String.format("%.2f", circle.getArea()));
	}

	public Circle showDialog() {
		setVisible(true);
		return circle;
	}

	public JLabel getAreaLabel() {
		return areaLabel;
	}

	public void setAreaLabel(JLabel areaLabel) {
		this.areaLabel = areaLabel;
	}

}
