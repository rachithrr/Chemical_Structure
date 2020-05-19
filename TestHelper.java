public class TestHelper {
    public static void main(String v[]){
        System.out.println("Adding atoms");
        Molecule methenol = new Molecule();
        Atom atom = new Atom('H',1.2001,0.0363,0.8431);
        methenol.addAtom(atom);
        atom = new Atom('C',0.7031,0.0083,-0.1305);
        methenol.addAtom(atom);
        atom = new Atom('H',0.9877,0.8943,-0.7114);
        methenol.addAtom(atom);
        atom = new Atom('H',1.0155,-0.8918,-0.6742);
        methenol.addAtom(atom);
        atom = new Atom('O',-0.6582,-0.0067,0.1730);
        methenol.addAtom(atom);
        atom = new Atom('H',-1.1326,-0.0311,-0.6482);
        methenol.addAtom(atom);

        System.out.println("Initilized, The C-O-H angle is: "+(methenol.getCOH_angle() * 180 * 7)/22 + "  degrees");

        methenol.updateNeighbours();
        System.out.println("Neighbours updated");

        methenol.updateNextNearestNeighbours();
        System.out.println("NearestNeighboursUpdated");

        //methenol.printMolecule();
        methenol.printNeighbours();
    }
}
