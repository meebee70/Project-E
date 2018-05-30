package engine;
import javax.swing.*;

import backgrounds.Background;
import backgrounds.BackgroundAberhart;
import backgrounds.GameBackGround;
import backgrounds.Tile;
import sprites.Barrier;
import sprites.Player;
import sprites.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * the main meat of the program, where all the stuff actually happens
 * @author Mr Wehnes
 *
 */
public class GameLoop extends JFrame {

	private static final long serialVersionUID = 6025644422779754466L;

	
	/**
	 * the speed at which the program should run
	 */
	final public static int FRAMES_PER_SECOND = 60;
	
	public static int SCREEN_HEIGHT = 900;
	public static int SCREEN_WIDTH = 900;

	/**
	 * if the camera should follow the player
	 */
	final public static boolean CENTER_ON_PLAYER = false;
	
    private JPanel panel = null;
    private JButton btnPauseRun;
    private JLabel lblTimeLabel;
    private JLabel lblTime;

    private static Thread loop;
    private Background gameBackground = new GameBackGround("res/backgrounds/grass.jpg"); 
    private Background shopBackground = new ShopBackground();
    private KeyboardInput keyboard = new KeyboardInput();
    
	private double score = 0;
	private State gameState = State.running;
	private State prevState = State.paused;
	
	/**
	 * 
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
	
	long current_time = 0;								//MILLISECONDS
	long next_refresh_time = 0;							//MILLISECONDS
	long last_refresh_time = 0;
	long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	long actual_delta_time = 0;							//MILLISECONDS
	long elapsed_time = 0;
	//private boolean isPaused = false;

	


    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    ArrayList<Sprite> spritesToDispose = new ArrayList<Sprite>();
    
    Sprite player1, player2;
    
    public GameLoop()
    {
        //super("Space Shooter"); replaced with "init()"
    	init("Space Shooter");
    	
    	

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
    	
    }
    
    /**
     * a method used on init to create all the starter sprites that will exist
     */
    private void createSprites() {
   		
    	addSprite(new Barrier(500,500));
    	
    	setPlayer1(new Player(this));
    	setPlayer2(new Player(this));
    	
    	for (Sprite sprite : sprites) {
    		sprite.setSprites(sprites);
    	}
    	
    }

    
    /**
     * Returns all collidable objects on screen
     */
    public ArrayList<Barrier> getBarriers() {
    	ArrayList<Barrier> output = new ArrayList<Barrier>();
    	for (Sprite sprite : sprites) {
    		if (sprite instanceof Barrier) {
    			output.add((Barrier) sprite);
    		}
    	}
    	return output;
    }
    
    
    
    /**
     * sets the new player1 sprite
     * @param newMe
     */
    private void setPlayer1(Sprite newPlayer1){
    	sprites.add(newPlayer1);
    	player1 = newPlayer1;
    }
    
    /**
     * sets the new player2 sprite
     * @param newPlayer2
     */
    private void setPlayer2(Sprite newPlayer2){
    	sprites.add(newPlayer2);
    	player1 = newPlayer2;
    }
    


	public static void main(String[] args)
	{
		GameLoop m = new GameLoop();


		loop = new Thread()
		{
			public void run()
			{
				m.gameLoop();
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
			
			SCREEN_HEIGHT = getHeight();
			SCREEN_WIDTH = getWidth();
			panel.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
			
			if (gameState.isRunning()){
				score += ((double)actual_delta_time / 1000) + (Math.log10(elapsed_time)/100); // adds score
				lblTime.setText(String.valueOf((int)score));
			
			
				updateTime();
				updateSprites();
				disposeSprites();
			}
			
			else if (gameState.isShopping()){
				
				
				
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
	}

	
	/**
	 * calls all sprites to update themselves according to their own update methods
	 */
	private void updateSprites() {
		for (Sprite sprite : sprites) {
			sprite.update(keyboard, actual_delta_time);
		}   
	}
	
	public void addSprite(Sprite s){
		sprites.add(s);
	}

	/**
	 * removes all sprites that need to be "disposed" of
	 */
	private void disposeSprites() {
		for (Sprite sprite : sprites) {
			if (sprite.getDispose() == true) {
				spritesToDispose.add(sprite);
			}
		}
		for (Sprite sprite : spritesToDispose) {
			sprites.remove(sprite);
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

		/**
		 * paints the JPanel
		 */
		public void paintComponent(Graphics g)
		{

			if (gameState.isRunning() || gameState.isPaused()){
				paintBackground(g, gameBackground);
	
				for (Sprite staticSprite : sprites) {
					g.drawImage(staticSprite.getImage(), (int)staticSprite.getXPos(), (int)staticSprite.getYPos(), (int)staticSprite.getWidth(), (int)staticSprite.getHeight(), null);
				}
			}
			else if (gameState.isShopping()){
				
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
}
