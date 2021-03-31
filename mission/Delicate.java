package mission;
import java.util.Stack;
public class Delicate extends Box{
    private String Color;
    public Delicate(int positionX, int positionY){
        super(positionX,positionY);
        this.setColor("red");
    }

    @Override
    public void add(Stack<Box> boxes[][],int [][] values){
            boxes[getPositionX()][getPositionY()].push(this);
            values[getPositionX()][getPositionY()]++;
    }


}
