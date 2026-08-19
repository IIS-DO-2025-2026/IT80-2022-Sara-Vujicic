package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {
	private Point center;
	private int radius;
	private Color innerColor = Color.WHITE;
	private Color borderColor = Color.BLACK;

	public Circle() {
	}

	public Circle(Point center, int radius) {
		this.radius = radius;
		this.center = center;
	}

	public Circle(Point center, int radius, boolean selected, Color innerColor, Color borderColor) {
		super(selected);
		this.center = center;
		this.radius = radius;
		this.innerColor = innerColor;
		this.borderColor = borderColor;

	}
	
	public Circle(Point center, int radius, Color innerColor, Color borderColor) {
		this.center = center;
		this.radius = radius;
		this.innerColor = innerColor;
		this.borderColor = borderColor;

	}

	
	public double circumference(int radius) {
		return 2 * radius * Math.PI;
	}

	@Override
	public String toString() {
		return "Center:" + center + "," + "radius:" + radius;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			return (this.getCenter().equals(c.getCenter()) && this.getRadius() == c.getRadius());
		}
		return false;
	}

	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
	}

	public boolean contains(Point point) {
		return this.center.distance(point.getX(), point.getY()) <= this.radius;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) throws Exception {
		if (radius < 0)
			throw new Exception("Radius must be a number greater than 0!");
		this.radius = radius;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getBorderColor() {
		return this.borderColor;
	}

	public Color getEdgeColor() {
		return this.borderColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.borderColor = edgeColor;
	}

	// Area of the circle
	public double area(int radius) {

		return radius * radius * Math.PI;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(innerColor);
		g.fillOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
		g.setColor(borderColor);
		g.drawOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.fillRect(center.getX() - 3, center.getY() - 3, 6, 6);
			g.fillRect(center.getX() - radius - 3, center.getY() - 3, 6, 6);
			g.fillRect(center.getX() + radius - 3, center.getY() - 3, 6, 6);
			g.fillRect(center.getX() - 3, center.getY() - radius - 3, 6, 6);
			g.fillRect(center.getX() - 3, center.getY() + radius - 3, 6, 6);
		}
	}

	@Override
	public void moveTo(int x, int y) {
		this.center.moveTo(x, y);

	}

	@Override
	public void moveBy(int byX, int byY) {
		this.center.moveBy(byX, byY);
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			return (int) (this.area(radius) - c.area(radius));
		} else {
			return 0;
		}
	}

	@Override
	public Circle clone() {
		return new Circle(this.center.clone(), this.radius, this.isSelected(), this.innerColor, this.borderColor);
	}
}
