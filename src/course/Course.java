package course;

import activity.Activity;

public class Course {
    private String title;
    private Activity[] Activities;

    public String getTitle() {
        return title;
    }

    public Course(String title, Activity a1, Activity a2) {
        this.title = title;
        this.Activities = new Activity[2];
        Activities[0] = a1;
        Activities[1] = a2;
    }

    public Course(String title, Activity a) {
        this.title = title;
        this.Activities = new Activity[1];
        Activities[0] = a;
    }

    // added for testing Grade
    public Course(String title,int a,int b){

    }

    public String toString() {
        String msg = title + " : ";
        for (Activity a : Activities) {
            msg += a + " ";
        }
        return msg;
    }

    public int getCost(int nbStudents) {
        int cost = 0;
        for (Activity a : Activities) {
            cost += a.getCost(nbStudents);
        }
        return cost;
    }

}