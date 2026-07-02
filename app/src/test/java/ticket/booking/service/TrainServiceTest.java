package ticket.booking.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TrainServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private File trainFile;
    private File localDBDir;

    private Train createSampleTrain(String id, String no, List<String> stations) {
        List<List<Integer>> seats = Arrays.asList(
                Arrays.asList(0, 0, 0),
                Arrays.asList(0, 0, 0)
        );
        Map<String, String> stationTimes = new HashMap<>();
        for (String station : stations) {
            stationTimes.put(station, "10:00");
        }
        return new Train(id, no, seats, stationTimes, stations);
    }

    @Before
    public void setUp() throws IOException {
        // TrainService reads from "../localDB/trains.json" relative to working dir
        localDBDir = new File("../localDB");
        localDBDir.mkdirs();
        trainFile = new File(localDBDir, "trains.json");
    }

    @After
    public void tearDown() {
        if (trainFile != null && trainFile.exists()) {
            trainFile.delete();
        }
        if (localDBDir != null && localDBDir.exists()) {
            localDBDir.delete();
        }
    }

    private TrainService createServiceWithTrains(List<Train> trains) throws Exception {
        objectMapper.writeValue(trainFile, trains);
        return new TrainService();
    }

    @Test
    public void testSearchTrainsFindsMatchingRoute() throws Exception {
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "jaipur", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train));

        List<Train> results = service.searchTrains("bangalore", "delhi");
        assertEquals(1, results.size());
        assertEquals("T1", results.get(0).getTrainId());
    }

    @Test
    public void testSearchTrainsReturnsEmptyForNoMatch() throws Exception {
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "jaipur", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train));

        List<Train> results = service.searchTrains("mumbai", "pune");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchTrainsRespectsStationOrder() throws Exception {
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "jaipur", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train));

        // Reverse direction should not match
        List<Train> results = service.searchTrains("delhi", "bangalore");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchTrainsMultipleResults() throws Exception {
        Train train1 = createSampleTrain("T1", "111", Arrays.asList("bangalore", "jaipur", "delhi"));
        Train train2 = createSampleTrain("T2", "222", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train1, train2));

        List<Train> results = service.searchTrains("bangalore", "delhi");
        assertEquals(2, results.size());
    }

    @Test
    public void testSearchTrainsWithPartialRoute() throws Exception {
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "jaipur", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train));

        List<Train> results = service.searchTrains("bangalore", "jaipur");
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchTrainsEmptyList() throws Exception {
        TrainService service = createServiceWithTrains(Arrays.asList());

        List<Train> results = service.searchTrains("bangalore", "delhi");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchTrainsSourceNotFound() throws Exception {
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train));

        List<Train> results = service.searchTrains("kolkata", "delhi");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testSearchTrainsDestinationNotFound() throws Exception {
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train));

        List<Train> results = service.searchTrains("bangalore", "kolkata");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testAddTrainNewTrain() throws Exception {
        Train existing = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(existing));

        Train newTrain = createSampleTrain("T2", "222", Arrays.asList("mumbai", "pune"));
        service.addTrain(newTrain);

        List<Train> results = service.searchTrains("mumbai", "pune");
        assertEquals(1, results.size());
        assertEquals("T2", results.get(0).getTrainId());
    }

    @Test
    public void testAddTrainDuplicateIdUpdatesExisting() throws Exception {
        Train existing = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(existing));

        Train updated = createSampleTrain("T1", "999", Arrays.asList("mumbai", "pune"));
        service.addTrain(updated);

        List<Train> results = service.searchTrains("mumbai", "pune");
        assertEquals(1, results.size());
        assertEquals("999", results.get(0).getTrainNo());
    }

    @Test
    public void testUpdateTrainExisting() throws Exception {
        Train existing = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(existing));

        Train updated = createSampleTrain("T1", "999", Arrays.asList("mumbai", "goa"));
        service.updateTrain(updated);

        List<Train> results = service.searchTrains("mumbai", "goa");
        assertEquals(1, results.size());
        assertEquals("999", results.get(0).getTrainNo());
    }

    @Test
    public void testUpdateTrainNonExistingAddsNew() throws Exception {
        Train existing = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(existing));

        Train newTrain = createSampleTrain("T3", "333", Arrays.asList("kolkata", "patna"));
        service.updateTrain(newTrain);

        List<Train> results = service.searchTrains("kolkata", "patna");
        assertEquals(1, results.size());
    }

    @Test
    public void testAddTrainPersistsToFile() throws Exception {
        Train existing = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(existing));

        Train newTrain = createSampleTrain("T2", "222", Arrays.asList("mumbai", "pune"));
        service.addTrain(newTrain);

        // Re-read from file to verify persistence
        TrainService freshService = new TrainService();
        List<Train> results = freshService.searchTrains("mumbai", "pune");
        assertEquals(1, results.size());
    }

    @Test
    public void testSearchTrainsSameSourceAndDestination() throws Exception {
        Train train = createSampleTrain("T1", "111", Arrays.asList("bangalore", "delhi"));
        TrainService service = createServiceWithTrains(Arrays.asList(train));

        List<Train> results = service.searchTrains("bangalore", "bangalore");
        assertTrue(results.isEmpty());
    }
}
