package ticket.booking.entities;

// 💡 [RYFT REVIEW - SUGGESTION] Consider using Jackson's @JsonProperty annotation for specific field mapping instead of @JsonNaming for the entire class.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a custom Jackson configuration to handle unknown properties instead of ignoring them.
@JsonNaming (PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

// 💡 [RYFT REVIEW - SUGGESTION] Field 'name' should be private and have a getter method.
    private String name;
// ⚠️ [RYFT REVIEW - WARNING] Field 'password' should be private and have a getter method. Consider using a more secure approach for storing passwords.
    private String password;
// 💡 [RYFT REVIEW - SUGGESTION] Field 'hashedPassword' should be private and have a getter method.
    private String hashedPassword;
// 💡 [RYFT REVIEW - SUGGESTION] Field 'ticketsBooked' should be private and have a getter method.
    private List<Ticket> ticketsBooked;
// 💡 [RYFT REVIEW - SUGGESTION] Field 'userId' should be private and have a getter method.
    private String userId;

// 💡 [RYFT REVIEW - SUGGESTION] Consider using a constructor with a single argument (e.g., UserDTO) for easier deserialization.
    public User(String name, String password, String hashedPassword, List<Ticket> ticketsBooked, String userId){
// ⚠️ [RYFT REVIEW - WARNING] Consider using a more secure approach for storing passwords.
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
// ⚠️ [RYFT REVIEW - WARNING] Consider using a more secure approach for storing passwords.
        this.userId = userId;
    }
    public User(){}

// 💡 [RYFT REVIEW - SUGGESTION] Getter method 'getName()' should be private.
    public String getName() {
        return name;
    }

// ⚠️ [RYFT REVIEW - WARNING] Getter method 'getPassword()' should be private. Consider using a more secure approach for storing passwords.
    public String getPassword(){
        return password;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Getter method 'getHashedPassword()' should be private.
    public String getHashedPassword() {
        return hashedPassword;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Getter method 'getTicketsBooked()' should be private.
    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Method 'printTickets()' is not following the single responsibility principle. Consider moving it to a separate class or method.
    public void printTickets(){
        for (int i = 0; i<ticketsBooked.size(); i++){
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }

// 💡 [RYFT REVIEW - SUGGESTION] Getter method 'getUserId()' should be private.
    public String getUserId() {
        return userId;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Setter method 'setName()' should be private.
    public void setName(String name) {
        this.name = name;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Setter method 'setHashedPassword()' should be private.
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Setter method 'setTicketsBooked()' should be private.
    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

// 💡 [RYFT REVIEW - SUGGESTION] Setter method 'setUserId()' should be private.
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
