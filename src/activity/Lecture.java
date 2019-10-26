package activity;

public class Lecture extends Activity {

    public Lecture(int hours) {
        super(hours);
    }

    public String getType() {
        return "Lecture";
    }

    public int getCost(int nbStudents) {
        return hours;
    }
}
