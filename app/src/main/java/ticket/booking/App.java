package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.service.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class App {

    private static boolean isLoggedIn = false;

    public static void main(String[] args) {
        System.out.println("Running Train Booking System");
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        try{
            userBookingService = new UserBookingService();
        }catch(IOException ex){
            System.out.println("There is something wrong");
            return;
        }
        Train trainSelectedForBooking = new Train();
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
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                continue;
            }
            switch (option){
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.nextLine().trim();
                    if (nameToSignUp.isEmpty()) {
                        System.out.println("Username cannot be empty.");
                        break;
                    }
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.nextLine().trim();
                    if (passwordToSignUp.isEmpty()) {
                        System.out.println("Password cannot be empty.");
                        break;
                    }
                    User userToSignup = new User(nameToSignUp, passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp), new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignup);
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = scanner.nextLine().trim();
                    if (nameToLogin.isEmpty()) {
                        System.out.println("Username cannot be empty.");
                        break;
                    }
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = scanner.nextLine().trim();
                    if (passwordToLogin.isEmpty()) {
                        System.out.println("Password cannot be empty.");
                        break;
                    }
                    User userToLogin = new User(nameToLogin, passwordToLogin, UserServiceUtil.hashPassword(passwordToLogin), new ArrayList<>(), UUID.randomUUID().toString());
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                    }catch (IOException ex){
                        System.out.println("Failed to initialize user service.");
                        break;
                    }
                    if (userBookingService.loginUser()) {
                        System.out.println("Login successful!");
                        isLoggedIn = true;
                    } else {
                        System.out.println("Invalid username or password.");
                        isLoggedIn = false;
                    }
                    break;
                case 3:
                    if (!isLoggedIn) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Fetching your bookings");
                    userBookingService.fetchBookings();
                    break;
                case 4:
                    if (!isLoggedIn) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Type your source station");
                    String source = scanner.nextLine().trim();
                    System.out.println("Type your destination station");
                    String dest = scanner.nextLine().trim();
                    List<Train> trains = userBookingService.getTrains(source, dest);
                    if (trains.isEmpty()) {
                        System.out.println("No trains found for this route.");
                        break;
                    }
                    int index = 1;
                    for (Train t: trains){
                        System.out.println(index+" Train id : "+t.getTrainId());
                        for (Map.Entry<String, String> entry: t.getStationTimes().entrySet()){
                            System.out.println("station "+entry.getKey()+" time: "+entry.getValue());
                        }
                        index++;
                    }
                    System.out.println("Select a train by typing 1,2,3...");
                    int trainChoice;
                    try {
                        trainChoice = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }
                    if (trainChoice < 1 || trainChoice > trains.size()) {
                        System.out.println("Invalid train selection.");
                        break;
                    }
                    trainSelectedForBooking = trains.get(trainChoice - 1);
                    break;
                case 5:
                    if (!isLoggedIn) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Select a seat out of these seats");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                    if (seats == null || seats.isEmpty()) {
                        System.out.println("No seat information available for the selected train.");
                        break;
                    }
                    for (List<Integer> row: seats){
                        for (Integer val: row){
                            System.out.print(val+" ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    System.out.println("Enter the row");
                    int row;
                    try {
                        row = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }
                    System.out.println("Enter the column");
                    int col;
                    try {
                        col = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }
                    System.out.println("Booking your seat....");
                    Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row, col);
                    if(booked.equals(Boolean.TRUE)){
                        System.out.println("Booked! Enjoy your journey");
                    }else{
                        System.out.println("Can't book this seat");
                    }
                    break;
                case 6:
                    if (!isLoggedIn) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Enter the ticket id to cancel");
                    String ticketId = scanner.nextLine().trim();
                    if (ticketId.isEmpty()) {
                        System.out.println("Ticket ID cannot be empty.");
                        break;
                    }
                    userBookingService.cancelBooking(ticketId);
                    break;
                case 7:
                    System.out.println("Exiting the app. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose between 1 and 7.");
                    break;
            }
        }
    }
}
