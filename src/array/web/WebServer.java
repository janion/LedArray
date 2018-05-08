package array.web;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

    public WebServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(81), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Started");
        while(true);
    }

    static class MyHandler implements HttpHandler {
        private static int i = 0;
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println(i++ + " : " + t.getRequestURI());
            String response = "This is the response";
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
