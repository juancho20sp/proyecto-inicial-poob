package mission;

public class Rebel extends Box{
    

    public Rebel(int positionX, int positionY,String color){
        super(positionY, positionX, color);
    }

    @Override
    public void add(Box[][] boxes){
        boxes[getPositionY()][getPositionX()] = new Rebel( getPositionX(),getPositionY(), getColor());
    }
}
