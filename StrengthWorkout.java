import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StrengthWorkout extends Workout {
    // additional attributes for strength workout
    private int sets;
    private int reps;

    // Constructor
    public StrengthWorkout(String workoutType, int sets, int reps, int duration, String date) {
        super(workoutType, duration, date);
        this.sets = sets;
        this.reps = reps;
    }

    @Override
    public void displayWorkout() {
        // used 'print formatted' for clear presentations
        System.out.printf("%-10s %-15s %-6d %-6d %-12s %-10s\n",
                "Strength", workoutType, sets, reps, duration + " mins", date);
    }

    // this loggable interface's method calls on WorkoutManager class to write data
    // to the text file as a string
    @Override
    public void logToFile() {
        WorkoutManager.appendToFile("Strength," + workoutType + "," + sets + "," + reps + "," + duration + "," + date);
    }

    // this overridden method allow us to sort the workout list according to their
    // date when displaying to the user regardless of when the workout is added
    @Override
    public int compareTo(Workout other) {
        try {
            // this 2 lines of code below take the respective date 'String' from the objects
            // (according to our choosen format) and parse them into Localdate variables
            LocalDate d1 = LocalDate.parse(this.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate d2 = LocalDate.parse(other.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            // so we can use comparison methods like isBefore(),isAfter(),isEqual()...etc
            if (d1.isBefore(d2)) {
                return -1;
            } else if (d1.isAfter(d2)) {
                return 1;
            } else {
                return 0;
            }
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format in compareTo: " + e.getMessage());
            return 0;
        }
    }

}
