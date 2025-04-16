
public abstract class Workout implements Loggable, Comparable<Workout> {
    protected String date;
    protected int duration; // in minutes
    protected String workoutType;

    public Workout(String workoutType, int duration, String date) {
        this.workoutType = workoutType;
        this.duration = duration;
        this.date = date;

    }

    public abstract void displayWorkout();

    public String getWorkoutType() {
        return workoutType;
    }

    public int getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

}
