 package mission;

public class Simulator {
    private Mission mission;


    public Simulator(int length, int widht){
        mission = new Mission(length,widht);
    }

    public Simulator(int lenght, int widht,int[][] heights){
        mission = new Mission(lenght, widht, heights);
    }
    

    public void addBox(Box box){
        mission.store(box);
    }

    public void stealBox(Box box){
        mission.steal(box);
    }

    public void arrange(Box from, Box to){
        mission.arrange(from, to);
    }

    
}
