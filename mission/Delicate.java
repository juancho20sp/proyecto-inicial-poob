package mission;

public class Delicate extends Box{

    public Delicate(int positionX, int positionY, String color){
        super(positionX, positionY, color);
    }

    @Override
    public void add(Box boxes[][]){
        boxes[getPositionX()][getPositionY()] = new Delicate(getPositionX(),getPositionY(), getColor());
    }

    
}
