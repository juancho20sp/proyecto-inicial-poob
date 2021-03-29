package mission;

public class Heavy extends Box{
    

    public Heavy(int positionX, int positionY,String color){
        super(positionX, positionY, color);
    }

    @Override
    public void add(Box[][] boxes){
        if(boxes[getPositionX()][getPositionY()] == null){
            boxes[getPositionX()][getPositionY()] = new Heavy(getPositionX(), getPositionY(), getColor());
        }
    }
}
