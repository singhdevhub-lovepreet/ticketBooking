package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.JsonFileRepository;
import ticket.booking.util.UserServiceUtil;
import java.io.IOException;
import java.util.*;

public class UserBookingService{
    
    private final JsonFileRepository<User> userRepository;

    private List<User> userList;

    private User user;

    private TrainService trainService;

    private static final String USER_FILE_PATH = "app/src/main/java/ticket/booking/localDb/users.json";

    public UserBookingService(User user) throws IOException {
        this.user = user;
        this.userRepository = new JsonFileRepository<>(USER_FILE_PATH, new TypeReference<List<User>>() {});
        this.trainService = new TrainService();
        loadUserListFromFile();
    }

    public UserBookingService() throws IOException {
        this.userRepository = new JsonFileRepository<>(USER_FILE_PATH, new TypeReference<List<User>>() {});
        this.trainService = new TrainService();
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException {
        userList = userRepository.loadAll();
    }

    private Optional<User> findAuthenticatedUser() {
        return userList.stream().filter(user1 ->
            user1.getName().equals(user.getName()) &&
            UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword())
        ).findFirst();
    }

    public Boolean loginUser(){
        return findAuthenticatedUser().isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        userRepository.saveAll(userList);
    }

    public void fetchBookings(){
        Optional<User> userFetched = findAuthenticatedUser();
        if(userFetched.isPresent()){
            userFetched.get().printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId){
    
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the ticket id to cancel");
        ticketId = s.next();

        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return Boolean.FALSE;
        }

        String finalTicketId = ticketId;
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId));

        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }
        

    public List<Train> getTrains(String source, String destination){
        return trainService.searchTrains(source, destination);
    }

    public List<List<Integer>> fetchSeats(Train train){
            return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat) {
        List<List<Integer>> seats = train.getSeats();
        if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
            if (seats.get(row).get(seat) == 0) {
                seats.get(row).set(seat, 1);
                train.setSeats(seats);
                trainService.addTrain(train);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
