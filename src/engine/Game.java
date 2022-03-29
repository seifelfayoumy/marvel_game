package engine;

import java.util.ArrayList;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.effects.*;
import model.effects.Effect;
import model.world.AntiHero;
import model.world.Champion;
import model.world.Cover;
import model.world.Hero;
import model.world.Villain;

import java.awt.Point;
import java.io.*;


public class Game {
	private Player firstPlayer;
	private Player secondPlayer;
	private boolean firstLeaderAbilityUsed;
	private boolean secondLeaderAbilityUsed;
	private Object[][] board;
	private static ArrayList<Champion> availableChampions;
	private static ArrayList<Ability> availableAbilities;
	private PriorityQueue turnOrder;
	private static int BOARDHEIGHT;
	private static int BOARDWIDTH;
	
	public Player getFirstPlayer() {
		return firstPlayer;
	}
	public Player getSecondPlayer() {
		return secondPlayer;
	}
	public boolean isFirstLeaderAbilityUsed() {
		return firstLeaderAbilityUsed;
	}
	public boolean isSecondLeaderAbilityUsed() {
		return secondLeaderAbilityUsed;
	}
	public Object[][] getBoard() {
		return board;
	}
	public static ArrayList<Champion> getAvailableChampions() {
		return availableChampions;
	}
	public static ArrayList<Ability> getAvailableAbilities() {
		return availableAbilities;
	}
	public PriorityQueue getTurnOrder() {
		return turnOrder;
	}
	public static int getBOARDHEIGHT() {
		return BOARDHEIGHT;
	}
	public static int getBOARDWIDTH() {
		return BOARDWIDTH;
	}
	
	
	public Game(Player first, Player second) {
		this.firstPlayer = first;
		this.secondPlayer = second;
		this.board = new Object[5][5];
		BOARDHEIGHT = 5;
		BOARDWIDTH = 5;
		this.placeChampions();
		this.placeCovers();
		
	}
	
	private void placeChampions() {
		this.firstPlayer.getTeam().get(0).setLocation(new Point(0,1));
		this.board[0][1] = this.firstPlayer.getTeam().get(0);
		this.firstPlayer.getTeam().get(1).setLocation(new Point(0,2));
		this.board[0][2] = this.firstPlayer.getTeam().get(1);
		this.firstPlayer.getTeam().get(2).setLocation(new Point(0,3));
		this.board[0][3] = this.firstPlayer.getTeam().get(2);
		
		this.secondPlayer.getTeam().get(0).setLocation(new Point(BOARDHEIGHT-1,1));
		this.board[BOARDHEIGHT-1][1] = (Champion) this.secondPlayer.getTeam().get(0);
		this.secondPlayer.getTeam().get(1).setLocation(new Point(BOARDHEIGHT-1,2));
		this.board[BOARDHEIGHT-1][2] = (Champion) this.secondPlayer.getTeam().get(1);
		this.secondPlayer.getTeam().get(2).setLocation(new Point(BOARDHEIGHT-1,3));
		this.board[BOARDHEIGHT-1][3] = (Champion) this.secondPlayer.getTeam().get(2);

	}
	
	private void placeCovers() {
		int i = 0;
		int randomX = (int) Math.random()*5;
		int randomY = (int) Math.random()*5;
		while(this.board[randomX][randomY] == null && i<5) {
			this.board[randomX][randomY] = new Cover(randomX,randomY);
			randomX = (int) Math.random()*5;
			randomY = (int) Math.random()*5;
			i++;
		}
	}
	
