package model.abilities;

import model.effects.Effect;

public class CrowdControlAbility extends Ability {

	private Effect effect;

	public Effect getEffect() {
		return effect;
	}
	
	public CrowdControlAbility(String name, int cost,int baseCooldown, int castRange, AreaOfEffect area, int required, Effect e) {
		super(name,cost,baseCooldown,castRange,area,required);
		this.effect = e;
		
	}
}
