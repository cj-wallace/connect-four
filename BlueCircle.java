import java.awt.Color;
public class BlueCircle extends Circle implements HasColor
{
   Color c;
   
   //blue circle constructor
   public BlueCircle(int rposition,int cposition)
   { 
      super(rposition,cposition,3);
      c = new Color(0,0,255);//blue
   }
   public Color getColor(){
      return c;
   }
   public void setColor(Color col){
      c = c;
   }
}
