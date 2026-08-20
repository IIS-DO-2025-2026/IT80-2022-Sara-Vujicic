package hexagon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Hexagon implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int x;
    private int y;
    private int r;
    private Color borderColor;
    private Color areaColor;
    private boolean selected;

    public Hexagon(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.borderColor = Color.BLACK;
        this.areaColor = Color.WHITE;
        this.selected = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getAreaColor() {
        return areaColor;
    }

    public void setAreaColor(Color areaColor) {
        this.areaColor = areaColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private Polygon getPolygon() {
        Polygon polygon = new Polygon();
        for (int i = 0; i < 6; i++) {
            int px = (int) (x + r * Math.cos(i * Math.PI / 3));
            int py = (int) (y + r * Math.sin(i * Math.PI / 3));
            polygon.addPoint(px, py);
        }
        return polygon;
    }

    public boolean contains(int x, int y) {
        return getPolygon().contains(x, y);
    }

    public void paint(Graphics g) {
        Polygon polygon = getPolygon();
        if (areaColor != null) {
            g.setColor(areaColor);
            g.fillPolygon(polygon);
        }
        if (borderColor != null) {
            g.setColor(borderColor);
            g.drawPolygon(polygon);
        }
        if (selected) {
           
            g.setColor(Color.BLUE);
            for (int i = 0; i < polygon.npoints; i++) {
                g.fillRect(polygon.xpoints[i] - 3, polygon.ypoints[i] - 3, 6, 6);
            }
            g.fillRect(x - 3, y - 3, 6, 6);
        }
    }
}
