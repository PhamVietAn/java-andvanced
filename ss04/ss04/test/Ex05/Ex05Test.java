package Ex05;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class Ex05Test {
    private Ex05 service = new Ex05();
    private User user;
    @AfterEach
    void cleanup() {
        user = null;
    }
    @Test
    void adminPermissions() {
        user = new User("admin", Role.ADMIN);
        assertAll(
                () -> assertTrue(service.canPerformAction(user, Action.DELETE_USER)),
                () -> assertTrue(service.canPerformAction(user, Action.LOCK_USER)),
                () -> assertTrue(service.canPerformAction(user, Action.VIEW_PROFILE))
        );
    }
    @Test
    void moderatorPermissions() {
        user = new User("mod", Role.MODERATOR);
        assertAll(
                () -> assertFalse(service.canPerformAction(user, Action.DELETE_USER)),
                () -> assertTrue(service.canPerformAction(user, Action.LOCK_USER)),
                () -> assertTrue(service.canPerformAction(user, Action.VIEW_PROFILE))
        );
    }
    @Test
    void userPermissions() {
        user = new User("user", Role.USER);
        assertAll(
                () -> assertFalse(service.canPerformAction(user, Action.DELETE_USER)),
                () -> assertFalse(service.canPerformAction(user, Action.LOCK_USER)),
                () -> assertTrue(service.canPerformAction(user, Action.VIEW_PROFILE))
        );
    }
}