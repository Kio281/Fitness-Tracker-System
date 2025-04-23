import java.time.LocalDate;//a class for representing a date without time-of-day and time-zone
import java.time.format.DateTimeFormatter;//to format the date in desired "dd-MM-yyyy" pattern
import java.time.format.DateTimeParseException;//thrown to indicate if there is an error during the parsing of a date string
import java.util.Scanner;
import java.time.temporal.ChronoUnit;//to calculate the difference between two temporal objects
//our program later will have to decide the eligible time window when the user want to log a workout

public class Main {

    // asks for the workout type user wants to log
    // make sure that user enters only characters
    public static String getWorkoutType(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (input.isBlank()) {
                System.out.println("Workout type cannot be empty.");
            } else if (!input.matches("^[a-zA-Z ]+$")) {
                System.out.println("Workout type should only contain letters and spaces.");
            } else {
                return input;
            }
        }
    }
    // checks if the Date String's content is in valid format for LocalDate
    public static boolean isValidDate(String dateStr, String pattern) {
        if (dateStr == null || dateStr.isBlank()) {// date cannot be null or empty
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static int getDuration(Scanner sc) {
        int duration;
        while (true) {
            System.out.print("Enter duration (minutes): ");
            String input = sc.nextLine().trim();
            try {
                duration = Integer.parseInt(input);
                // because humans should not workout more than 5 hours at a time :D
                // lets limit the duration input to maximum 300 mins
                if (duration > 0 && duration <= 300) {
                    return duration;
                } else {
                    System.out.println("Invalid input. Duration must be appropriate number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }

        }
    }

    public static int getSets(Scanner sc) {
        int sets;
        while (true) {
            System.out.print("Enter number of sets: ");
            String input = sc.nextLine().trim();
            try {
                sets = Integer.parseInt(input);
                if (sets > 0 && sets <= 50) {// user is allowed to input only the realstic amount of sets
                    return sets;
                } else {
                    System.out.println("Invalid input. Number of sets must be appropriate number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }

        }
    }

    public static int getReps(Scanner sc) {
        int reps;
        while (true) {
            System.out.print("Enter number of reps per set: ");
            String input = sc.nextLine().trim();
            try {
                reps = Integer.parseInt(input);
                if (reps > 0 && reps <= 100) {// put a limitation on number of reps
                    return reps;
                } else {
                    System.out.println("Invalid input. Number of reps must be appropriate number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }

        }
    }

    public static void printMenu() {// a main menu of choices for our system
        System.out.println("\n--- Fitness Tracker Menu ---");
        System.out.println("1. Add Cardio Workout");
        System.out.println("2. Add Strength Workout");
        System.out.println("3. View All Workouts");
        System.out.println("4. Exit");
    }

    public static String getDate(Scanner sc) {
        while (true) {
            System.out.print("Enter date (DD-MM-YYYY): ");
            String input = sc.nextLine().trim();
            if (isValidDate(input, "dd-MM-yyyy")) {
                // usually LocalDate.parse() method below might throw DateTimeParseException
                // according to the format
                // but our isvalidDate() method made sure that is not the case.
                LocalDate enteredDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalDate today = LocalDate.now();// get current date
                long daysBetween = ChronoUnit.DAYS.between(enteredDate, today);// days between today's date and the date
                                                                               // user entered

                if (enteredDate.isAfter(today)) {// do not allow logging workouts for future
                    System.out.println("You can't log future workouts.");
                } else if (daysBetween > 365) {// do not allow logging workouts that are more than one year old either!
                    System.out.println("That date is too far in the past. Please enter a date within the past year.");
                } else {
                    return input;
                }
            } else {
                System.out.println("Invalid date format. Please use DD-MM-YYYY.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);// initialize Scanner, later will be called by several methods
        WorkoutManager.loadFromFile();// read last saved workouts from the file (if there is any) and add them to the
                                      // workout list

        while (true) {
            printMenu();// display choice menu

            // set up initial conditions for looping
            int choice =-1;
            boolean ValidChoice = false;

            // note that our system only offers 4 choices in menu
            // until the user gets it right, keep asking
            while (!ValidChoice) {
                System.out.print("Choose an option: ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice >= 1 && choice <= 4) {
                        ValidChoice = true;// user entered 1-4, break the while loop and proceed
                    } else {
                        System.out.println("Invalid input. Please choose the option from 1 to 4.");
                    }
                } catch (NumberFormatException e) {// in case user entered anything other than numbers
                    System.out.println("Invalid input. Please enter a number.");
                    continue;

                }
            }

            // so now we have a valid value of choice
            switch (choice) {
                case 1:// user wants to log a new 'CARDIO' workout
                    try {
                        // we have implemented each getBlahBlah methods carefully to not accept wrong
                        // data type or even the correct data in wrong format
                        String type = getWorkoutType(sc,"Enter cardio activity type (e.g., Running): ");// what kind of
                                                                                                         // activity
                        int duration = getDuration(sc);// how long they did the workout for(in minutes)
                        String date = getDate(sc);// on what date they worked out
                        WorkoutManager.addWorkout(new CardioWorkout(type, duration, date));
                        System.out.println("Cardio workout added!");
                    } catch (Exception e) {
                        System.out.println("Invalid input. Workout not added.");
                    }
                    break;

                case 2:// user wants to log a 'STRENGTH' workout
                    try {

                        String workoutType = getWorkoutType(sc,
                                "Enter the type of strength workout (e.g., Back, Biceps, Triceps, Legs): ");
                        int sets = getSets(sc);// number of sets done
                        int reps = getReps(sc);// number of reps per set
                        int duration = getDuration(sc);// duration (in minutes)
                        String date = getDate(sc);
                        WorkoutManager.addWorkout(new StrengthWorkout(workoutType, sets, reps, duration, date));// add
                                                                                                                // workout
                                                                                                                // to
                                                                                                                // list
                                                                                                                // then
                                                                                                                // write
                                                                                                                // to
                                                                                                                // file
                        System.out.println("Strength workout added!");
                    } catch (Exception e) {
                        System.out.println("Invalid input. Workout not added.");
                    }
                    break;

                case 3:
                    WorkoutManager.displayWorkouts();

                    while (true) {
                        System.out.print("Press 'C' to continue or 'E' to exit: ");
                        // whether user enters small or big letter, transform it into captial letter
                        String input = sc.nextLine().trim().toUpperCase();
                        if (input.equals("C")) { 
                            break; // Go back to main menu
                        } else if (input.equals("E")) {
                            System.out.println("Exiting... Stay strong!");
                            return;// end program
                        } else {
                            System.out.println("Invalid option. Please enter 'C' or 'E'.");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Exiting... Stay strong!");
                    return;// end of program
                default:// this default case wont be seen since we handled
                    System.out.println("Unused 'Invalid option'.");
            }
        }
    }
}
