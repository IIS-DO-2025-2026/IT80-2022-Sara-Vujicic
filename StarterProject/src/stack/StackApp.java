package stack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class StackApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultListModel<Circle> circleListModel;
	private JList<Circle> circleList;
	private JButton addButton;
	private JButton removeButton;
	private Stack<Circle> circleStack;

	public StackApp() {
		circleStack = new Stack<>();
		circleListModel = new DefaultListModel<>();
		circleList = new JList<>(circleListModel);
		addButton = new JButton("Add Circle");
		removeButton = new JButton("Remove Circle");

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCircle();
			}
		});

		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeCircle();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);

		JScrollPane listScrollPane = new JScrollPane(circleList);

		getContentPane().add(listScrollPane);
		getContentPane().add(buttonPanel, "South");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setTitle("Circle Stack App");
	}

	private void addCircle() {
		Dialog inputDialog = new Dialog(this);
		Circle circle = inputDialog.showDialog();

		if (circle != null) {
			circleStack.push(circle);
			System.out.println("Dodat krug: " + circle + ", Površina: " + circle.getArea());
			updateCircleList();
		}
	}

	private void removeCircle() {
		if (!circleStack.isEmpty()) {
			circleStack.pop();
			updateCircleList();
		}
	}

	private void updateCircleList() {
		circleListModel.clear();
		for (Circle circle : circleStack) {
			circleListModel.addElement(circle);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				StackApp app = new StackApp();
				app.setVisible(true);
			}
		});
	}

}
