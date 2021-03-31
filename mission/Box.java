package mission;
import java.util.Stack;

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
   
   
   public Box(int positionX, int positionY){
       this.positionX = positionX;
       this.positionY = positionY;
       this.color = "blue";
   }
   

   @Override
   public void add(Stack<Box>[][] boxes,int[][] values){
           
           boxes[positionX][positionY].push(this);
           values[positionX][positionY]++;
           
   }

   @Override
   public void steal(Stack<Box>[][] boxes,int[][] values,int stolenBoxes){
        boxes[positionX][positionY].pop();
        values[positionX][positionY]--;
        stolenBoxes++;
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

   public void setPositionX(int positionX){
       this.positionX = positionX;
   }

   public void setPositionY(int positionY){
        this.positionY = positionY;
    }
   public void setColor(String color){
        this.color = color;
   }

}
