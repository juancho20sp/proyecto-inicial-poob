package mission;
import java.util.ArrayList;
import java.util.Stack;

public interface BoxActions {
    

    void add(Stack<Box>[][] boxes,int[][] values);

    void steal(Stack<Box>[][] boxes,int[][]values,int stolenBoxes);
}
