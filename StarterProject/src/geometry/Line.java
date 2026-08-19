package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape {
	private Point startPoint;
	private Point endPoint;
	private Color color = Color.BLACK;

	public Line() {
	}

	public Line(Point startPoint, Point endPoint) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Line(int startPoint1, int endPoint1, int startPoint2, int endPoint2) {
		this.startPoint = new Point(startPoint1, endPoint1);
		this.endPoint = new Point(startPoint2, endPoint2);
	}

	public Line(Point startPoint, Point endPoint, boolean selected) {
		this(startPoint, endPoint);
		this.selected = selected;
	}

	@Override
	public String toString() {
		return startPoint + " --> " + endPoint;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Line) {
			Line l = (Line) obj;
			return (this.getStartPoint().equals(l.getStartPoint()) && this.getEndPoint().equals(l.getEndPoint()));
		} else {
			return false;
		}
	}

	public double length() {
		return startPoint.distance(endPoint.getX(), endPoint.getY());
	}

	public boolean contains(int x, int y) {
		return startPoint.distance(x, y) + endPoint.distance(x, y) - length() <= 2;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.fillRect(startPoint.getX() - 3, startPoint.getY() - 3, 6, 6);
			g.fillRect(endPoint.getX() - 3, endPoint.getY() - 3, 6, 6);
			g.fillRect((startPoint.getX() + endPoint.getX()) / 2 - 3, (startPoint.getY() + endPoint.getY()) / 2 - 3, 6, 6);
		}
	}

	@Override
	public void moveTo(int x, int y) {
		this.startPoint.moveTo(x, y);
		this.endPoint.moveTo(x, y);

	}

	@Override
	public void moveBy(int byX, int byY) {
		this.startPoint.moveBy(byX, byY);
		this.endPoint.moveBy(byX, byY);

	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Line)
			return (int) (this.length() - ((Line) obj).length());
		return 0;
	}

	@Override
	public Line clone() {
		Line l = new Line(this.startPoint.clone(), this.endPoint.clone(), this.isSelected());
		l.setColor(this.getColor());
		return l;
	}
}
