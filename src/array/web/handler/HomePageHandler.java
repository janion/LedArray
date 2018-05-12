package array.web.handler;

import array.led.PixelUpdater;
import array.led.PixelWriterFactory;
import array.pattern.PatternManager;
import array.web.HomePageCreator;
import array.web.HtmlFormatter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

public class HomePageHandler extends Handler {

    private HomePageCreator homePageCreator;

    public HomePageHandler(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        super(pixelUpdater, writerFactory, patternManager);
        homePageCreator = new HomePageCreator(pixelUpdater);
    }

    @Override
    protected String createResponse(Map<String, String> parameters) {
        return homePageCreator.buildHomePage(patternManager);
    }

}
