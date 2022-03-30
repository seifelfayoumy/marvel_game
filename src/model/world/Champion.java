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
	public void setCurrentHP(int current) {
		if(current >= 0 && current<= this.maxHP) {
			this.currentHP = current;
		}else if(current<0) {
			this.currentHP = 0;
		}else {
			this.currentHP = this.maxHP;
		}
		
	}
	public int getMana() {
		return mana;
	}
	public void setMana(int mana) {
		this.mana = mana;
	}
	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}
	public void setMaxActionPointsPerTurn(int maxActionpointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionpointsPerTurn;
	}
	public int getCurrentActionPoints() {
		return currentActionPoints;
	}
	public void setCurrentActionPoints(int currentActionPoint) {
		this.currentActionPoints = currentActionPoint;
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
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackDamage;
	private int attackRange;
	private int speed;
	private ArrayList<Ability> abilities;
	private ArrayList<Effect>appliedEffects;
	private Condition condition;
	private Point location;
	
	public Champion(String name, int maxHP, int mana, int maxActions, int speed, int attackRange,int attackDamage) {
		this.name = name;
		this.maxHP = maxHP;
		this.mana = mana;
		this.maxActionPointsPerTurn = maxActions;
		this.speed = speed;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
		this.currentHP = maxHP;
		this.currentActionPoints = maxActions;
		
		this.abilities = new ArrayList<Ability>(3);
		this.appliedEffects = new ArrayList<Effect>();
		this.condition = Condition.ACTIVE;
		this.location = null;
		
	}
	
	

}