import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServerPythonTest {

    private HttpServer server;
    private Handler handler;

    public WebServerPythonTest() throws IOException {
        server = HttpServer.create(new InetSocketAddress(80), 0);

        handler = new Handler();
        server.createContext("/", handler);

        server.setExecutor(null); // creates a default executor
    }

    public void serve() {
        server.start();
    }

    public void stop() {
        handler.close();
    }

    public static void main(String[] args) throws IOException {
        WebServerPythonTest server = new WebServerPythonTest();
        server.serve();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

}
