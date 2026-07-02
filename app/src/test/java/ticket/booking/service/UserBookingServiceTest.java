package ticket.booking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserBookingServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private File userFile;
    private File userDir;
    private File trainFile;
    private File trainDir;

    private Train createSampleTrain(String id, String no, List<String> stations) {
        List<List<Integer>> seats = Arrays.asList(
                new ArrayList<>(Arrays.asList(0, 0, 0)),
                new ArrayList<>(Arrays.asList(0, 0, 0))
        );
        Map<String, String> stationTimes = new HashMap<>();
        for (String station : stations) {
            stationTimes.put(station, "10:00");
        }
        return new Train(id, no, seats, stationTimes, stations);
    }

    @Before
    public void setUp() throws IOException {
        // UserBookingService reads from "app/src/main/java/ticket/booking/localDb/users.json"
        userDir = new File("app/src/main/java/ticket/booking/localDb");
        userDir.mkdirs();
        userFile = new File(userDir, "users.json");

        // TrainService reads from "../localDB/trains.json"
        trainDir = new File("../localDB");
        trainDir.mkdirs();
        trainFile = new File(trainDir, "trains.json");
    }

    @After
    public void tearDown() {
        if (userFile != null && userFile.exists()) {
            userFile.delete();
        }
        // Clean up created directories
        deleteDirectoryRecursively(new File("app"));
        if (trainFile != null && trainFile.exists()) {
            trainFile.delete();
        }
        if (trainDir != null && trainDir.exists()) {
            trainDir.delete();
        }
    }

    private void deleteDirectoryRecursively(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDirectoryRecursively(child);
                }
            }
        }
        dir.delete();
    }

    private void writeTestUsers(List<User> users) throws IOException {
        objectMapper.writeValue(userFile, users);
    }

    private void writeTestTrains(List<Train> trains) throws IOException {
        objectMapper.writeValue(trainFile, trains);
    }

    private User createTestUser(String name, String password) {
        String hashed = UserServiceUtil.hashPassword(password);
        return new User(name, password, hashed, new ArrayList<>(), "user-" + name);
    }

    @Test
    public void testConstructorNoArg() throws Exception {
        writeTestUsers(new ArrayList<>());
        UserBookingService service = new UserBookingService();
        assertNotNull(service);
    }

    @Test
    public void testConstructorWithUser() throws Exception {
        User user = createTestUser("alice", "pass123");
        writeTestUsers(Arrays.asList(user));
        UserBookingService service = new UserBookingService(user);
        assertNotNull(service);
    }

    @Test
    public void testLoginUserSuccess() throws Exception {
        String password = "pass123";
        String hashed = UserServiceUtil.hashPassword(password);
        User storedUser = new User("alice", password, hashed, new ArrayList<>(), "U1");
        writeTestUsers(Arrays.asList(storedUser));

        User loginUser = new User("alice", password, hashed, new ArrayList<>(), "U1");
        UserBookingService service = new UserBookingService(loginUser);
        assertTrue(service.loginUser());
    }

    @Test
    public void testLoginUserWrongPassword() throws Exception {
        String password = "pass123";
        String hashed = UserServiceUtil.hashPassword(password);
        User storedUser = new User("alice", password, hashed, new ArrayList<>(), "U1");
        writeTestUsers(Arrays.asList(storedUser));

        User loginUser = new User("alice", "wrongpass", UserServiceUtil.hashPassword("wrongpass"), new ArrayList<>(), "U1");
        UserBookingService service = new UserBookingService(loginUser);
        assertFalse(service.loginUser());
    }

    @Test
    public void testLoginUserWrongUsername() throws Exception {
        String password = "pass123";
        String hashed = UserServiceUtil.hashPassword(password);
        User storedUser = new User("alice", password, hashed, new ArrayList<>(), "U1");
        writeTestUsers(Arrays.asList(storedUser));

        User loginUser = new User("bob", password, hashed, new ArrayList<>(), "U2");
        UserBookingService service = new UserBookingService(loginUser);
        assertFalse(service.loginUser());
    }

    @Test
    public void testSignUp() throws Exception {
        writeTestUsers(new ArrayList<>());
        UserBookingService service = new UserBookingService();

        User newUser = createTestUser("charlie", "secret");
        Boolean result = service.signUp(newUser);
        assertTrue(result);
    }

    @Test
    public void testFetchBookingsWithTickets() throws Exception {
        String password = "pass123";
        String hashed = UserServiceUtil.hashPassword(password);
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        Ticket ticket = new Ticket("TK1", "U1", "Bangalore", "Delhi", "2024-01-15", train);
        List<Ticket> tickets = new ArrayList<>(Arrays.asList(ticket));
        User storedUser = new User("alice", password, hashed, tickets, "U1");
        writeTestUsers(Arrays.asList(storedUser));

        User loginUser = new User("alice", password, hashed, new ArrayList<>(), "U1");
        UserBookingService service = new UserBookingService(loginUser);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        service.fetchBookings();

        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("TK1"));
        assertTrue(output.contains("Bangalore"));
        assertTrue(output.contains("Delhi"));
    }

    @Test
    public void testFetchBookingsNoTickets() throws Exception {
        String password = "pass123";
        String hashed = UserServiceUtil.hashPassword(password);
        User storedUser = new User("alice", password, hashed, new ArrayList<>(), "U1");
        writeTestUsers(Arrays.asList(storedUser));

        User loginUser = new User("alice", password, hashed, new ArrayList<>(), "U1");
        UserBookingService service = new UserBookingService(loginUser);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        service.fetchBookings();

        System.setOut(originalOut);

        assertEquals("", outContent.toString());
    }

    @Test
    public void testGetTrainsReturnsResults() throws Exception {
        writeTestUsers(new ArrayList<>());
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        List<Train> results = service.getTrains("bangalore", "delhi");
        assertEquals(1, results.size());
    }

    @Test
    public void testGetTrainsNoMatch() throws Exception {
        writeTestUsers(new ArrayList<>());
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        List<Train> results = service.getTrains("mumbai", "pune");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testFetchSeats() throws Exception {
        writeTestUsers(new ArrayList<>());
        UserBookingService service = new UserBookingService();

        List<List<Integer>> seats = Arrays.asList(
                Arrays.asList(0, 1, 0),
                Arrays.asList(1, 0, 1)
        );
        Train train = new Train("T1", "111", seats, null, null);

        List<List<Integer>> result = service.fetchSeats(train);
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(0), result.get(0).get(0));
        assertEquals(Integer.valueOf(1), result.get(0).get(1));
    }

    @Test
    public void testBookTrainSeatSuccess() throws Exception {
        writeTestUsers(new ArrayList<>());
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        Boolean result = service.bookTrainSeat(train, 0, 0);
        assertTrue(result);
    }

    @Test
    public void testBookTrainSeatAlreadyBooked() throws Exception {
        writeTestUsers(new ArrayList<>());
        List<List<Integer>> seats = Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 0, 0)),
                new ArrayList<>(Arrays.asList(0, 0, 0))
        );
        Map<String, String> stationTimes = new HashMap<>();
        stationTimes.put("bangalore", "10:00");
        Train train = new Train("T1", "111", seats, stationTimes, Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        Boolean result = service.bookTrainSeat(train, 0, 0);
        assertFalse(result);
    }

    @Test
    public void testBookTrainSeatInvalidRow() throws Exception {
        writeTestUsers(new ArrayList<>());
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        Boolean result = service.bookTrainSeat(train, 99, 0);
        assertFalse(result);
    }

    @Test
    public void testBookTrainSeatInvalidColumn() throws Exception {
        writeTestUsers(new ArrayList<>());
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        Boolean result = service.bookTrainSeat(train, 0, 99);
        assertFalse(result);
    }

    @Test
    public void testBookTrainSeatNegativeRow() throws Exception {
        writeTestUsers(new ArrayList<>());
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        Boolean result = service.bookTrainSeat(train, -1, 0);
        assertFalse(result);
    }

    @Test
    public void testBookTrainSeatNegativeColumn() throws Exception {
        writeTestUsers(new ArrayList<>());
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        writeTestTrains(Arrays.asList(train));

        UserBookingService service = new UserBookingService();
        Boolean result = service.bookTrainSeat(train, 0, -1);
        assertFalse(result);
    }
}
