package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.service.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class App {

    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        try{
            userBookingService = new UserBookingService();
        }catch(IOException ex){
            System.out.println("Failed to initialize the booking system: " + ex.getMessage());
            return;
        }
        Train trainSelectedForBooking = null;
        while(option!=7){
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                scanner.next();
                continue;
            }
            switch (option){
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.next();
                    User userToSignup = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignup);
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = scanner.next();
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = scanner.next();
                    User userToLogin = new User(nameToLogin, passwordToLogin, UserServiceUtil.hashPassword(passwordToLogin), new ArrayList<>(), UUID.randomUUID().toString());
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                    }catch (IOException ex){
                        System.out.println("Failed to login: " + ex.getMessage());
                        break;
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings");
                    userBookingService.fetchBookings();
                    break;
                case 4:
                    System.out.println("Type your source station");
                    String source = scanner.next();
                    System.out.println("Type your destination station");
                    String dest = scanner.next();
                    List<Train> trains = userBookingService.getTrains(source, dest);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found for the given route.");
                        break;
                    }
                    int index = 1;
                    for (Train t: trains){
                        System.out.println(index + ". Train id: " + t.getTrainId());
                        for (Map.Entry<String, String> entry: t.getStationTimes().entrySet()){
                            System.out.println("   station " + entry.getKey() + " time: " + entry.getValue());
                        }
                        index++;
                    }
                    System.out.println("Select a train by typing 1,2,3...");
                    try {
                        int selectedIndex = scanner.nextInt();
                        if (selectedIndex < 1 || selectedIndex > trains.size()) {
                            System.out.println("Invalid selection. Please choose a number between 1 and " + trains.size() + ".");
                            break;
                        }
                        trainSelectedForBooking = trains.get(selectedIndex - 1);
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    }
                    break;
                case 5:
                    if (trainSelectedForBooking == null) {
                        System.out.println("Please search and select a train first (option 4).");
                        break;
                    }
                    System.out.println("Select a seat out of these seats");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                    for (List<Integer> row: seats){
                        for (Integer val: row){
                            System.out.print(val+" ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    try {
                        System.out.println("Enter the row");
                        int row = scanner.nextInt();
                        System.out.println("Enter the column");
                        int col = scanner.nextInt();
                        System.out.println("Booking your seat....");
                        Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row, col);
                        if(booked.equals(Boolean.TRUE)){
                            System.out.println("Booked! Enjoy your journey");
                        }else{
                            System.out.println("Can't book this seat");
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("Invalid input. Please enter valid row and column numbers.");
                        scanner.next();
                    }
                    break;
                case 6:
                    userBookingService.cancelBooking(null);
                    break;
                case 7:
                    System.out.println("Exiting the app. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 7.");
                    break;
            }
        }
    }
}
