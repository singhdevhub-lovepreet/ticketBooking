package ticket.booking.entities;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TrainTest {

    @Test
    public void testDefaultConstructor() {
        Train train = new Train();
        assertNotNull(train);
        assertNull(train.getTrainId());
        assertNull(train.getTrainNo());
        assertNull(train.getSeats());
        assertNull(train.getStationTimes());
        assertNull(train.getStations());
    }

    @Test
    public void testParameterizedConstructor() {
        List<List<Integer>> seats = Arrays.asList(
                Arrays.asList(0, 0, 0),
                Arrays.asList(0, 0, 0)
        );
        Map<String, String> stationTimes = new HashMap<>();
        stationTimes.put("bangalore", "10:00");
        stationTimes.put("delhi", "18:00");
        List<String> stations = Arrays.asList("bangalore", "delhi");

        Train train = new Train("T1", "12345", seats, stationTimes, stations);

        assertEquals("T1", train.getTrainId());
        assertEquals("12345", train.getTrainNo());
        assertEquals(seats, train.getSeats());
        assertEquals(stationTimes, train.getStationTimes());
        assertEquals(stations, train.getStations());
    }

    @Test
    public void testSetTrainId() {
        Train train = new Train();
        train.setTrainId("T2");
        assertEquals("T2", train.getTrainId());
    }

    @Test
    public void testSetTrainNo() {
        Train train = new Train();
        train.setTrainNo("54321");
        assertEquals("54321", train.getTrainNo());
    }

    @Test
    public void testSetSeats() {
        Train train = new Train();
        List<List<Integer>> seats = Arrays.asList(
                Arrays.asList(0, 1, 0),
                Arrays.asList(1, 0, 1)
        );
        train.setSeats(seats);
        assertEquals(seats, train.getSeats());
    }

    @Test
    public void testSetStationTimes() {
        Train train = new Train();
        Map<String, String> stationTimes = new HashMap<>();
        stationTimes.put("mumbai", "08:00");
        train.setStationTimes(stationTimes);
        assertEquals(stationTimes, train.getStationTimes());
    }

    @Test
    public void testSetStations() {
        Train train = new Train();
        List<String> stations = Arrays.asList("mumbai", "pune", "goa");
        train.setStations(stations);
        assertEquals(stations, train.getStations());
    }

    @Test
    public void testGetTrainInfo() {
        Train train = new Train("T1", "12345", null, null, null);
        String info = train.getTrainInfo();
        assertEquals("Train ID: T1 Train No: 12345", info);
    }

    @Test
    public void testGetTrainInfoWithNullValues() {
        Train train = new Train();
        String info = train.getTrainInfo();
        assertEquals("Train ID: null Train No: null", info);
    }

    @Test
    public void testSeatsMutability() {
        List<List<Integer>> seats = Arrays.asList(
                Arrays.asList(0, 0, 0),
                Arrays.asList(0, 0, 0)
        );
        Train train = new Train("T1", "12345", seats, null, null);
        List<List<Integer>> retrievedSeats = train.getSeats();
        assertSame(seats, retrievedSeats);
    }
}
