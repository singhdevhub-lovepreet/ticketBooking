package ticket.booking.entities;

// 💡 [RYFT REVIEW - SUGGESTION] Consider using import statements at the top of the file, not in the middle
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

// 💡 [RYFT REVIEW - SUGGESTION] This annotation is unnecessary if you're using Lombok for getters and setters
@JsonIgnoreProperties(ignoreUnknown = true)
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more specific naming strategy, such as UpperCamelCase
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Ticket{

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive field name, such as 'ticketNumber'
    private String ticketId;

    private String userId;

    private String source;

    private String destination;

    private String dateOfTravel;

// 🚨 [RYFT REVIEW - CRITICAL] This field should be annotated with @JsonIgnore to prevent infinite recursion
    private Train train;

    public Ticket(){}
// 💡 [RYFT REVIEW - SUGGESTION] Consider using a constructor with a single argument (e.g., builder) for better encapsulation

    public Ticket(String ticketId, String userId, String source, String destination, String dateOfTravel, Train train){
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'getTicketSummary'
    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, destination, dateOfTravel);
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'getTicketNumber'
    public String getTicketId(){
        return ticketId;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'setTicketNumber'
    public void setTicketId(String ticketId){
        this.ticketId = ticketId;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'getSourceStation'
    public String getSource(){
        return source;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'setSourceStation'
    public void setSource(String source){
        this.source = source;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'getDestinationStation'
    public String getUserId(){
        return userId;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'setDestinationStation'
    public void setUserId(String userId){
        this.userId = userId;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'getTravelDate'
    public String getDestination(){
        return destination;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'setTravelDate'
    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDateOfTravel(){
        return dateOfTravel;
    }

    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'getTrainInformation'
    public Train getTrain(){
        return train;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a more descriptive method name, such as 'setTrainInformation'
    public void setTrain(Train train){
        this.train = train;
    }

}