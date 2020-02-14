import java.awt.Color;
class Pointer implements HasColor
{
   int position;
   int mode;
   Color c;
   
   //constructor
   public Pointer(int pos, int mod){
      position = pos;
      mode = mod;
      changeColor();
   }
   //move left one space
   public void moveLeft(){
      if(position>0)position--;
   }
   
   //move right one space
   public void moveRight(){
      if(position<6)position++;
   }
   
   //change color based one what mode
   public void changeColor(){
      if(mode==2)c = new Color(255,0,0);
      if(mode==3)c = new Color(0,0,255);
   }
   
   //return statements
   public Color getColor(){
      return c;
   }
   public void setColor(Color col){
      c = c;
   }
   public int getPos(){
      return position;
   }
   public int getMode(){
      return mode;
   }
   public void changeMode(){
      if(mode==2)mode=3;
      else mode=2;
      changeColor();
   }
   public int getPosition(){
      return position;
   }
}