import java.util.ArrayList;
import java.util.HashMap;

public class Molecule {
    HashMap<Integer,Atom> mAtom_map = new HashMap<>();
    HashMap<Integer,ArrayList<Integer>> mNeighboursMap = new HashMap<>();
    HashMap<Integer,ArrayList<Integer>> mNextNearestMap = new HashMap<>();
    HashMap<Integer,Integer> mNextNearestCountMap = new HashMap<>();

    public Molecule() {}

    public void addAtom(Atom atom){
        mAtom_map.put(mAtom_map.size()+1,atom);
    }

    private double getDistance(Atom one, Atom two){
        return Math.sqrt(Math.pow(one.x_axis - two.x_axis,2) +
                Math.pow(one.y_axis - two.y_axis,2)+
                Math.pow(one.z_axis - two.z_axis,2));
    }

    private double getDotProduct(Atom one, Atom two, Atom three){
        return (two.x_axis - one.x_axis)*(three.x_axis - two.x_axis) +
                (two.y_axis - one.y_axis)*(three.y_axis - two.y_axis) +
                (two.z_axis - one.z_axis)*(three.z_axis - two.z_axis);
    }

    private Atom getNearestAtom(int atom){
        return mAtom_map.get(getNearestAtomIndex(atom));
    }

    private int getNearestAtomIndex(int atom)
    {
        double shortest_dist = 1;
        int out_num = 0;
        Atom cur;
        for(int i : mAtom_map.keySet()){
            if(i == atom){
                continue;
            }
            cur = mAtom_map.get(i);
            double dist = getDistance(mAtom_map.get(atom),cur);
            if(dist < shortest_dist){
                shortest_dist = dist;
                out_num = i;
            }
        }
        return  out_num;
    }

    public double getCOH_angle(){
        Atom hyd = getNearestAtom(5);
        Atom oxy = mAtom_map.get(5);
        Atom car = mAtom_map.get(2);
        return Math.acos(getDotProduct(car,oxy,hyd) / (getDistance(car,oxy) * getDistance(oxy,hyd)));
    }

    public void updateNeighbours(){
        ArrayList<Integer> temp;
        for(int i : mAtom_map.keySet()){
            temp =  new ArrayList<>();
            for (int j : mAtom_map.keySet()){
                if (j == i){
                    continue;
                }
                double dist = getDistance(mAtom_map.get(i),mAtom_map.get(j));
                if(dist < 1.5){
                    mAtom_map.get(i).addNeighbour(mAtom_map.get(j));
                    temp.add(j);
                }
            }
            mNeighboursMap.put(i,temp);
        }
    }

    public void updateNextNearestNeighbours(){
        for(int i:mAtom_map.keySet()){
            mNextNearestMap.put(i,getNextNearestNeighbours(i));
        }
    }

    private ArrayList<Integer> getNearestNeighbours(int atom){
        ArrayList<Integer> out = new ArrayList<>();
        for (int i: mAtom_map.keySet()){
            if (atom == i){
                continue;
            }
            if (getDistance(mAtom_map.get(atom),mAtom_map.get(i)) < 1.5){
                out.add(i);
            }
        }
        return out;
    }

    private void updateNearestMap(){
        for(int i:mAtom_map.keySet()){
            mNeighboursMap.put(i,getNearestNeighbours(i));
        }
    }

    private ArrayList<Integer> getNextNearestNeighbours(int atom){
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i: mNeighboursMap.get(atom)){
            temp.addAll(mNeighboursMap.get(i));
        }
        ArrayList<Integer> out = new ArrayList<>();
        for(int i: temp){
            if(i == atom || out.contains(i)){
                continue;
            }
            out.add(i);
        }
        return out;
    }

    public void printMolecule(){
        for(int i : mAtom_map.keySet()){
            mAtom_map.get(i).printAtom();
            System.out.println("\n =============================");
        }
    }

    public void printNeighbours(){
        for(int i:mNeighboursMap.keySet()){
            System.out.println("Atom-> "+i+" Neighbours / ");
            System.out.print("Neighbours -> "+mNeighboursMap.get(i));
            System.out.print("\n-----------------------\n");
        }
    }

    public void printNextNearestNeighbours(){
        for(int i:mNextNearestMap.keySet()){
            System.out.println("Atom-> "+i+" Next Nearest Neighbours / ");
            System.out.print("Next Nearest Neighbours -> "+mNextNearestMap.get(i));
            System.out.print("\n-----------------------\n");
        }
    }

    private int getNextNeighboursCount(int first,int sec){
        int counter = 0;
        ArrayList<Integer> temp = mNeighboursMap.get(first);
        if(temp.contains(sec)){
            return counter;
        }
        for(int i: temp){
            ArrayList<Integer> inter = mNeighboursMap.get(i);
            for(int j: inter){
                if(j == sec){
                    counter++;
                }
            }
        }
        return counter;
    }

    public void updateNextNearestCountMap(){
        int counter;
        for(int i:mAtom_map.keySet()){
            counter = 0;
            for(int j: mAtom_map.keySet()){
                if(i == j){
                    continue;
                }
                counter += getNextNeighboursCount(i,j);
            }
            mNextNearestCountMap.put(i,counter);
        }
    }

    public void printNextNearestCount(){
        for(int i: mNextNearestCountMap.keySet()){
            System.out.println("Atom -> "+i+"  NextNearestCount -> "+mNextNearestCountMap.get(i));
            System.out.println("==========================");
        }
    }
}
