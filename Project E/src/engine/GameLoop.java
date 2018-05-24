package engine;
import javax.swing.*;

import backgrounds.Background;
import backgrounds.BackgroundAberhart;
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
	
	final public static int SCREEN_HEIGHT = 900;
	final public static int SCREEN_WIDTH = 900;

	/**
	 * if the camera should follow the player
	 */
	final public static boolean CENTER_ON_PLAYER = false;
	
    private JPanel panel = null;
    private JButton btnPauseRun;
    private JLabel lblTimeLabel;
    private JLabel lblTime;

    private static Thread loop;
    private BackgroundAberhart background = new BackgroundAberhart();    
    private KeyboardInput keyboard = new KeyboardInput();

	long current_time = 0;								//MILLISECONDS
	long next_refresh_time = 0;							//MILLISECONDS
	long last_refresh_time = 0;
	long minimum_delta_time = 1000 / FRAMES_PER_SECOND;	//MILLISECONDS
	long actual_delta_time = 0;							//MILLISECONDS
	long elapsed_time = 0;
	private boolean isPaused = false;

	/**
	 * the amount by which the player has moved in the x direction
	 */
	int xOffset = 0;
	/**
	 * the amount by which the player has moved in the y direction
	 */
	int yOffset = 0;


    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    ArrayList<Sprite> spritesToDispose = new ArrayList<Sprite>();
    
    Sprite player1 = null;
    Sprite player2 = null;
    
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
        cp.add(btnPauseRun);
        
        lblTime = new JLabel("000");
        lblTime.setForeground(Color.WHITE);
        lblTime.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTime.setBounds(171, 22, 302, 30);
        cp.add(lblTime);

        panel.setLayout(null);
        panel.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        //setSize(SCREEN_WIDTH + 20, SCREEN_HEIGHT + 36);
                        
        lblTimeLabel = new JLabel("Time: ");
        lblTimeLabel.setForeground(Color.WHITE);
        lblTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblTimeLabel.setBounds(78, 22, 97, 30);
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
   		
    	sprites.add(new Barrier(500,500));
    	
    	setPlayer1(new Player());
    	setPlayer2(new Player());
    	
    	for (Sprite sprite : sprites) {
    		sprite.setSprites(sprites);
    	}
    	
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
		boolean endGame = false;
		while (!endGame) { // main game loop

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
			updateTime();
			updateSprites();
			disposeSprites();

			//REFRESH
			this.repaint();

		}
	}

	/**
	 * uses the built in system functions to update the timer
	 */
	private void updateTime() {
		current_time = System.currentTimeMillis();
		actual_delta_time = (isPaused ? 0 : current_time - last_refresh_time);
		last_refresh_time = current_time;
		elapsed_time += actual_delta_time;

		this.lblTime.setText(Long.toString(elapsed_time));
	}

	
	/**
	 * calls all sprites to update themselves according to their own update methods
	 */
	private void updateSprites() {
		for (Sprite sprite : sprites) {
			sprite.update(keyboard, actual_delta_time);
		}    	
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
		if (isPaused) {
			isPaused = false;
			this.btnPauseRun.setText("||");
		}
		else {
			isPaused = true;
			this.btnPauseRun.setText(">");
		}
	}

	/**
	 * allows the interface to respond to specific key inputs
	 */
	private void handleKeyboardInput() {
		//if the interface needs to respond to certain keyboard events
		if (keyboard.keyDown(80) && ! isPaused) {
			btnPauseRun_mouseClicked(null);	
		}
		if (keyboard.keyDown(79) && isPaused ) {
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
			if (CENTER_ON_PLAYER && player1 != null) {
				xOffset = - ((int) player1.getXPos() - (SCREEN_WIDTH / 2));
				yOffset = - ((int) player1.getYPos() - (SCREEN_HEIGHT / 2));	        
			}

			paintBackground(g, background);


			for (Sprite staticSprite : sprites) {
				g.drawImage(staticSprite.getImage(), (int)staticSprite.getXPos() + xOffset, (int)staticSprite.getYPos() + yOffset, (int)staticSprite.getWidth(), (int)staticSprite.getHeight(), null);
			}

		}

		/**
		 * draws the background of the game
		 * @param g the graphics object to draw it to
		 * @param background the background to draw
		 */
		private void paintBackground(Graphics g, Background background) {
			//what tile covers the top-left corner?
			int xTopLeft = - xOffset;
			int yTopLeft = - yOffset;
			int row = background.getRow(yTopLeft);
			int col = background.getCol(xTopLeft);
			Tile tile = null;

			boolean rowDrawn = false;
			boolean colDrawn = false;
			while (colDrawn == false) {
				while (rowDrawn == false) {
					tile = background.getTile(col, row);
					g.drawImage(tile.getImage(), tile.getX() + xOffset, tile.getY() + yOffset, tile.getWidth(), tile.getHeight(), null);
					//does the RHE of this tile extend past the RHE of the visible area?
					int rheTile = tile.getX() + xOffset + tile.getWidth();
					if (rheTile > SCREEN_WIDTH || tile.isOutOfBounds()) {
						rowDrawn = true;
					}
					else {
						col++;
					}
				}
				//does the bottom edge of this tile extend past the bottom edge of the visible area?
				int bottomEdgeTile = tile.getY() + yOffset + tile.getHeight();
				if (bottomEdgeTile > SCREEN_HEIGHT || tile.isOutOfBounds()) {
					colDrawn = true;
				}
				else {
					col = background.getCol(xTopLeft);
					row++;
					rowDrawn = false;
				}
			}
		}				
	}
}
