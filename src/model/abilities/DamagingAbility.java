package model.abilities;

public class DamagingAbility extends Ability {
	private int damageAmount;

	public int getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	
	public DamagingAbility(String name, int cost,int baseCooldown, int castRange, AreaOfEffect area, int required, int damageAmount) {
		super(name,cost,baseCooldown,castRange,area,required);
		this.damageAmount = damageAmount;
	}

}
