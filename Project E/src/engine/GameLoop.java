package engine;
import javax.swing.*;

import backgrounds.Background;
import backgrounds.GameBackGround;
import backgrounds.ShopBackground;
import backgrounds.Tile;
import misc.Constants;
import sprites.Baddie;
import sprites.Barrier;
import sprites.Dylan;
import sprites.Player1;
import sprites.Player2;
import sprites.Snowman;
import sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * the main meat of the program, where all the stuff actually happens
 * Original author: Mr Wehnes
 * Editied and improved by Chris Kozbial and Alexander Aldridge
 *
 */
public class GameLoop extends JFrame {

	private static final long serialVersionUID = 6025644422779754466L;


	/**
	 * the speed at which the program should run
	 */
	final public static int FRAMES_PER_SECOND = (int) Constants.FPS;

	public final int SCREEN_HEIGHT = 750;
	public final int SCREEN_WIDTH = 900;

	/**
	 * if the camera should follow the player
	 */
	final public static boolean CENTER_ON_PLAYER = false;

	private JPanel panel = null;
	private JButton btnPauseRun;
	private JLabel lblTimeLabel;
	private JLabel lblTime;

	private static Thread loop;

	private Background gameBackground = new GameBackGround("res/backgrounds/8bitGrass.png"); 
	private Background shopBackground = new ShopBackground("res/backgrounds/money bag.png");

	private KeyboardInput keyboard = new KeyboardInput();

	private double score = 0;
	private State gameState = State.running;
	private State prevState = State.paused;
	
	
	private int selectedRow = 0;
	private int selectedCol = 0;
	private String[] playerOneUpgrades = 	{"Move Speed","Fire Rate",};
	private String[] playerTwoUpgrades = 	{"Move Speed","Fire Rate",};
	private String[] generalUpgrades = 		{"score multiplyer","extra life",};//these are subject to change
	
	private Font shopFont;
	private Font shopFontBold;

	long current_time = 0;								//MILLISECONDS
	long next_refresh_time = 0;							//MILLISECONDS
	long last_refresh_time = 0;
	long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	long actual_delta_time = 0;							//MILLISECONDS
	long elapsed_time = 0;
	//private boolean isPaused = false;

	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	ArrayList<Sprite> spritesToDispose = new ArrayList<Sprite>();

	Player1 player1, player2;
	BaddieGenerator dylanGenerator;
	BaddieGenerator snowmanGenerator;
	
	
	/**
	 * this is all possible "states" the game may exist in, and due to the way the game is set up, other game
	 * objects need not know the current state of the game.  Should that change, feel free to move this to its
	 * own class.
	 * @author Chris k
	 *
	 */
	private enum State{
		paused,
		running,
		shop,
		done;

		public boolean isPaused(){
			return this == paused;
		}

		public boolean isRunning(){
			return this == running;
		}

		public boolean isShopping(){
			return this == shop;
		}

		public boolean isDone(){
			return this == done;
		}
	}
	

