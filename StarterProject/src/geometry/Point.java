package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {
	private int x;
	private int y;
	private Color color=Color.BLACK;

	
	
	public Point() {}
	
	public Point(int x,int y) {
		this.x=x;
		this.y=y;
	}
	public Point(int x,int y,boolean selected) {
		this(x,y);
		this.selected=selected;
	}
	
	public Point(int x,int y,Color color) {
		this(x,y);
		this.color=color;
	}
	public Point(int x,int y,boolean selected,Color color) {
		this(x,y,selected);
		this.color=color;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Point) {
			Point p = (Point) obj;
			return (this.x == p.x && this.y == p.y);
			
		}else {
			return false;
		}
	}
	
	public int distance(int x2, int y2) {
		int dx = x2 - x;
		int dy = y2 - y;
		int d = (int) Math.sqrt(dx*dx + dy*dy);
		return d;
	}
	
	public boolean contains(int x, int y) {
		return this.distance(x,y) <= 5;
	}
	

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setColor(Color color) {
		this.color=color;
	}
	
	public Color getColor() {
		return this.color;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x - 5, y - 5, 5, 5);	
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.fillRect(x - 3, y - 3, 6, 6);
		}
	}

   @Override
   public void moveTo(int x,int y) {
	   this.x=x;
	   this.y=y;
   }
   
   
   @Override
   public void moveBy(int byX,int byY) {
	  this.x+=byX;
	  this.y+=byY;
   }
   
   @Override
   public int compareTo(Object obj) {
		if(obj instanceof Point) {
			Point p=(Point)obj;
			return  (int) this.distance(x, y)-p.distance(x, y);
			}else {
				return 0;}
	}

	@Override
	public Point clone() {
		return new Point(x, y, isSelected(), color);
	}
}
   
	
	


