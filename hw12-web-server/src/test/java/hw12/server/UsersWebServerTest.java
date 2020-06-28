package hw12.server;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class UsersWebServerTest {

    @Test
    public void ctrTest(){
        assertDoesNotThrow(()-> new UsersWebServer(8080));
    }

    // @Test
    // public void startTest() {
    //     assertThrows(UnsupportedOperationException.class, () -> new UsersWebServer(8080).start());
    // }
    //
    // @Test
    // public void joinTest() {
    //     assertThrows(UnsupportedOperationException.class, () -> new UsersWebServer(8080).join());
    // }
    //
    // @Test
    // public void stopTest() {
    //     assertThrows(UnsupportedOperationException.class, () -> new UsersWebServer(8080).stop());
    // }
}
