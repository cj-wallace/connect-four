import javax.swing.JFrame;
import java.util.Scanner;
public class ConnectFourRunner extends JFrame{
	private static final int WIDTH = 725;
	private static final int HEIGHT = 750;
      
	public ConnectFourRunner(int mode)
	{
      //create the game
		super("Connect Four Runner");      
		setSize(WIDTH,HEIGHT);
      ConnectFour game = new ConnectFour(mode);
      
      //set focus for game
      addKeyListener(game);
      game.setFocusable(true);
      
      //open game screen
      getContentPane().add(game);  
      setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

	public static void main( String args[] )
	{  
      //user inputs what color they want to be
      System.out.println("Player 1, enter 1 for Red or 2 for Blue:");
      Scanner sc = new Scanner(System.in);
      int mode = sc.nextInt()+1;
      
		ConnectFourRunner runs = new ConnectFourRunner(mode);
	}
}