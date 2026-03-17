package ticket.booking.util;

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a check for BCrypt library presence to prevent NoClassDefFoundError
import org.mindrot.jbcrypt.BCrypt;

// ⭐ [RYFT REVIEW - PRAISE] Class name 'UserServiceUtil' is clear and descriptive, good job!
public class UserServiceUtil
{
// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a parameter validation for plainPassword to prevent NullPointerException
    public static String hashPassword(String plainPassword) {
// 💡 [RYFT REVIEW - SUGGESTION] Using BCrypt.hashpw with gensalt() is a good practice, but consider using a constant for the salt
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

// 💡 [RYFT REVIEW - SUGGESTION] Consider adding a parameter validation for plainPassword and hashedPassword to prevent NullPointerException
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
// 💡 [RYFT REVIEW - SUGGESTION] BCrypt.checkpw is a good choice, but consider logging the result for debugging purposes
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
