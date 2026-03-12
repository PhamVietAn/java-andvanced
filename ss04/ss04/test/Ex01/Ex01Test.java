package Ex01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Ex01Test {
    @Test
    void TC01_validUsername() {
        String username = "user123";
        boolean result = Ex01.isValidUsername(username);
        assertTrue(result);
    }
    @Test
    void TC02_usernameTooShort() {
        String username = "abc";
        boolean result = Ex01.isValidUsername(username);
        assertFalse(result);
    }
    @Test
    void TC03_usernameContainsSpace() {
        String username = "user name";
        boolean result = Ex01.isValidUsername(username);
        assertFalse(result);
    }
}