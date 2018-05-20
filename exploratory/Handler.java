import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Handler implements HttpHandler {

    protected static final String ERROR = "<!DOCTYPE html>\n" +
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

    private Process process;

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
        try {
            Socket socket = new Socket("localhost", 83);
            System.out.println(socket.isConnected());
            PrintWriter bufferedWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write(path + "\n");
            bufferedWriter.flush();
            System.out.println("Wrote");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // TODO: Read until "</html>"? or format response html
            response = bufferedReader.readLine();
            System.out.println("Read");

            bufferedWriter.close();
            bufferedReader.close();

            socket.close();
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
//        commands.add("LocalHostSocketTest.py");
        commands.add("LocalTableLauncher.py");

        //Run macro on target
        ProcessBuilder pb = new ProcessBuilder(commands);
//        pb.directory(new File("/home/pi/table/LedArray/builds"));
        pb.directory(new File("/home/pi/table/LedTable/src"));
        pb.redirectErrorStream(true);
        process = pb.start();
    }

    public void close() {
        if (process != null) {
            process.destroy();
        }
    }

}
