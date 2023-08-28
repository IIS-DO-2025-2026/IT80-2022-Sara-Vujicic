package sort;
    
    
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

	public class CircleApp extends JFrame {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private DefaultListModel<Circle> circleListModel;
	    private JList<Circle> circleList;

	    public CircleApp() {
	        setTitle("Vujicic Sara IT80/2022");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400, 300);
	        setLocationRelativeTo(null);

	        circleListModel = new DefaultListModel<>();
	        circleList = new JList<>(circleListModel);
	        circleList.setCellRenderer(new CircleCellRenderer());

	        JScrollPane scrollPane = new JScrollPane(circleList);

	        JPanel contentPane = new JPanel(new BorderLayout());
	        contentPane.add(scrollPane, BorderLayout.CENTER);

	        JButton addButton = new JButton("Dodaj krug");
	        addButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    double radius = Double.parseDouble(JOptionPane.showInputDialog("Unesite poluprečnik kruga:"));
	                    if (radius <= 0) {
	                        throw new IllegalArgumentException("Poluprečnik mora biti pozitivan broj.");
	                    }
	                    Circle circle = new Circle(radius);
	                    circleListModel.addElement(circle);
	                    sortCircleList();
	                } catch (NumberFormatException ex) {
	                    JOptionPane.showMessageDialog(null, "Pogrešan format za poluprečnik.", "Greška", JOptionPane.ERROR_MESSAGE);
	                } catch (IllegalArgumentException ex) {
	                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        contentPane.add(addButton, BorderLayout.SOUTH);

	        setContentPane(contentPane);
	    }

	    private void sortCircleList() {
	        List<Circle> circles = new ArrayList<>();
	        for (int i = 0; i < circleListModel.getSize(); i++) {
	            circles.add(circleListModel.getElementAt(i));
	        }
	        Collections.sort(circles, new Comparator<Circle>() {
	            @Override
	            public int compare(Circle c1, Circle c2) {
	                return Double.compare(c1.getArea(), c2.getArea());
	            }
	        });
	        circleListModel.clear();
	        for (Circle circle : circles) {
	            circleListModel.addElement(circle);
	        }
	    }

	    private static class Circle {
	        private double radius;

	        public Circle(double radius) {
	            this.radius = radius;
	        }

	        public double getArea() {
	            return Math.PI * radius * radius;
	        }

	        @Override
	        public String toString() {
	            return String.format("Povrsina kruga je: %.2f", getArea());
	        }
	    }

	    private static class CircleCellRenderer extends DefaultListCellRenderer {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            if (renderer instanceof JLabel && value instanceof Circle) {
	                Circle circle = (Circle) value;
	                ((JLabel) renderer).setText(circle.toString());
	            }
	            return renderer;
	        }
	    }
	    

		 public static void main(String[] args) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                CircleApp app = new CircleApp();
		                app.setVisible(true);
		            }
		        });
		    }

	   
	}


