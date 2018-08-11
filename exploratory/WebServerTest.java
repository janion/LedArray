import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServerTest {

    private HttpServer server;
    private int count = 0;

    public WebServerTest() throws IOException {
        server = HttpServer.create(new InetSocketAddress(80), 0);

        server.createContext("/", exchange -> String.format("%s - %d", exchange.getRequestURI().getRawSchemeSpecificPart(), count++));

        server.setExecutor(null); // creates a default executor
    }

    public void serve() {
        server.start();
    }

    public static void main(String[] args) throws IOException {
        WebServerTest server = new WebServerTest();
        server.serve();
    }

}
