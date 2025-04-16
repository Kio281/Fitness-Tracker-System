import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CardioWorkout extends Workout {

    // Constructor
    public CardioWorkout(String workoutType, int duration, String date) {
        super(workoutType, duration, date);
    }

    @Override
    public void displayWorkout() {
        // used 'print formatted' for clear presentations
        System.out.printf("%-10s %-15s %-6s %-6s %-12s %-10s\n",
                "Cardio", workoutType, "-", "-", duration + " mins", date);
    }

    // this loggable interface's method calls on WorkoutManager class to write data
    // to the text file as a string
    @Override
    public void logToFile() {
        WorkoutManager.appendToFile("Cardio," + workoutType + "," + duration + "," + date);
    }

    //this overridden method allow us to sort the workout list according to their date when displaying to the user regardless of when the workout is added
    @Override
    public int compareTo(Workout other) {
        try {
            //this 2 lines of code below take the respective date 'String' from the objects (according to our choosen format) and parse them into Localdate variables
            LocalDate d1 = LocalDate.parse(this.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate d2 = LocalDate.parse(other.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            //so we can use comparison methods like isBefore(),isAfter(),isEqual()...etc
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
