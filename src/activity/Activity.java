package activity;

public abstract class Activity {
    protected int hours;

    public Activity(int hours) {
        this.hours = hours;
    }

    public String toString() {
        return getType() + "(" + hours + "h)";
    }

    public abstract String getType();

    public abstract int getCost(int nbStudents);

}