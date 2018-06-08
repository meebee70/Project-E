package engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import sprites.Baddie;
import sprites.Dylan;
import sprites.Sprite;

public class BaddieGenerator {
	private final int cyclesPerBaddie = 10;
	private final int hardMax = 50;
	private int maxBaddies;
	private final int scorePerBaddie = 75;
	private int cycle = 0;
	private int baddies = 0;
	private Class<?> baddieType;
	
	private Random generator = new Random();
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * Initial Max Baddies = 5
	 * Score Per Baddie = 75
	 */
	public BaddieGenerator(Class<Baddie> type) {
		this.maxBaddies = 5;
		this.cycle = 0;
		this.baddieType = type;
	}
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * @param initialMaxBaddies
	 * @param scorePerBaddie
	 */
	public BaddieGenerator(Class<?> type, int initialMaxBaddies, int scorePerBaddie) {
		this.maxBaddies = initialMaxBaddies;
		this.cycle = 0;
		this.baddieType = type;
	}
	
	public void update(GameLoop game) {
		this.cycle++;
		updateBaddieCount(game.getSprites());
		if (game.getScore() >= scorePerBaddie * baddies) {
//			System.out.println("game score " + game.getScore());
			this.addBaddie();
		}
		
		if (maxBaddies > hardMax) {
			maxBaddies = hardMax;
		}
		
		
		if (cycle % cyclesPerBaddie == 0 && baddies < maxBaddies) {
			this.generateBaddie(game);
			System.out.println("Baddies: " + baddies + " Max: " + maxBaddies + " Type: " + baddieType.getName());
		}
	}
	
	private void generateBaddie(GameLoop game) {
		try {
			game.addSprite((Baddie) baddieType.getDeclaredConstructor(int.class, int.class).newInstance(generator.nextInt(900), -150));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			game.addSprite(new Dylan(generator.nextInt(900), -150));
			e.printStackTrace();
			System.out.println("Generated Default Baddie");
		}
	}
	
	private void updateBaddieCount(ArrayList<Sprite> sprites) {
		baddies = 0;
		for (Sprite object : sprites) {
			if (object.getClass().equals(baddieType.getClass())) {
				baddies++;
			}
		}
	}
	
	private void addBaddie() {
		this.maxBaddies++;
	}

}
