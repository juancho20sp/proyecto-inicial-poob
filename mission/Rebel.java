package mission;
import java.util.Stack;
public class Rebel extends Box{
    

    public Rebel(int positionX, int positionY){
        super(positionY, positionX);
    }

    @Override
    public void add(Stack<Box>[][] boxes,int[][] values){
        boxes[getPositionY()][getPositionX()].push(this);
        values[getPositionY()][getPositionX()]++;
    }
}