	public GameLoop() {
		//super("Space Shooter"); replaced with "init()"
		init("Cross-Fire");

		try {
			shopFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("res/retroComputerFont.ttf"))).deriveFont(Font.PLAIN, 20);
			shopFontBold = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("res/retroComputerFont.ttf"))).deriveFont(Font.BOLD, 25);
		} catch (Exception e){}
		

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				keyboard.keyPressed(arg0);
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyboard.keyReleased(arg0);
			}
		});
		this.setFocusable(true);

		getContentPane().setBackground(Color.WHITE);
		Container cp = getContentPane();

		panel = new DrawPanel();
		cp.add(panel, BorderLayout.CENTER);
		cp.setLayout(null);

		btnPauseRun = new JButton("||");
		btnPauseRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnPauseRun_mouseClicked(arg0);
			}
		});

		btnPauseRun.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPauseRun.setBounds(20, 20, 48, 32);
		btnPauseRun.setFocusable(false);
		cp.add(btnPauseRun);

		lblTime = new JLabel("000");
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTime.setBounds(180, 25, 310, 30);
		cp.add(lblTime);

		panel.setLayout(null);
		panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		lblTimeLabel = new JLabel("Score: ");
		lblTimeLabel.setForeground(Color.WHITE);
		lblTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTimeLabel.setBounds(78, 22, 125, 30);
		cp.add(lblTimeLabel);

		cp.setComponentZOrder(lblTimeLabel, 0);
		cp.setComponentZOrder(lblTime, 0);
		cp.setComponentZOrder(btnPauseRun, 0);
		
		dylanGenerator = new BaddieGenerator(Dylan.class, 5, 65, this);
		dylanGenerator.setHardMax(30);
		snowmanGenerator = new BaddieGenerator(Snowman.class, 1, 3000, 500, this);
		snowmanGenerator.setHardMax(8);
		createSprites();

		setVisible(true); //this should not be touched    	    
	}
	
	/**
	 * Initializes all setting of the screen itself
	 * @param title this is the title of the program
	 */
	private void init(String title){
		setTitle(title);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

	}
	
	/**
	 * a method used on init to create all the starter sprites that will exist
	 */
	private void createSprites() {

		addSprite(new Barrier(500,500));
//		addSprite(new Snowman(0, 0));

		setPlayer1(new Player1());
		setPlayer2(new Player2());

		for (Sprite sprite : sprites) {
			sprite.setSprites(sprites);
		}

	}
	
	public static void main(String[] args)
	{
		GameLoop m = new GameLoop();

		loop = new Thread()
		{
			public void run()
			{
				m.gameLoop();//why is this it's own thread?
			}
		};

		loop.start();

	}
	
	/**
	 * the main method for the program
	 */
	private void gameLoop() {
		while (!gameState.isDone()) { // main game loop

			/*
			 * adapted from http://www.java-gaming.org/index.php?topic=24220.0
			 */
			last_refresh_time = System.currentTimeMillis();
			next_refresh_time = current_time + minimum_delta_time;

			if (current_time < next_refresh_time) {
				Thread.yield();

				try {
					Thread.sleep(next_refresh_time - current_time);
				} catch(Exception e) {
					e.printStackTrace();
				}

				this.updateTime();
			}

			//read input
			keyboard.poll();
			handleKeyboardInput();

			//UPDATE STATE
			if (gameState.isRunning()){
				score += ((double)actual_delta_time / 1000) + (Math.log10(elapsed_time)/100); // adds score
				lblTime.setText(String.valueOf((int)score));

				dylanGenerator.update();
				snowmanGenerator.update();
				

				updateSprites();
				disposeSprites();
				
				if (!sprites.contains(player1)){
					if (!sprites.contains(player2)){
						if (score > 5000){
							player1.setLives(1);
							player2.setLives(1);
							sprites.clear();
							sprites.add(player1);
							sprites.add(player2);
							score -= 5000;
						}else{
							//gameState = State.done;
							//TODO implement a "done" game state
						}
					}
				}
			} else if (gameState.isShopping()){
				

			}
			//REFRESH
			this.repaint();
		}
	}

	
	public void endGame(){
		gameState = State.done;
	}

	/**
	 * uses the built in system functions to update the timer
	 */
	private void updateTime() {
		current_time = System.currentTimeMillis();
		actual_delta_time = (gameState.isPaused() ? 0 : current_time - last_refresh_time);
		last_refresh_time = current_time;
		elapsed_time += actual_delta_time;
		//System.out.println(actual_delta_time);
	}


	/**
	 * calls all sprites to update themselves according to their own update methods
	 */
	private void updateSprites() {
		for (int i = 0; i < sprites.size(); i++) {
			sprites.get(i).update(keyboard, this);
		}    	

	}
	
	/**
	 * Returns all collidable objects on screen
	 */
	public ArrayList<Sprite> getBarriers() {
		ArrayList<Sprite> output = new ArrayList<Sprite>();
		for (Sprite sprite : sprites) {
			if (sprite.isCollideable()) {
				output.add(sprite);
			}
		}
		return output;
	}

	/**
	 * sets the new player1 sprite
	 * @param newMe
	 */
	private void setPlayer1(Player1 newPlayer1){
		sprites.add(newPlayer1);
		player1 = newPlayer1;
	}

	/**
	 * sets the new player2 sprite
	 * @param newPlayer2
	 */
	private void setPlayer2(Player1 newPlayer2){
		sprites.add(newPlayer2);
		player2 = newPlayer2;
	}

	public void addSprite(Sprite s){
		sprites.add(s);
	}

	public ArrayList<Sprite> getSprites() {
		return sprites;
	}
	
	public Player1 getPlayer(int playerID) {
		if (playerID == 1) {
			return player1;
		} else {
			return player2;
		}
	}

	/**
	 * removes all sprites that need to be "disposed" of
	 */
	private void disposeSprites() {
		for (Sprite sprite : sprites) {
			if (sprite.getDispose()) {
				spritesToDispose.add(sprite);
			}
		}
		for (Sprite sprite : spritesToDispose) {
			sprites.remove(sprite);
			if (sprite instanceof Baddie) {
				this.score += ((Baddie) sprite).getScore();
			}
		}
		if (spritesToDispose.size() > 0) {
			spritesToDispose.clear();
		}
	}

	/**
	 * pauses the timer when the pause button is clicked
	 * @param arg0
	 */
	protected void btnPauseRun_mouseClicked(MouseEvent arg0) {
		if (gameState.isPaused()) {
			gameState = State.running;
			this.btnPauseRun.setText("||");
		}
		else if (gameState.isRunning()){
			gameState = State.paused;
			this.btnPauseRun.setText(">");
		}
	}

	/**
	 * allows the interface to respond to specific key inputs
	 */
	private void handleKeyboardInput() {
		//if the interface needs to respond to certain keyboard events
		if (keyboard.keyDown(80) && ! (gameState.isPaused())) {
			btnPauseRun_mouseClicked(null);	
		}
		if (keyboard.keyDown(79) && (gameState.isPaused())) {
			btnPauseRun_mouseClicked(null);
		}
		
		if (keyboard.keyDown(Constants.m)){ // debug tool for free points
			score += 10000;
		}
		
		//System.out.println(keyboard.keyDownOnce(Constants.spaceBar));
        if (keyboard.keyDownOnce(Constants.spaceBar)){
			if (gameState.isShopping()){
				gameState = prevState;
				
				lblTime.setLocation(178, 20);
				lblTimeLabel.setLocation(78,22);

			}else{
				prevState = gameState;
				gameState = State.shop;
				lblTime.setLocation(150, 600);
				lblTimeLabel.setLocation(50,602);
			}
			
		}
        
        if (gameState.isShopping()){
        	if (keyboard.keyDownOnce(Constants.playerTwoUp)){
        		selectedRow--;
        	}
        	if (keyboard.keyDownOnce(Constants.playerTwoDown)){
        		selectedRow++;
        	}
        	if (keyboard.keyDownOnce(Constants.playerTwoLeft)){
        		selectedCol--;
        	}if (keyboard.keyDownOnce(Constants.playerTwoRight)){
        		selectedCol++;
        	}
        	
        	if (selectedRow < 0){
        		selectedRow = generalUpgrades.length-1;
        	}else if (selectedRow > generalUpgrades.length-1){
        		selectedRow = 0;
        	}
        	
        	if (selectedCol < 0){
        		selectedCol = 2;
        	}else if (selectedCol > 2){
        		selectedCol = 0;
        	}
        	
        	
        	if (keyboard.keyDown(Constants.enterKey)){
        		//TODO make shop upgrades do things
        	}
        	
        	
        }
	}

	/**
	 * a custom JPanel that can be used for drawing the game more easily
	 * @author Mr Wehnes
	 *
	 */
	class DrawPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7200442706585427853L;

		private final int LEFT_SHOP_COL = 15;
		private final int RIGHT_SHOP_COL = 475;
		private final int CENTER_SHOP_COL = 225;
		
		/**
		 * paints the JPanel
		 */
		public void paintComponent(Graphics g)
		{

			if (gameState.isRunning() || gameState.isPaused()){
				paintBackground(g, gameBackground);

				for (int i = 0; i < sprites.size();i++) {
					g.drawImage(sprites.get(i).getImage(), (int)sprites.get(i).getXPos(), (int)sprites.get(i).getYPos(), (int)sprites.get(i).getWidth(), (int)sprites.get(i).getHeight(), null);
				}
			}
			else if (gameState.isShopping()){
				paintBackground(g, shopBackground);
				
				//draw player one upgrades
				g.setFont(shopFontBold);
				g.drawString("Player one upgrades", LEFT_SHOP_COL, 100);
				g.setFont(shopFont);
				for (int i = 0; i < playerOneUpgrades.length;i++){
					if (selectedRow == i && selectedCol == 0){
						g.setFont(shopFontBold);
						g.setColor(Color.RED);
						g.drawString(playerOneUpgrades[i], LEFT_SHOP_COL + 25, 150 + 35*i);
						
						g.setColor(Color.BLACK);
						g.setFont(shopFont);
					}else g.drawString(playerOneUpgrades[i], LEFT_SHOP_COL + 25, 150 + 35*i);
				}
				
				//draw player two upgrades
				g.setFont(shopFontBold);
				g.drawString("player two upgrades", RIGHT_SHOP_COL, 100);
				g.setFont(shopFont);
				for (int i = 0; i < playerOneUpgrades.length;i++){
					if (selectedRow == i && selectedCol == 1){
						g.setFont(shopFontBold);
						g.setColor(Color.RED);
						g.drawString(playerOneUpgrades[i], RIGHT_SHOP_COL + 25, 150 + 35*i);
						
						g.setColor(Color.BLACK);
						g.setFont(shopFont);
					}else g.drawString(playerTwoUpgrades[i], RIGHT_SHOP_COL + 25, 150 + 35*i);
				}
				
				//draw general upgrades
				g.setFont(shopFontBold);
				g.drawString("General game upgrades", CENTER_SHOP_COL, 300);
				g.setFont(shopFont);
				for (int i = 0; i < playerOneUpgrades.length;i++){
					if (selectedRow == i && selectedCol == 2){
						g.setFont(shopFontBold);
						g.setColor(Color.RED);
						g.drawString(generalUpgrades[i], CENTER_SHOP_COL + 25, 335 + 35*i);
						
						g.setColor(Color.BLACK);
						g.setFont(shopFont);
					}else g.drawString(generalUpgrades[i], CENTER_SHOP_COL + 25, 335 + 35*i);
				}
				
				
			}

		}

		/**
		 * draws the background of the game
		 * @param g the graphics object to draw it to
		 * @param background the background to draw
		 */
		private void paintBackground(Graphics g, Background background) {
			Tile i = background.getTile(0, 0);
			g.drawImage(i.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0, i.getWidth(), i.getHeight(), null);
		}				
	}

	public Point getPlayerLocation(int playerID) {
		Player1 selectedPlayer = this.getPlayer(playerID);
		return new Point(selectedPlayer.getXPos(), selectedPlayer.getYPos());
	}

	public Point getPlayerLocationCentered(int playerID) {
		Player1 selectedPlayer = this.getPlayer(playerID);
		return new Point(selectedPlayer.getCenterX(), selectedPlayer.getCenterY());
	}
	
	public int getScore() {
		return (int) this.score;
	}
}
