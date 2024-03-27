import http.MainServerThread;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class MainServerTest {

    @Test
    public void testHandleRequest() throws IOException {

        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                "GET /quotes HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());

        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);

        MainServerThread mainServerThread = new MainServerThread(socket);
        mainServerThread.run();

        verify(socket).getInputStream();
        verify(socket).getOutputStream();
    }

    @Test
    public void testHandleRequestContent() throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                "GET /quotes HTTP/1.1\r\nHost: localhost\r\n\r\n".getBytes());

        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);

        MainServerThread mainServerThread = new MainServerThread(socket);
        mainServerThread.run();

        String response = outputStream.toString();
        assertTrue(response.contains("HTTP/1.1 200 OK"));
        assertTrue(response.contains("Content-Type: text/html"));
    }

}
