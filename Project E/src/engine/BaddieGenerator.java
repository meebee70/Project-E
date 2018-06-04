package engine;

import java.util.ArrayList;
import java.util.Random;

import sprites.Baddie;
import sprites.Dylan;
import sprites.Sprite;

public class BaddieGenerator {
	
	private final int cyclesPerDylan = 10;
	private final int hardMax = 35;
	private int maxBaddies = 5;
	private int scorePerBaddie = 75;
	private int cycle = 0;
	private int baddies = 0;
	private int usedScore = 0;
	
	private Random generator = new Random();
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * Initial Max Baddies = 5
	 * Score Per Baddie = 75
	 */
	public BaddieGenerator() {
		this.cycle = 0;
		this.usedScore = 0;
		this.generator = new Random();
	}
	
	/**
	 * Periodically generates Dylan objects above the screen
	 * @param initialMaxBaddies
	 * @param scorePerBaddie
	 */
	public BaddieGenerator(int initialMaxBaddies, int scorePerBaddie) {
		this.maxBaddies = initialMaxBaddies;
		this.cycle = 0;
		this.usedScore = 0;
	}
	
	public void update(GameLoop game) {
		this.cycle++;
		if (maxBaddies > hardMax) {
			maxBaddies = hardMax;
		}
		updateBaddieCount(game.getSprites());
		if (game.getScore() - usedScore >= scorePerBaddie) {
			this.addBaddies(1);
			usedScore += scorePerBaddie;
		}
		
		
		if (cycle % cyclesPerDylan == 0 && baddies <= maxBaddies) {
			game.addSprite(new Dylan(generator.nextInt(500), -50));
		}
	}
	
	private void updateBaddieCount(ArrayList<Sprite> sprites) {
		baddies = 0;
		for (Sprite object : sprites) {
			if (object instanceof Baddie) {
				baddies++;
			}
		}
	}
	
	private void addBaddies(int moreBaddies) {
		this.maxBaddies += moreBaddies;
	}

}
