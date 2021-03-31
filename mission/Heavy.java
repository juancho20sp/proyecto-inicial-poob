package mission;
import java.util.Stack;
public class Heavy extends Box{
    

    public Heavy(int positionX, int positionY){
        super(positionX, positionY);
    }

    @Override
    public void add(Stack<Box>[][] boxes,int values[][]){
        if(boxes[getPositionX()][getPositionY()].empty()){
            boxes[getPositionX()][getPositionY()].push(this);
            values[getPositionX()][getPositionY()]++;
        }
    }
}
