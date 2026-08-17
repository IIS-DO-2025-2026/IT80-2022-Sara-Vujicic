package dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import geometry.HexagonAdapter;
import geometry.Point;

public class HexagonDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    private Color innerChosenColor = Color.WHITE;
    private Color borderChosenColor = Color.BLACK;
    private boolean confirmed = false;
    private HexagonAdapter hexagonAdapter;

    public HexagonDialog(Frame parent, Point point) {
        super(parent, "Add Hexagon", true);
        if (parent instanceof mvc.DrawingFrame) {
            this.borderChosenColor = ((mvc.DrawingFrame) parent).getActiveEdgeColor();
            this.innerChosenColor = ((mvc.DrawingFrame) parent).getActiveInnerColor();
        }
        setSize(new Dimension(400, 200));
        setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Radius: "), gbc);

        gbc.gridx = 1;
        JTextField radiusField = new JTextField();
        radiusField.setMinimumSize(new Dimension(200, 20));
        radiusField.setMaximumSize(new Dimension(200, 20));
        radiusField.setPreferredSize(new Dimension(200, 20));
        add(radiusField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JButton innerColorChooser = new JButton("Inner color");
        innerColorChooser.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose your inner color", innerChosenColor);
            if (newColor != null) {
                innerChosenColor = newColor;
            }
        });
        innerColorChooser.setMinimumSize(new Dimension(200, 20));
        innerColorChooser.setMaximumSize(new Dimension(200, 20));
        innerColorChooser.setPreferredSize(new Dimension(200, 20));
        add(innerColorChooser, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton borderColorChooser = new JButton("Border color");
        borderColorChooser.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose your border color", borderChosenColor);
            if (newColor != null) {
                borderChosenColor = newColor;
            }
        });
        borderColorChooser.setMinimumSize(new Dimension(200, 20));
        borderColorChooser.setMaximumSize(new Dimension(200, 20));
        borderColorChooser.setPreferredSize(new Dimension(200, 20));
        add(borderColorChooser, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String radiusFieldText = radiusField.getText();
                boolean hasError = false;

                if (radiusFieldText.isBlank()) {
                    JOptionPane.showMessageDialog(null, "Radius field cannot be blank.");
                    hasError = true;
                }
                if (!hasError) {
                    try {
                        int radius = Integer.parseInt(radiusFieldText);
                        if (radius < 0) {
                            JOptionPane.showMessageDialog(null, "Radius cannot be negative number!");
                        } else if (radius == 0) {
                            JOptionPane.showMessageDialog(null, "Radius cannot be zero!");
                        } else {
                            HexagonAdapter h = new HexagonAdapter(point, radius);
                            h.setInnerColor(innerChosenColor);
                            h.setEdgeColor(borderChosenColor);
                            HexagonDialog.this.hexagonAdapter = h;
                            HexagonDialog.this.confirmed = true;
                            dispose();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Radius field cannot convert to integer.");
                    }
                }
            }
        });
        gbc.gridy = 4;
        saveButton.setMinimumSize(new Dimension(200, 20));
        saveButton.setMaximumSize(new Dimension(200, 20));
        saveButton.setPreferredSize(new Dimension(200, 20));
        add(saveButton, gbc);
    }

    public HexagonDialog(Frame parent, HexagonAdapter hexagon) {
        super(parent, "Modify Hexagon", true);
        this.innerChosenColor = hexagon.getInnerColor();
        this.borderChosenColor = hexagon.getEdgeColor();
        setSize(new Dimension(400, 250));
        setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("X: "), gbc);

        gbc.gridx = 1;
        JTextField xField = new JTextField();
        xField.setText(String.valueOf(hexagon.getCenter().getX()));
        xField.setMinimumSize(new Dimension(200, 20));
        xField.setMaximumSize(new Dimension(200, 20));
        xField.setPreferredSize(new Dimension(200, 20));
        add(xField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Y: "), gbc);

        gbc.gridx = 1;
        JTextField yField = new JTextField();
        yField.setText(String.valueOf(hexagon.getCenter().getY()));
        yField.setMinimumSize(new Dimension(200, 20));
        yField.setMaximumSize(new Dimension(200, 20));
        yField.setPreferredSize(new Dimension(200, 20));
        add(yField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Radius: "), gbc);

        gbc.gridx = 1;
        JTextField radiusField = new JTextField();
        radiusField.setText(String.valueOf(hexagon.getRadius()));
        radiusField.setMinimumSize(new Dimension(200, 20));
        radiusField.setMaximumSize(new Dimension(200, 20));
        radiusField.setPreferredSize(new Dimension(200, 20));
        add(radiusField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        JButton innerColorChooser = new JButton("Inner color");
        innerColorChooser.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose your inner color", innerChosenColor);
            if (newColor != null) {
                innerChosenColor = newColor;
            }
        });
        innerColorChooser.setMinimumSize(new Dimension(200, 20));
        innerColorChooser.setMaximumSize(new Dimension(200, 20));
        innerColorChooser.setPreferredSize(new Dimension(200, 20));
        add(innerColorChooser, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        JButton borderColorChooser = new JButton("Border color");
        borderColorChooser.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Choose your border color", borderChosenColor);
            if (newColor != null) {
                borderChosenColor = newColor;
            }
        });
        borderColorChooser.setMinimumSize(new Dimension(200, 20));
        borderColorChooser.setMaximumSize(new Dimension(200, 20));
        borderColorChooser.setPreferredSize(new Dimension(200, 20));
        add(borderColorChooser, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String xFieldText = xField.getText();
                String yFieldText = yField.getText();
                String radiusFieldText = radiusField.getText();
                boolean hasError = false;

                if (xFieldText.isBlank() || yFieldText.isBlank() || radiusFieldText.isBlank()) {
                    JOptionPane.showMessageDialog(null, "X, Y and radius for hexagon cannot be blank.");
                    hasError = true;
                }
                if (!hasError) {
                    try {
                        int x = Integer.parseInt(xFieldText);
                        int y = Integer.parseInt(yFieldText);
                        int radius = Integer.parseInt(radiusFieldText);
                        if (radius < 0) {
                            JOptionPane.showMessageDialog(null, "Radius cannot be negative number!");
                        } else if (radius == 0) {
                            JOptionPane.showMessageDialog(null, "Radius cannot be zero!");
                        } else {
                            hexagon.setCenter(new Point(x, y));
                            hexagon.setRadius(radius);
                            hexagon.setInnerColor(innerChosenColor);
                            hexagon.setEdgeColor(borderChosenColor);
                            HexagonDialog.this.hexagonAdapter = hexagon;
                            HexagonDialog.this.confirmed = true;
                            dispose();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Coordinates and radius must be integers.");
                    }
                }
            }
        });
        gbc.gridy = 10;
        saveButton.setMinimumSize(new Dimension(200, 20));
        saveButton.setMaximumSize(new Dimension(200, 20));
        saveButton.setPreferredSize(new Dimension(200, 20));
        add(saveButton, gbc);
    }

    public HexagonAdapter getHexagon() {
        return hexagonAdapter;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
