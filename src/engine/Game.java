package engine;

import java.util.*;

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
	private final static int BOARDHEIGHT = 5;
	private final static int BOARDWIDTH = 5;
	
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
	public static int getBoardheight() {
		return BOARDHEIGHT;
	}
	public static int getBoardwidth() {
		return BOARDWIDTH;
	}
	
	
	public Game(Player first, Player second) throws Exception {
		this.firstPlayer = first;
		this.secondPlayer = second;
		this.board = new Object[5][5];
		this.placeChampions();
		this.placeCovers();
		availableAbilities = new ArrayList<Ability>();
		availableChampions = new ArrayList<Champion>();
		this.turnOrder = new PriorityQueue(6);
		
	}
	
	private void placeChampions()throws Exception {
		if(firstPlayer.getTeam().size()>0 && firstPlayer.getTeam().size()<4 && secondPlayer.getTeam().size()>0 && secondPlayer.getTeam().size()<5) {
			this.firstPlayer.getTeam().get(0).setLocation(new Point(0,1));
			this.board[0][1] = this.firstPlayer.getTeam().get(0);
			this.firstPlayer.getTeam().get(1).setLocation(new Point(0,2));
			this.board[0][2] = this.firstPlayer.getTeam().get(1);
			this.firstPlayer.getTeam().get(2).setLocation(new Point(0,3));
			this.board[0][3] = this.firstPlayer.getTeam().get(2);
			
			this.secondPlayer.getTeam().get(0).setLocation(new Point(4,1));
			this.board[4][1] = this.secondPlayer.getTeam().get(0);
			this.secondPlayer.getTeam().get(1).setLocation(new Point(4,2));
			this.board[4][2] = this.secondPlayer.getTeam().get(1);
			this.secondPlayer.getTeam().get(2).setLocation(new Point(4,3));
			this.board[4][3] = this.secondPlayer.getTeam().get(2);
		}


	}
	
	private void placeCovers() {
		Random rand = new Random();
		int i = 0;
		
		int randomX = rand.nextInt(5);
		int randomY = rand.nextInt(3) + 1;
		while(i<5) {
			if(this.board[randomY][randomX] == null ) {
				this.board[randomY][randomX] = new Cover(randomY,randomX);
				i++;
			}
			randomX = rand.nextInt(5);
			randomY = rand.nextInt(3) + 1;

		}

	}
	
	public static void loadAbilities(String filePath) throws Exception {
		BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
		String stream = csvReader.readLine();
		String [] data;
		while (stream != null) {
			data =  stream.split(",");
		    String name = data[1];
		    int manaCost = Integer.parseInt(data[2]);
		    int castRange =  Integer.parseInt(data[3]);
		    int baseCooldown =  Integer.parseInt(data[4]);
		    AreaOfEffect area = AreaOfEffect.valueOf(data[5]);
		    int required = Integer.parseInt(data[6]);
		    
		    Ability a = null;
		    
		    if(data[0].equals("CC")) {
		    	String effectName = data[7];
		    	int duration = Integer.parseInt(data[8]);
		    	Effect e = null;

		    	if(effectName.equals("Disarm")) {
		    		 e = new Disarm(duration);
		    	}
		    	else if(effectName.equals("PowerUp")) {
		    		 e = new PowerUp(duration);
		    	}
		    	else if(effectName.equals("Shield")) {
		    		 e = new Shield(duration);
		    	}
		    	else if(effectName.equals("Silence")) {
		    		 e = new Silence(duration);
		    	}
		    	else if(effectName.equals("SpeedUp")) {
		    		 e = new SpeedUp(duration);
		    	}
		    	else if(effectName.equals("Embrace")) {
		    		 e = new Embrace(duration);
		    	}
		    	else if(effectName.equals("Root")) {
		    		 e = new Root(duration);
		    	}
		    	else if(effectName.equals("Shock")) {
		    		 e = new Shock(duration);
		    	}
		    	else if(effectName.equals("Dodge")) {
		    		 e = new Dodge(duration);
		    	}
		    	else if(effectName.equals("Stun")) {
		    		 e = new Stun(duration);
		    	}
		    	
		    	a = new CrowdControlAbility(name,manaCost,baseCooldown,castRange,area,required,e);
		    	
		    }
		    else if(data[0].equals("DMG")) {
		    	int damage = Integer.parseInt(data[7]);
		    	a = new DamagingAbility(name,manaCost,baseCooldown,castRange,area,required,damage);
		    	
		    }
		    else if(data[0].equals("HEL")) {
		    	int heal = Integer.parseInt(data[7]);
		    	a = new HealingAbility(name,manaCost,baseCooldown,castRange,area,required,heal);
		    	
		    }
		    availableAbilities.add(a);
		    stream = csvReader.readLine();
		}
		csvReader.close();
	}
	
	public static void loadChampions(String filePath) throws Exception {
		BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
		String stream = csvReader.readLine();
		String [] data;
		while (stream != null) {
		    data =  stream.split(",");
		    String type = data[0];
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
		    //ArrayList<Ability> abilities = new ArrayList<Ability>();
		    
		    Champion c = null;
		    
		    if(type.equals("A")) {
		    	c = new AntiHero(name,maxHP,mana,actions,speed,attackRange,attackDamage);
		    	
		    }
		    else if(type.equals("H")) {
		    	c = new Hero(name,maxHP,mana,actions,speed,attackRange,attackDamage);
		    
		    }
		    else {
		    	c = new Villain(name,maxHP,mana,actions,speed,attackRange,attackDamage);
		    	
		    }
		    
		    for(int i = 0;i < availableAbilities.size();i++) {
		    	if(availableAbilities.get(i).getName().equals(ability1)) {
		    		c.getAbilities().add(availableAbilities.get(i));
		    	}
		    }
		    for(int i = 0;i < availableAbilities.size();i++) {
		    	if(availableAbilities.get(i).getName().equals(ability2)) {
		    		c.getAbilities().add(availableAbilities.get(i));
		    	}
		    }
		    for(int i = 0;i < availableAbilities.size();i++) {
		    	if(availableAbilities.get(i).getName().equals(ability3)) {
		    		c.getAbilities().add(availableAbilities.get(i));
		    	}
		    }

		    availableChampions.add(c);
		    stream = csvReader.readLine();
		}
		csvReader.close();
	}
	
	
}
