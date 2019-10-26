package activity;

public class Lab extends Activity {
    private int nbStudentsMax;

    public Lab(int hours, int nbStudentsMax) {
        super(hours);
        this.nbStudentsMax = nbStudentsMax;
    }

    public String getType() {
        return "Lab work";
    }

    public int getCost(int nbStudents) {
        int groupsNumber = (int) Math.ceil(nbStudents / (double) nbStudentsMax);
        return groupsNumber * hours;
    }

}
