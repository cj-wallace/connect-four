import java.awt.Color;
import java.awt.Graphics;
public class Circle
{
   public int rpos;
   public int cpos;
   public int mode;
   
   //empty circle constructor
   public Circle(int rposition,int cposition, int modeNum)
   { 
      rpos = rposition;
      cpos = cposition;
      mode = modeNum;
   }
   
   //if mode is 1, it is empty
   public boolean isEmpty(){
      return mode==1;
   }
   
   //return statements
   public int getMode()
   {  
      return mode;  
   }   
   public Color getColor(){
      return new Color(255,255,255);//return white
   }
}