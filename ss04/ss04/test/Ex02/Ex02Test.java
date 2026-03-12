package Ex02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Ex02Test {
    Ex02 userService = new Ex02();
    @Test
    void TC01_age18_validRegistration() {
        int age = 18;
        boolean result = userService.checkRegistrationAge(age);
        assertEquals(true, result);
    }

    @Test
    void TC02_age17_invalidRegistration() {
        int age = 17;
        boolean result = userService.checkRegistrationAge(age);
        assertEquals(false, result);
    }

    @Test
    void TC03_negativeAge_shouldThrowException() {
        int age = -1;
        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkRegistrationAge(age);
        });
    }
}