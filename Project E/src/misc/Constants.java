package misc;

public final class Constants {
	
	final public static int FPS = 90;
	
	//PlayerInputs
	final public static int playerOneLeft = 65;	//A
	final public static int playerOneRight = 68;//D
	final public static int playerOneUp = 87;	//W
	final public static int playerOneDown = 83;	//S
	
	final public static int playerTwoLeft = 37;	// <-
	final public static int playerTwoRight = 39;// ->
	final public static int playerTwoUp = 38;	//Up Arrow
	final public static int playerTwoDown = 40;	//Down Arrow
	
	//FireBalls
	final public static int playerOneFireLeft = 74;	//J
	final public static int playerOneFireRight = 76;//L
	final public static int playerOneFireUp = 73;	//I
	final public static int playerOneFireDown = 75;	//K
	
	final public static int playerTwoFireLeft = 97;	//1
	final public static int playerTwoFireRight = 99;//3
	final public static int playerTwoFireUp = 101;	//5
	final public static int playerTwoFireDown = 98;	//2
	
	//Misc keyboard
	final public static int spaceBar = 32;
	final public static int enterKey = 10;
	final public static int m = 77;
	
	//Stats
	final public static double moveSpeed = 2.6 * 60 / FPS;
	final public static double fireballSpeed = moveSpeed * 2.2;
	final public static double dylanSpeed = moveSpeed * 0.6;
	final public static int shotCooldown = 30 * 60 / FPS;
	final public static int snowmanTeleportCooldown = 5 * FPS;
	final public static int snowballCooldown = (int) (3 * FPS);
	final public static int upgradeCost = 500;
	
}
