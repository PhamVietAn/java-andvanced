package Ex03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Ex03Test {
    private Ex03 userProcessor;
    @BeforeEach
    void setup() {
        userProcessor = new Ex03();
    }
    @Test
    void shouldReturnSameEmailWhenEmailIsValid() {
        String email = "user@gmail.com";
        String result = userProcessor.processEmail(email);
        assertEquals("user@gmail.com", result);
    }
    @Test
    void shouldThrowExceptionWhenEmailMissingAtSymbol() {
        String email = "usergmail.com";
        assertThrows(IllegalArgumentException.class, () -> {
            userProcessor.processEmail(email);
        });
    }
    @Test
    void shouldThrowExceptionWhenEmailHasNoDomain() {
        String email = "user@";
        assertThrows(IllegalArgumentException.class, () -> {
            userProcessor.processEmail(email);
        });
    }
    @Test
    void shouldConvertEmailToLowercase() {
        String email = "Example@Gmail.com";
        String result = userProcessor.processEmail(email);
        assertEquals("example@gmail.com", result);
    }
}