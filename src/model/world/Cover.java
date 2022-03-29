package model.world;
import  java.awt.*;
import java.util.*;
public class Cover {
	private int currentHP;
	private Point location;
	public Cover(int x,int y) {
		this.location = new Point(x,y);
		this.currentHP = (int) Math.random()*900;
		
	}
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	public Point getLocation() {
		return location;
	}
	

}
