package mission;
import java.util.ArrayList;

/**
 * Write a description of class Box here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Box implements BoxActions{
   private int positionX;
   private int positionY;
   private String color;
   
   
   public Box(int positionX, int positionY, String color){
       this.positionX = positionX;
       this.positionY = positionY;
       this.color = color;
   }
   

   @Override
   public void add(Box[][] boxes){
        boxes[positionX][positionY] = new Box(positionX,positionY,color);
   }

   @Override
   public void steal(Box[][] boxes){
    boxes[positionX][positionY] = null;
   }
   

   public int getPositionX(){
       return this.positionX;
   }
   public int getPositionY(){
       return this.positionY;
   }

   public String getColor(){
       return this.color;
   }
}
