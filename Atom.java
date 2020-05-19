import java.util.ArrayList;

public class Atom {
    public char mElement;
    public double x_axis;
    public double y_axis;
    public double z_axis;
    private ArrayList<Atom> mNeighbours;
    public Atom(char mElement, double x_axis, double y_axis, double z_axis){
        this.mElement = mElement;
        this.x_axis = x_axis;
        this.y_axis = y_axis;
        this.z_axis = z_axis;
        mNeighbours = new ArrayList<>();
    }

    public void addNeighbour(Atom atom){
        mNeighbours.add(atom);
    }
    
    public void printAtom(){
        System.out.println("mElement -> "+mElement);
        System.out.print("Neighbours -> ");
        for(Atom i : mNeighbours){
            System.out.print(i.mElement + "   ");
        }
    }
    
}
