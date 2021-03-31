package mission;
import java.util.Stack;
public class Safe extends Box{
    
    public Safe(int positionX,int positionY){
        super(positionX, positionY);
    }


    @Override
    public void add(Stack<Box>[][] boxes,int values[][]){
        boxes[getPositionX()][getPositionY()].push(this);
        values[getPositionX()][getPositionY()]++;
    }

    @Override
    public void steal(Stack<Box>[][] boxes,int values[][],int stolenBoxes){

    }


}