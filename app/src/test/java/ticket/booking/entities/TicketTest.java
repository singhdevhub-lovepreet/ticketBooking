package ticket.booking.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class TicketTest {

    @Test
    public void testDefaultConstructor() {
        Ticket ticket = new Ticket();
        assertNotNull(ticket);
        assertNull(ticket.getTicketId());
        assertNull(ticket.getUserId());
        assertNull(ticket.getSource());
        assertNull(ticket.getDestination());
        assertNull(ticket.getDateOfTravel());
        assertNull(ticket.getTrain());
    }

    @Test
    public void testParameterizedConstructor() {
        Train train = new Train("T1", "12345", null, null, null);
        Ticket ticket = new Ticket("TK1", "U1", "Bangalore", "Delhi", "2024-01-15", train);

        assertEquals("TK1", ticket.getTicketId());
        assertEquals("U1", ticket.getUserId());
        assertEquals("Bangalore", ticket.getSource());
        assertEquals("Delhi", ticket.getDestination());
        assertEquals("2024-01-15", ticket.getDateOfTravel());
        assertSame(train, ticket.getTrain());
    }

    @Test
    public void testGetTicketInfo() {
        Train train = new Train("T1", "12345", null, null, null);
        Ticket ticket = new Ticket("TK1", "U1", "Bangalore", "Delhi", "2024-01-15", train);

        String info = ticket.getTicketInfo();
        assertEquals("Ticket ID: TK1 belongs to User U1 from Bangalore to Delhi on 2024-01-15", info);
    }

    @Test
    public void testGetTicketInfoWithNullFields() {
        Ticket ticket = new Ticket();
        String info = ticket.getTicketInfo();
        assertEquals("Ticket ID: null belongs to User null from null to null on null", info);
    }

    @Test
    public void testSetTicketId() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("TK99");
        assertEquals("TK99", ticket.getTicketId());
    }

    @Test
    public void testSetUserId() {
        Ticket ticket = new Ticket();
        ticket.setUserId("U99");
        assertEquals("U99", ticket.getUserId());
    }

    @Test
    public void testSetSource() {
        Ticket ticket = new Ticket();
        ticket.setSource("Mumbai");
        assertEquals("Mumbai", ticket.getSource());
    }

    @Test
    public void testSetDestination() {
        Ticket ticket = new Ticket();
        ticket.setDestination("Pune");
        assertEquals("Pune", ticket.getDestination());
    }

    @Test
    public void testSetDateOfTravel() {
        Ticket ticket = new Ticket();
        ticket.setDateOfTravel("2025-06-01");
        assertEquals("2025-06-01", ticket.getDateOfTravel());
    }

    @Test
    public void testSetTrain() {
        Ticket ticket = new Ticket();
        Train train = new Train("T2", "67890", null, null, null);
        ticket.setTrain(train);
        assertSame(train, ticket.getTrain());
    }

    @Test
    public void testSetTrainReplacesExisting() {
        Train train1 = new Train("T1", "11111", null, null, null);
        Train train2 = new Train("T2", "22222", null, null, null);
        Ticket ticket = new Ticket("TK1", "U1", "A", "B", "2024-01-01", train1);

        ticket.setTrain(train2);
        assertSame(train2, ticket.getTrain());
        assertEquals("T2", ticket.getTrain().getTrainId());
    }
}
