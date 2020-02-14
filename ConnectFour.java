import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Canvas;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.io.*;
import sun.audio.*;

public class ConnectFour extends JPanel implements KeyListener
{
	private Timer timer;
	private static final int SLEEP = 70;//how long to wait between actions
   private Circle[][] game;//2d array
   private boolean hasWon;//if game has been won
   
   public Pointer pointer;//pointer for selecting column
   
   public ConnectFour(int mode)
   {
      game = new Circle[7][6];//7 columns, 6 rows
      hasWon = false;
      
      pointer = new Pointer(0,mode);//start with mode 2 (RED)
      
      setBackground(Color.GRAY);
      
      addKeyListener(this);//listener for key inputs
      
      ActionListener paintCaller = new ActionListener(){
			public void actionPerformed(ActionEvent event)
			{
				if(!hasWon)repaint();//after SLEEP milliseconds have passed and game is not won yet - paint is called
            
			}
		};	
      //how often to act - what to use when acting
		timer = new Timer(SLEEP,paintCaller);
		timer.start();
      
      //create empty Circle array
      for(int r = 0; r < game.length; r++){
         for(int c = 0; c < game[r].length; c++){
            game[r][c] = new Circle(r,c,1);
         }
      }
   }
   
   //paint
   public void paintComponent( Graphics window )
	{  
      //refresh screen
      window.setColor(Color.LIGHT_GRAY);
      window.fillRect(0,0,getWidth(),getHeight());
      
      //check if red has won
      if(checkHasWon(2)){//if red won
         window.setColor(new Color(0,0,0));
         window.drawString("RED HAS WON",getWidth()/2,50);
         hasWon=true;
       }
       //check if blue has won
       if(checkHasWon(3)){//if blue won
         window.setColor(new Color(0,0,0));
         window.drawString("BLUE HAS WON",getWidth()/2,50);
         hasWon=true;
       }
      //draw pointer
      window.setColor(pointer.getColor());
      window.fillPolygon(new int[] {pointer.position*100+30, pointer.position*100+50, pointer.position*100+70}, new int[] {60, 90, 60},3);
      //draw gameboard
      window.setColor(new Color(237, 215,14));
      window.fillRect(1,95,700,600);
      for(int r = 0; r < game.length; r++){
         for(int c = 0; c < game[r].length; c++){
            window.setColor(new Color(0,0,0));
            window.drawRect(r*100+1,c*100+95,100,100);
            window.setColor(game[r][c].getColor());//get color from game[r][c]
            window.fillOval(r*100+6,c*100+100,90,90);
         }
      }
           
   }
   //drop circle at position, with mode circleMode
   public void addCircle(int position, int circleMode){
      if(game[position][0].isEmpty()){
         int i = 0;
         while(i<game[0].length-1&&game[position][i+1].getMode()==1){
            i++;
         }
         if(circleMode == 2){
            game[position][i] = new RedCircle(i,position);
         }
         else if(circleMode == 3){
            game[position][i] = new BlueCircle(i,position);
         }
      }      
   }
   
   //when keys get pressed
   public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == 65||e.getKeyCode() == 37){//(a) - left
         pointer.moveLeft();
      }
      else if(e.getKeyCode() == 68||e.getKeyCode() == 39){//(d) - right
         pointer.moveRight();      }
      else if(e.getKeyCode() == 83||e.getKeyCode() == 40){//(s) - drop
         if(game[pointer.position][0].getMode()==1){
            addCircle(pointer.getPosition(),pointer.getMode());
            pointer.position=0;
            pointer.changeMode();
         }
      }
   }
   public void keyReleased(KeyEvent e){}//inherited from KeyListener
   
   public void keyTyped(KeyEvent e){}//inherited from KeyListener
   
   //checks columns, rows, and diagonals for 4 in a rows
   //checks if num has won
   public boolean checkHasWon(int num){
      if(checkColumns()==num || checkRows()==num|| checkDiagonals()==num)
         return true;
      else return false;
   }
   
   //check columns
   public int checkColumns(){
      // Check rows and see if there are 4 disks of the same color
        for (int column = 0; column < game[0].length; ++column) {
            int count = 0;
            // We will compare current element with the previous
            for (int row = 1; row < game.length; ++row) {
                if (game[row][column].getMode() != 1 &&
                    game[row][column].getMode() == game[row-1][column].getMode())
                    ++count;
                else
                    count = 1;

                // Check if there are 4 in a row.
                if (count >= 4) {
                    // Return color of the winner
                    return game[row][column].getMode();
                }
            }
        }
        // Otherwise return   character, which means nobody win in rows.
        return -1;
   }
   
   //check rows
   public int checkRows(){
      // Check rows and see if there are 4 disks of the same color
        for (int row = 0; row < game.length; ++row) {
            int count = 0;
            // We will compare current element with the previous
            for (int column = 1; column < game[0].length; ++column) {
                if (game[row][column].getMode() != 1 &&
                    game[row][column].getMode() == game[row][column-1].getMode())
                    ++count;
                else
                    count = 1;

                // Check if there are 4 in a row.
                if (count >= 4) {
                    // Return color of the winner
                    return game[row][column].getMode();
                }
            }
        }
        // Otherwise return   character, which means nobody win in rows.
        return -1;
   }
   
   //check diagonals
   public int checkDiagonals(){
      // Check diagonals, if there are 4 or more disks of the same color - return winner color]
        // There are 2 kinds of diagonals, let's check those that go from top-left to bottom right

        // There are diagonals, that starts on top of each column, let's check them
        for (int column = 0; column < game[0].length; ++column) {
            int count = 0;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < game.length; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column + row >= game[0].length) break;
                if (game[row][column+row].getMode() != 1 &&
                    game[row-1][column + row - 1].getMode() == game[row][column+row].getMode())
                    ++count;
                else
                    count = 1;
                if (count >= 4) return game[row][column+row].getMode();
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < game.length; ++row) {
            int count = 0;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int column = 1; column < game[0].length; ++column) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column + row >= 7) break;
                if (game[row + column][column].getMode() != 1 &&
                    game[row+column - 1][column - 1].getMode() == game[row + column][column].getMode())
                    ++count;
                else
                    count = 1;
                if (count >= 4) return game[row + column][column].getMode();
            }
        }

        // Now we need to do the same for diagonals that go from top-right to bottom-left
        // There are diagonals, that starts on top of each column, let's check them
        for (int column = 0; column < game[0].length; ++column) {
            int count = 0;
            // Traverse diagonal that starts at [0][column], we start with the first row,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int row = 1; row < game.length; ++row) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column - row < 0) break;
                if (game[row][column-row].getMode() != 1 &&
                    game[row - 1][column - row + 1].getMode() == game[row][column-row].getMode())
                    ++count;
                else
                    count = 1;
                if (count >= 4) return game[row][column-row].getMode();
            }
        }

        // There are diagonals, that starts on left of each row, let's check them
        for (int row = 0; row < game.length; ++row) {
            int count = 0;
            // Traverse diagonal that starts at [row][0], we start with the first column,
            // because we will compare elements with the previous one, similar to how
            // we did this for rows and columns
            for (int column = game[0].length-2; column >= 0; --column) {
                // Coordinates an the diagonal change as [row + i][column + i], 
                // so we stop when column can get outside of the range
                if (column - row <= 0) break;
                if (game[column - row][column].getMode() != 1 &&
                    game[column - row - 1][column + 1].getMode() == game[column - row][column].getMode())
                    ++count;
                else
                    count = 1;
                if (count >= 4) return game[column - row][column].getMode();
            }
        }
        return -1;
   }
}