	public static void loadAbilities(String filePath) throws Exception {
		BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
		while (csvReader.readLine() != null) {
		    String[] data = csvReader.readLine().split(",");
		    String name = data[1];
		    int manaCost = Integer.parseInt(data[2]);
		    int castRange =  Integer.parseInt(data[3]);
		    int baseCooldown =  Integer.parseInt(data[4]);
		    AreaOfEffect area = AreaOfEffect.valueOf(data[5]);
		    int required = Integer.parseInt(data[6]);
		    
		    if(data[0].equals("CC")) {
		    	String effectName = data[7];
		    	int duration = Integer.parseInt(data[8]);
		    	Effect e = null;
		    	switch(effectName) {
		    	case "Disarm":
		    		 e = new Disarm(effectName,duration);
		    		break;
		    	case "PowerUp":
		    		 e = new PowerUp(effectName,duration);
		    		 break;
		    	case "Shield":
		    		 e = new Shield(effectName,duration);
		    		 break;
		    	case "Silence":
		    		 e = new Silence(effectName,duration);
		    		 break;
		    	case "SpeedUp":
		    		 e = new SpeedUp(effectName,duration);
		    		 break;
		    	case "Embrace":
		    		 e = new Embrace(effectName,duration);
		    		 break;
		    	case "Root":
		    		 e = new Root(effectName,duration);
		    		 break;
		    	case "Shock":
		    		 e = new Shock(effectName,duration);
		    		 break;
		    	case "Dodge":
		    		 e = new Dodge(effectName,duration);
		    		 break;
		    	case "Stun":
		    		 e = new Stun(effectName,duration);
		    		 break;
		    	
		    	}
		    	CrowdControlAbility a = new CrowdControlAbility(name,manaCost,baseCooldown,castRange,area,required,e);
		    	availableAbilities.add(a);
		    }
		    else if(data[0].equals("DMG")) {
		    	int damage = Integer.parseInt(data[7]);
		    	DamagingAbility d = new DamagingAbility(name,manaCost,baseCooldown,castRange,area,required,damage);
		    	availableAbilities.add(d);
		    }
		    else if(data[0].equals("HEL")) {
		    	int heal = Integer.parseInt(data[7]);
		    	HealingAbility h = new HealingAbility(name,manaCost,baseCooldown,castRange,area,required,heal);
		    	availableAbilities.add(h);
		    }
		}
		csvReader.close();
	}
	
	public static void loadChampions(String filePath) throws Exception {
		BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
		while (csvReader.readLine() != null) {
		    String[] data = csvReader.readLine().split(",");
		    
		    String name = data[1];
		    int maxHP = Integer.parseInt(data[2]);
		    int mana = Integer.parseInt(data[3]);
		    int actions = Integer.parseInt(data[4]);
		    int speed = Integer.parseInt(data[5]);
		    int attackRange = Integer.parseInt(data[6]);
		    int attackDamage = Integer.parseInt(data[7]);
		    String ability1 = data[8];
		    String ability2 = data[9];
		    String ability3 = data[10];
		    ArrayList<Ability> abilities = new ArrayList<Ability>();
		    for(int i = 0;i<availableAbilities.size();i++) {
		    	if(availableAbilities.get(i).getName().equals(ability1)) {
		    		abilities.add(availableAbilities.get(i));
		    	}
		    }
		    for(int i = 0;i<availableAbilities.size();i++) {
		    	if(availableAbilities.get(i).getName().equals(ability2)) {
		    		abilities.add(availableAbilities.get(i));
		    	}
		    }
		    for(int i = 0;i<availableAbilities.size();i++) {
		    	if(availableAbilities.get(i).getName().equals(ability3)) {
		    		abilities.add(availableAbilities.get(i));
		    	}
		    }
		    
		    if(data[0].equals("A")) {
		    	AntiHero a = new AntiHero(name,maxHP,mana,actions,speed,attackRange,attackDamage,abilities);
		    	availableChampions.add(a);
		    }
		    else if(data[0].equals("H")) {
		    	Hero h = new Hero(name,maxHP,mana,actions,speed,attackRange,attackDamage,abilities);
		    	availableChampions.add(h);
		    }
		    else if(data[0].equals("V")) {
		    	Villain v = new Villain(name,maxHP,mana,actions,speed,attackRange,attackDamage,abilities);
		    	availableChampions.add(v);
		    }
		}
		csvReader.close();
	}
	
	
}
