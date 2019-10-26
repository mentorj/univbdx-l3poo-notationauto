
package course;

public class Program {
    private String title;
    private int nbStudents;
    private int maxCost;
    private int remainingHours;
    private Course[] courses;
    private int indexCourses;
    static private final int maxSize = 10;

    public Program(String name, int maxCost, int nbStudents) {
        this.title = name;
        this.nbStudents = nbStudents;
        this.maxCost = maxCost;
        this.remainingHours = maxCost;
        this.courses = new Course[maxSize];
        this.indexCourses = 0;
    }

    public boolean addCourse(Course c) throws MaxSizeExceededException {
        if (indexCourses == maxSize)
            throw new MaxSizeExceededException(maxSize);
        int cost = c.getCost(nbStudents);
        if (cost > remainingHours)
            return false;
        remainingHours -= cost;
        courses[indexCourses++] = c;
        return true;
    }

    public int getTotalCost() {
        int cost = 0;
        for (int i = 0; i < indexCourses; i++) {
            cost += courses[i].getCost(nbStudents);
        }
        return cost;
    }

    public String getTitle() {
        return title;
    }

    public int getNbStudents() {
        return nbStudents;
    }

    public int getMaxCost() {
        return maxCost;
    }

}