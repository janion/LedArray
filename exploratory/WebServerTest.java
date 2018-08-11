import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class WebServerTest {

    private class Handler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("Connection from: " + exchange.getRemoteAddress().getHostString());

            String response = String.format("%s - %d", exchange.getRequestURI().getRawSchemeSpecificPart(), count++);

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private HttpServer server;
    private int count = 0;

    public WebServerTest() throws IOException {
        server = HttpServer.create(new InetSocketAddress(80), 0);

        server.createContext("/", new Handler());

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
