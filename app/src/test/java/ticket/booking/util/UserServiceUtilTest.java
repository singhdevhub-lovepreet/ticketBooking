package ticket.booking.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceUtilTest {

    @Test
    public void testHashPasswordReturnsNonNull() {
        String hashed = UserServiceUtil.hashPassword("myPassword");
        assertNotNull(hashed);
    }

    @Test
    public void testHashPasswordReturnsDifferentValueThanPlaintext() {
        String plain = "myPassword";
        String hashed = UserServiceUtil.hashPassword(plain);
        assertNotEquals(plain, hashed);
    }

    @Test
    public void testHashPasswordProducesDifferentHashesForSameInput() {
        String plain = "myPassword";
        String hash1 = UserServiceUtil.hashPassword(plain);
        String hash2 = UserServiceUtil.hashPassword(plain);
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void testCheckPasswordWithCorrectPassword() {
        String plain = "correctPassword";
        String hashed = UserServiceUtil.hashPassword(plain);
        assertTrue(UserServiceUtil.checkPassword(plain, hashed));
    }

    @Test
    public void testCheckPasswordWithIncorrectPassword() {
        String hashed = UserServiceUtil.hashPassword("correctPassword");
        assertFalse(UserServiceUtil.checkPassword("wrongPassword", hashed));
    }

    @Test
    public void testCheckPasswordWithEmptyPassword() {
        String hashed = UserServiceUtil.hashPassword("");
        assertTrue(UserServiceUtil.checkPassword("", hashed));
    }

    @Test
    public void testCheckPasswordEmptyAgainstNonEmpty() {
        String hashed = UserServiceUtil.hashPassword("somePassword");
        assertFalse(UserServiceUtil.checkPassword("", hashed));
    }

    @Test
    public void testHashPasswordWithSpecialCharacters() {
        String plain = "p@$$w0rd!#%&*";
        String hashed = UserServiceUtil.hashPassword(plain);
        assertNotNull(hashed);
        assertTrue(UserServiceUtil.checkPassword(plain, hashed));
    }

    @Test
    public void testHashPasswordWithLongInput() {
        String plain = "a".repeat(200);
        String hashed = UserServiceUtil.hashPassword(plain);
        assertNotNull(hashed);
        assertTrue(UserServiceUtil.checkPassword(plain, hashed));
    }

    @Test
    public void testHashPasswordStartsWithBcryptPrefix() {
        String hashed = UserServiceUtil.hashPassword("test");
        assertTrue(hashed.startsWith("$2a$"));
    }
}
