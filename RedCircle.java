import java.awt.Color;
public class RedCircle extends Circle implements HasColor
{
   Color c;
   
   //red circle constructor
   public RedCircle(int rposition,int cposition)
   { 
      super(rposition, cposition, 2);
      c = new Color(255,0,0);//red
   }
   public Color getColor(){
      return c;
   }
   public void setColor(Color col){
      c = c;
   }
}
