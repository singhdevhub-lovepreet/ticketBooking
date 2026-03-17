package ticket.booking.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class UserBookingService{
    
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a thread-safe ObjectMapper instance
    private ObjectMapper objectMapper = new ObjectMapper();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for userList

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for user
    private List<User> userList;

    private User user;

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more secure way to store the user file path
    private final String USER_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a null check for the user parameter
    public UserBookingService(User user) throws IOException {
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a null check for the user parameter
        this.user = user;
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a try-catch block to handle IOException
        loadUserListFromFile();
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a try-catch block to handle IOException
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a try-catch block to handle IOException
    public UserBookingService() throws IOException {
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a try-catch block to handle IOException
        loadUserListFromFile();
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a try-catch block to handle IOException
    private void loadUserListFromFile() throws IOException {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        userList = objectMapper.readValue(new File(USER_FILE_PATH), new TypeReference<List<User>>() {});
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    public Boolean loginUser(){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        Optional<User> foundUser = userList.stream().filter(user1 -> {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
        }).findFirst();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        return foundUser.isPresent();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    public Boolean signUp(User user1){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        try{
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            userList.add(user1);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            saveUserListToFile();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
            return Boolean.TRUE;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }catch (IOException ex){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return Boolean.FALSE;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
    }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    private void saveUserListToFile() throws IOException {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        File usersFile = new File(USER_FILE_PATH);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        objectMapper.writeValue(usersFile, userList);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    public void fetchBookings(){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        Optional<User> userFetched = userList.stream().filter(user1 -> {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
        }).findFirst();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        if(userFetched.isPresent()){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
            userFetched.get().printTickets();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
        }
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
        // todo: Complete this function
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
         public Boolean cancelBooking(String ticketId){
    
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more secure way to get user input
        Scanner s = new Scanner(System.in);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        System.out.println("Enter the ticket id to cancel");
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        ticketId = s.next();

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        if (ticketId == null || ticketId.isEmpty()) {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            System.out.println("Ticket ID cannot be null or empty.");
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return Boolean.FALSE;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        String finalTicketId1 = ticketId;  //Because strings are immutable
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        String finalTicketId = ticketId;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        user.getTicketsBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        if (removed) {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return Boolean.TRUE;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }else{
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        System.out.println("No ticket found with ID " + ticketId);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return Boolean.FALSE;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
    }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
        
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    public List<Train> getTrains(String source, String destination){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        try{
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            TrainService trainService = new TrainService();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return trainService.searchTrains(source, destination);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }catch(IOException ex){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return new ArrayList<>();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
    }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    public List<List<Integer>> fetchSeats(Train train){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return train.getSeats();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
    }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the method
    public Boolean bookTrainSeat(Train train, int row, int seat) {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        try{
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            TrainService trainService = new TrainService();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            List<List<Integer>> seats = train.getSeats();
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                if (seats.get(row).get(seat) == 0) {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                    seats.get(row).set(seat, 1);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                    train.setSeats(seats);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                    trainService.addTrain(train);
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                    return true; // Booking successful
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                } else {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                    return false; // Seat is already booked
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            } else {
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
                return false; // Invalid row or seat index
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }catch (IOException ex){
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
            return Boolean.FALSE;
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
        }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the variable
    }
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive name for the class
}
