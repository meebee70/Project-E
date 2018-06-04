package engine;

import java.util.ArrayList;
import java.util.Random;

import sprites.Baddie;
import sprites.Dylan;
import sprites.Sprite;

public class BaddieGenerator {
	
	private final int cyclesPerDylan;
	private int maxBaddies = 30;
	private final int hardMax = 35;
	private int scorePerBaddie = 100;
	private int cycle, baddies, usedScore;
	private Random generator;
	
	public BaddieGenerator() {
		this.cycle = 0;
		this.usedScore = 0;
		this.cyclesPerDylan = 10;
		this.generator = new Random();
	}
	
	public void update(GameLoop game) {
		this.cycle++;
		if (maxBaddies > hardMax) {
			maxBaddies = hardMax;
		}
		updateBaddieCount(game.getSprites());
		while (game.getScore() - usedScore >= scorePerBaddie) {
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
	
	public void addBaddies(int moreBaddies) {
		this.maxBaddies += moreBaddies;
	}

}
