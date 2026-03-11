package ss04;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("case1")
    void sum() {
        Main main = new Main();
        // giá trị mong muốn: a=1, b=2
        Assertions.assertEquals(5, main.sum(1, 2));
    }
}