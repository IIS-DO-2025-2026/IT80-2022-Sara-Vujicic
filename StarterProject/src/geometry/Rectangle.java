package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape {
	private Point upperLeftPoint;
	private int width;
	private int height;
	private Color color = Color.black;
	private Color innerColor;

	public Rectangle() {
	}

	public Rectangle(Point upperLeftPoint, int width, int height) {
		this.upperLeftPoint = upperLeftPoint;
		this.width = width;
		this.height = height;

	}

	public Rectangle(Point upperLeftPoint, int width, int height, Color color, Color innerColor, boolean selected) {
		this(upperLeftPoint, width, height);
		this.color = color;
		this.innerColor = innerColor;
		this.selected = selected;
	}

	// Povrsina pravougaonika
	public int area(int width, int height) {
		return width * height;
	}

	// Obim pravougaonika
	public int circumference(int width, int height) {
		return 2 * width + 2 * height;
	}

	@Override
	public String toString() {
		return "Upper left point: " + upperLeftPoint + ", width = " + width + ", height = " + height;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			return (r.getUpperLeftPoint().equals(this.upperLeftPoint) && r.getHeight() == this.height
					&& r.getWidth() == this.width);
		} else {
			return false;
		}
	}

	// objasniti
	public boolean contains(int x, int y) {
		return (x >= upperLeftPoint.getX() && x <= upperLeftPoint.getX() + width && y >= upperLeftPoint.getY()
				&& y <= upperLeftPoint.getY() + height);
	}

	public boolean contains(Point point) {
		return contains(point.getX(), point.getY());
	}

	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getInnerColor() {
		return this.innerColor;

	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);
	}

	@Override
	public void moveTo(int x, int y) {
		this.upperLeftPoint.moveTo(x, y);
	}

	@Override
	public void moveBy(int byX, int byY) {
		this.upperLeftPoint.moveBy(byX, byY);
	}

	public int compareTo(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			return this.area(width, height) - r.area(width, height);
		} else {
			return 0;
		}
	}

}
