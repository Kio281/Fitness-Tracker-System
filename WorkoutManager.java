import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutManager {
    // create a list to stor
    private static List<Workout> workoutList = new ArrayList<>();

    public static void addWorkout(Workout workout) {
        workoutList.add(workout);// add to the list
        workout.logToFile();// then also write to the file
    }

    public static void displayWorkouts() {

        Collections.sort(workoutList);

        if (workoutList.isEmpty()) {
            System.out.println("No workouts logged yet.");
            return;
        }
        System.out.println("\n================ Logged Workouts ================\n");
        System.out.printf("%-10s %-15s %-6s %-6s %-12s %-10s\n",
                "Type", "Workout", "Sets", "Reps", "Duration", "Date");// formatted printing for clear presentation
        System.out.println("--------------------------------------------------------------");

        for (Workout w : workoutList) {
            w.displayWorkout();// calls respective display method depending on cardio or strength iteratively
        }
    }

    // write workout records to a text file
    public static void appendToFile(String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("workouts.txt", true))) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    // read workout records from the text file
    public static void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("workouts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");// read each word seperated by a comma as elements and assign them to an array of strings

                if (parts[0].equals("Cardio")) {// determine if the read workout type is cardio
                    workoutList.add(new CardioWorkout(parts[1], Integer.parseInt(parts[2]), parts[3]));
                } else if (parts[0].equals("Strength")) {// determine if the read workout type is strength
                    workoutList.add(new StrengthWorkout(parts[1], Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("No existing file found or file is corrupted.");
        }
    }
}
