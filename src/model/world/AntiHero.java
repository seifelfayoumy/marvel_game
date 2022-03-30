package model.world;

import java.util.ArrayList;

import model.abilities.Ability;

public class AntiHero extends Champion {
	public AntiHero(String name, int maxHP, int mana, int maxActions, int speed, int attackRange,int attackDamage) {
		super(name, maxHP,mana,maxActions,speed,attackRange,attackDamage);
		this.setCurrentHP(maxHP);
	}
}
