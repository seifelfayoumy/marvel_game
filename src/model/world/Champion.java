package model.world;
import  java.awt.*;
import java.util.ArrayList;

import model.abilities.Ability;
import model.effects.Effect;

public class Champion {
	private String name;
	public int getCurrentHP() {
		return currentHP;
	}
	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	public int getMaxActionpointsPerTurn() {
		return maxActionpointsPerTurn;
	}
	public void setMaxActionpointsPerTurn(int maxActionpointsPerTurn) {
		this.maxActionpointsPerTurn = maxActionpointsPerTurn;
	}
	public int getCurrentActionPoint() {
		return currentActionPoint;
	}
	public void setCurrentActionPoint(int currentActionPoint) {
		this.currentActionPoint = currentActionPoint;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public int getAttackRange() {
		return attackRange;
	}
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionpointsPerTurn;
	private int currentActionPoint;
	private int attackDamage;
	private int attackRange;
	private int speed;
	protected ArrayList<Ability> abilities;
	private ArrayList<Effect>appliedEffects;
	private Condition condition;
	private Point location;
	
	public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange,int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.maxActionpointsPerTurn = maxActions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.currentHP = maxHP;
		this.currentActionPoint = maxActions;
		
		this.abilities = new ArrayList<Ability>();
	}
	
	

}