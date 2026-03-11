package BTTH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BTTHTest {
    private BTTH userService;
    @BeforeEach
    void setUp() {
        userService = new BTTH();
    }
    // Test Case 1
    @Test
    void shouldAddUserSuccessfully() {
        User user = new User(1, "huy", "huy@gmail.com");
        userService.addUser(user);
        assertEquals(1, userService.getUsers().size());
    }
    // Test Case 2
    @Test
    void shouldThrowExceptionWhenUsernameIsNull() {
        User user = new User(1, null, "test@gmail.com");
        assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(user);
        });
    }

    // Test Case 3
    @Test
    void shouldReturnNullWhenUserNotFound() {
        User user = new User(1, "huy", "huy@gmail.com");
        userService.addUser(user);
        User result = userService.findUserById(2);
        assertNull(result);
    }
    // Test Case 4
    @Test
    void shouldReturnTrueWhenEmailIsValid() {
        String email = "test@gmail.com";
        boolean result = userService.isValidEmail(email);
        assertTrue(result);
    }
}