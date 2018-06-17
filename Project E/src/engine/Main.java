package engine;

public class Main {

	private static Thread loop;
	
	public static void main(String[] args)
	{
		GameLoop m = new GameLoop();

		loop = new Thread() {
			public void run() {
				m.gameLoop();
			}
		};

		loop.start();

	}

}
