package geometry;

import java.awt.Graphics;

public abstract class Shape implements Moveable, Comparable<Object>, Cloneable {
	protected boolean selected;
	
	public Shape() {}
	
	public Shape(boolean selected) {
		this.selected = selected;
	}
	
	
    public abstract boolean contains(int x, int y);
	
	public abstract void draw(Graphics g);

	public abstract Shape clone();

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void moveBy(int byX, int byY) {}

	public void moveTo(int x, int y) {}
	

	

}
