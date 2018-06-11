package engine;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import sprites.Baddie;
import sprites.Dylan;
import sprites.Sprite;

public class BaddieGenerator {
	private int hardMax = 25;
	private int maxBaddies;
	private final int scorePerBaddie;
	private int baddies = 0;
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
		this.baddieType = type;
		this.game = game;
		this.scorePerBaddie = 75;
	}
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * @param initialMaxBaddies
	 * @param scorePerBaddie
	 */
	public BaddieGenerator(Class<?> type, int initialMaxBaddies, int scorePerBaddie, GameLoop game) {
		this.scorePerBaddie = scorePerBaddie;
		this.maxBaddies = initialMaxBaddies;
		this.baddieType = type;
		this.game = game;
	}
	
	public void update() {
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
		
		
		if (baddies < maxBaddies) {
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
