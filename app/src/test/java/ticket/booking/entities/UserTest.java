package ticket.booking.entities;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
        assertNull(user.getName());
        assertNull(user.getPassword());
        assertNull(user.getHashedPassword());
        assertNull(user.getTicketsBooked());
        assertNull(user.getUserId());
    }

    @Test
    public void testParameterizedConstructor() {
        List<Ticket> tickets = new ArrayList<>();
        User user = new User("Alice", "pass123", "hashed_pass", tickets, "U1");

        assertEquals("Alice", user.getName());
        assertEquals("pass123", user.getPassword());
        assertEquals("hashed_pass", user.getHashedPassword());
        assertSame(tickets, user.getTicketsBooked());
        assertEquals("U1", user.getUserId());
    }

    @Test
    public void testSetName() {
        User user = new User();
        user.setName("Bob");
        assertEquals("Bob", user.getName());
    }

    @Test
    public void testSetHashedPassword() {
        User user = new User();
        user.setHashedPassword("new_hash");
        assertEquals("new_hash", user.getHashedPassword());
    }

    @Test
    public void testSetTicketsBooked() {
        User user = new User();
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket("TK1", "U1", "A", "B", "2024-01-01", null));
        user.setTicketsBooked(tickets);
        assertEquals(1, user.getTicketsBooked().size());
        assertEquals("TK1", user.getTicketsBooked().get(0).getTicketId());
    }

    @Test
    public void testSetUserId() {
        User user = new User();
        user.setUserId("U42");
        assertEquals("U42", user.getUserId());
    }

    @Test
    public void testPrintTicketsWithEntries() {
        Train train = new Train("T1", "12345", null, null, null);
        Ticket ticket1 = new Ticket("TK1", "U1", "Bangalore", "Delhi", "2024-01-15", train);
        Ticket ticket2 = new Ticket("TK2", "U1", "Mumbai", "Pune", "2024-02-20", train);
        List<Ticket> tickets = new ArrayList<>(Arrays.asList(ticket1, ticket2));
        User user = new User("Alice", "pass", "hash", tickets, "U1");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        user.printTickets();

        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("TK1"));
        assertTrue(output.contains("TK2"));
        assertTrue(output.contains("Bangalore"));
        assertTrue(output.contains("Delhi"));
        assertTrue(output.contains("Mumbai"));
        assertTrue(output.contains("Pune"));
    }

    @Test
    public void testPrintTicketsEmpty() {
        User user = new User("Alice", "pass", "hash", new ArrayList<>(), "U1");

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        user.printTickets();

        System.setOut(originalOut);

        assertEquals("", outContent.toString());
    }

    @Test
    public void testGettersReturnCorrectValues() {
        List<Ticket> tickets = new ArrayList<>();
        User user = new User("Charlie", "secret", "hashed_secret", tickets, "U99");

        assertEquals("Charlie", user.getName());
        assertEquals("secret", user.getPassword());
        assertEquals("hashed_secret", user.getHashedPassword());
        assertEquals(0, user.getTicketsBooked().size());
        assertEquals("U99", user.getUserId());
    }
}
