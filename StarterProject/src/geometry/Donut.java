package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Donut extends Circle {
	private int innerRadius;
	private Color outerColor;
	private Color borderColor;

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
		double dFromCenter = this.getCenter().distance(x, y);
		return dFromCenter >this.innerRadius  && dFromCenter < getRadius() ;
	}

	@Override
	public boolean contains(Point point) {
		double dFromCenter = this.getCenter().distance(point.getX(), point.getY());
		return super.contains(point.getX(), point.getY()) && dFromCenter > this.innerRadius;
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
		int centerX = super.getCenter().getX();
		int centerY = super.getCenter().getY();
		int outerRadius = super.getRadius();

		g.setColor(outerColor);
		g.fillOval(centerX - outerRadius, centerY - outerRadius, outerRadius * 2, outerRadius * 2);
		g.setColor(borderColor);
		g.fillOval(centerX - innerRadius, centerY - innerRadius, innerRadius * 2, innerRadius * 2);

	}

	// String s = new String();
	// Circle c = new Circle();
	// Circle c = new Circle(3,2);
	// Circle x = super.getCenter();
	public int compareTo(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			return (int) (super.area(getInnerRadius()) - d.area(getInnerRadius())); //TODO: radius?
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

	public void setOuterColor(Color color) {
		this.outerColor = color;
	}

	public void setBorderColor(Color color) {
		this.borderColor = color;
	}

	public Color getOuterColor() {
		return this.outerColor;
	}

	public Color getBorderColor() {
		return this.borderColor;
	}

}
