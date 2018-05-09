package array.web;

import java.io.IOException;
import java.net.InetSocketAddress;

import array.led.PixelUpdater;
import array.led.PixelWriterFactory;
import array.pattern.PatternManager;
import array.web.handler.*;
import com.sun.net.httpserver.HttpServer;

public class WebServer {

    private HttpServer server;

    public WebServer(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) throws IOException {
        server = HttpServer.create(new InetSocketAddress(81), 0);

        server.createContext("/", new HomePageHandler(pixelUpdater, writerFactory, patternManager));
        server.createContext("/setBrightness", new SetBrightnessHandler(pixelUpdater, writerFactory, patternManager));
        server.createContext("/configure", new ConfigureHandler(pixelUpdater, writerFactory, patternManager));
        server.createContext("/setPattern", new SetPatternHandler(pixelUpdater, writerFactory, patternManager));
        server.createContext("/addPattern", new AddPatternHandler(pixelUpdater, writerFactory, patternManager));
        server.createContext("/removePattern", new RemovePatternHandler(pixelUpdater, writerFactory, patternManager));

        server.setExecutor(null); // creates a default executor
    }

    public void serve() {
        server.start();
    }

}
