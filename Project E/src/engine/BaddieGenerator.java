package engine;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import sprites.Baddie;
import sprites.Dylan;
import sprites.Sprite;

public class BaddieGenerator {
	private int hardMax = 25;
	private int respawnCycles = 20;
	private int cycles = 0;
	private int maxBaddies, baddies;
	private final int scorePerBaddie;
	private Class<?> baddieType;
	private final GameLoop game;
	
	private static Random generator = new Random();
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * Initial Max Baddies = 5
	 * Score Per Baddie = 75
	 */
	public BaddieGenerator(Class<Baddie> type, GameLoop game) {
		this.maxBaddies = 5;
		this.scorePerBaddie = 75;
		this.baddieType = type;
		this.game = game;
		if (maxBaddies > hardMax) {
			this.hardMax = maxBaddies;
		}
	}
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * @param initialMaxBaddies
	 * @param scorePerBaddie
	 * @param gameLoop
	 */
	public BaddieGenerator(Class<?> type, int initialMaxBaddies, int scorePerBaddie, GameLoop game) {
		this.scorePerBaddie = scorePerBaddie;
		this.maxBaddies = initialMaxBaddies;
		this.baddieType = type;
		this.game = game;
		if (maxBaddies > hardMax) {
			this.hardMax = maxBaddies;
		}
	}
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * @param initialMaxBaddies
	 * @param scorePerBaddie
	 * @param respawnCycles
	 * @param gameLoop
	 */
	public BaddieGenerator(Class<?> type, int initialMaxBaddies, int scorePerBaddie, int respawnCycles, GameLoop game) {
		this.scorePerBaddie = scorePerBaddie;
		this.maxBaddies = initialMaxBaddies;
		this.baddieType = type;
		this.game = game;
		this.respawnCycles = respawnCycles;
		if (maxBaddies > hardMax) {
			this.hardMax = maxBaddies;
		}
	}
	
	public void update() {
		cycles++;
		baddies = 0;
		for (Sprite object : game.getSprites()) {
			if (object.getClass().getName() == baddieType.getName()) {
				baddies++;
			}
		}
		
		if (baddies >= maxBaddies && game.getScore() >= scorePerBaddie * baddies) {
			maxBaddies++;
			if (maxBaddies > hardMax) {
				maxBaddies = hardMax;
			}
		}
		
		
		if (baddies < maxBaddies && cycles % respawnCycles == 0) {
			this.generateBaddie();
		}
	}
	
	private void generateBaddie() {
		for (int i = 0; i < maxBaddies - baddies; i++) {
			try {
				game.addSprite((Baddie) baddieType.getDeclaredConstructor(int.class, int.class).newInstance(generator.nextInt(game.SCREEN_WIDTH), -150));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				//If unable to generate proper Baddie
				game.addSprite(new Dylan(generator.nextInt(game.SCREEN_WIDTH), -150));
				e.printStackTrace();
				System.out.println("Generated Default Baddie");
			}
		}
	}
	
	public void setHardMax(int newMax) {
		this.hardMax = newMax;
	}

}
