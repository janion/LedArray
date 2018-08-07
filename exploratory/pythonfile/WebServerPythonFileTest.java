package pythonfile;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServerPythonFileTest {

    private HttpServer server;
    private Handler handler;

    public WebServerPythonFileTest() throws IOException {
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
        WebServerPythonFileTest server = new WebServerPythonFileTest();
        server.serve();

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

}
