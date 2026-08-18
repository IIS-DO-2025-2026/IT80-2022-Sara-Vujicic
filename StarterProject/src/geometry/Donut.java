package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {
	private int innerRadius;
	private Color innerColor = Color.WHITE;
	private Color borderColor = Color.BLACK;

	public Donut() {
	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		this.setSelected(selected);

	}

	@Override
	public boolean contains(int x, int y) {
		int centerX = this.getCenter().getX();
		int centerY = this.getCenter().getY();
		int outerRadius = this.getRadius();

		Ellipse2D outerEllipse = new Ellipse2D.Double(
				centerX - outerRadius, centerY - outerRadius, outerRadius * 2, outerRadius * 2);
		Ellipse2D innerEllipse = new Ellipse2D.Double(
				centerX - innerRadius, centerY - innerRadius, innerRadius * 2, innerRadius * 2);

		Area outerArea = new Area(outerEllipse);
		Area innerArea = new Area(innerEllipse);
		outerArea.subtract(innerArea);

		return outerArea.contains(x, y);
	}

	@Override
	public boolean contains(Point point) {
		return this.contains(point.getX(), point.getY());
	}

	@Override
	public double area(int radius) {
		return super.area(radius) - innerRadius * innerRadius * Math.PI;
	}

	@Override
	public String toString() {
		return super.toString() + ", innerRadius = " + this.innerRadius;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (super.equals(d) && this.innerRadius == d.innerRadius) {
				return true;
			} else {
				return false;
			}
		} else {

			return false;
		}
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int centerX = this.getCenter().getX();
		int centerY = this.getCenter().getY();
		int outerRadius = this.getRadius();

		Ellipse2D outerEllipse = new Ellipse2D.Double(
				centerX - outerRadius, centerY - outerRadius, outerRadius * 2, outerRadius * 2);
		Ellipse2D innerEllipse = new Ellipse2D.Double(
				centerX - innerRadius, centerY - innerRadius, innerRadius * 2, innerRadius * 2);

		Area outerArea = new Area(outerEllipse);
		Area innerArea = new Area(innerEllipse);
		outerArea.subtract(innerArea);

		g2d.setColor(this.getInnerColor());
		g2d.fill(outerArea);

		g2d.setColor(this.getEdgeColor());
		g2d.draw(outerEllipse);
		g2d.draw(innerEllipse);

		if (this.isSelected()) {
			drawSelectionSquares(g2d, centerX, centerY, outerRadius, innerRadius);
		}
	}

	private void drawSelectionSquares(Graphics2D g2d, int centerX, int centerY, int outerRadius, int innerRadius) {
		g2d.setColor(Color.BLUE);

		
		drawSingleSelectionSquare(g2d, centerX - outerRadius, centerY);
		drawSingleSelectionSquare(g2d, centerX + outerRadius, centerY);
		drawSingleSelectionSquare(g2d, centerX, centerY - outerRadius);
		drawSingleSelectionSquare(g2d, centerX, centerY + outerRadius);

		
		drawSingleSelectionSquare(g2d, centerX - innerRadius, centerY);
		drawSingleSelectionSquare(g2d, centerX + innerRadius, centerY);
		drawSingleSelectionSquare(g2d, centerX, centerY - innerRadius);
		drawSingleSelectionSquare(g2d, centerX, centerY + innerRadius);

		
		drawSingleSelectionSquare(g2d, centerX, centerY);
	}

	private void drawSingleSelectionSquare(Graphics2D g2d, int x, int y) {
		g2d.fillRect(x - 3, y - 3, 6, 6);
	}

	public int compareTo(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			return (int) (super.area(getInnerRadius()) - d.area(getInnerRadius()));
		} else {
			return 0;
		}
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public void setBorderColor(Color color) {
		this.borderColor = color;
	}

	public Color getBorderColor() {
		return this.borderColor;
	}

	@Override
	public Color getEdgeColor() {
		return this.borderColor;
	}

	@Override
	public void setEdgeColor(Color edgeColor) {
		this.borderColor = edgeColor;
	}

	@Override
	public Donut clone() {
		Donut d = new Donut(this.getCenter().clone(), this.getRadius(), this.innerRadius, this.isSelected());
		d.setInnerColor(this.getInnerColor());
		d.setEdgeColor(this.getEdgeColor());
		return d;
	}
}
