package array.web.handler;

import array.led.PixelUpdater;
import array.led.PixelWriterFactory;
import array.pattern.PatternManager;
import array.web.HtmlFormatter;
import array.web.UrlParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class Handler implements HttpHandler {

    protected static final String REDIRECT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<script type=\"text/javascript\">\n" +
            "setTimeout(\"location.href = '/';\",%d);\n" +
            "</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "%s\n" +
            "</body>\n" +
            "</html>\n";

    protected static final String EMPTY_REDIRECT = String.format(REDIRECT, 0, "");

    protected PixelUpdater pixelUpdater;
    protected PixelWriterFactory writerFactory;
    protected PatternManager patternManager;
    private UrlParser urlParser;
    private HtmlFormatter htmlFormatter;

    protected Handler(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        this.pixelUpdater = pixelUpdater;
        this.writerFactory = writerFactory;
        this.patternManager = patternManager;

        urlParser = new UrlParser();
        htmlFormatter = new HtmlFormatter();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Connection from: " + exchange.getRemoteAddress().getHostString());

        String query = exchange.getRequestURI().getQuery();
        Map<String, String> parameters = query == null ? new HashMap<>() : urlParser.parseURL(query);

        String response = htmlFormatter.formatHtml(createResponse(parameters));

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    protected abstract String createResponse(Map<String, String> parameters);

}
