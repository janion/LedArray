package pythonfile;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Handler implements HttpHandler {

    private static final String ERROR = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<script type=\"text/javascript\">\n" +
            "setTimeout(\"location.href = '/';\",%d);\n" +
            "</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "ERROR\n" +
            "</body>\n" +
            "</html>\n";

    private static final String FILE_LOCATION = "/home/pi/table/LedTable";
    private static final String REQUEST_FILE_NAME_FORMAT = "%08d.req";
    private static final String RESPONSE_FILE_NAME_FORMAT = "%08d.res";

    private Process process;
    private int requestCount = 0;

    public Handler() {
        // Start python server
        try {
            startPython();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Started python");
    }



    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println();
        System.out.println("Connection from: " + exchange.getRemoteAddress().getHostString());

        String query = exchange.getRequestURI().getRawSchemeSpecificPart();

        System.out.println("Query: " + query);
        // Get response from python server
        String response = query == null ? ERROR : getResponseFromPython(query);
        System.out.println("Got python response: " + response);

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String getResponseFromPython(String path) {
        String response = ERROR;
        if (path.contains("favicon")) {
            return response;
        }
        try {
            PrintWriter writer = new PrintWriter(FILE_LOCATION + "/" + String.format(REQUEST_FILE_NAME_FORMAT, requestCount), "UTF-8");
            writer.println(path);
            writer.close();

            String responseFileName = FILE_LOCATION + "/" + String.format(RESPONSE_FILE_NAME_FORMAT, requestCount++);
            try {
                String responseInFile = "";
                BufferedReader bufferedReader = new BufferedReader(new FileReader(responseFileName));

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    responseInFile += line;
                    responseInFile += "\n";
                }

                bufferedReader.close();
                response = responseInFile;

                new File(responseFileName).delete();
            } catch (FileNotFoundException ex) {
                // Try harder
            } catch (IOException ex) {
                response = ERROR;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void startPython() throws IOException {
        //Build command
        List<String> commands = new ArrayList<>();
        commands.add("sudo");
        commands.add("python3");
        //Add arguments
        commands.add("LocalFileTableLauncher.py");

        //Run macro on target
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File("/home/pi/table/LedTable/src"));
        pb.redirectErrorStream(true);
        process = pb.start();

        new Thread(() -> {
            while (process.isAlive());
            System.out.println("Python died");
        }).start();
    }

    public void close() {
        if (process != null) {
            process.destroy();
        }
    }

}
