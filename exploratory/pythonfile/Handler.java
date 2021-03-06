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
            "setTimeout(\"location.href = '/home';\",5000);\n" +
            "</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "ERROR\n" +
            "</body>\n" +
            "</html>\n";

    private static final String FILE_LOCATION = "/home/pi/table/LedTable";
    private static final String REQUEST_FILE_NAME_FORMAT = "%08d.req";
    private static final String RESPONSE_FILE_NAME_FORMAT = "%08d.res";

    private static final long TIMEOUT = 5000;

    private Process process;
    private int requestCount = 0;

    public Handler() {
        // Start python server
//        try {
//            startPython();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("Python assumed started");
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
        //System.out.println("Got python response: " + response);

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public String getResponseFromPython(String path) {
        String response = ERROR;
//        if (path.contains("favicon")) {
//            return response;
//        }
        try {
            String requestFileName = String.format(REQUEST_FILE_NAME_FORMAT, requestCount);
            PrintWriter writer = new PrintWriter(FILE_LOCATION + "/" + requestFileName, "UTF-8");
            writer.println(path);
            writer.close();
            System.out.println("JAVA - Request written " + requestFileName);

            long start = System.currentTimeMillis();

            String responseFileName = FILE_LOCATION + "/" + String.format(RESPONSE_FILE_NAME_FORMAT, requestCount++);
            while (true) {
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
                    System.out.println("JAVA - Response file read and deleted: " + responseFileName);
                    break;
                } catch (FileNotFoundException ex) {
                    // Try harder
                    if (System.currentTimeMillis() - start > TIMEOUT) {
                        System.err.println("JAVA - Timeout " + ex);
                        response = ERROR;
                        break;
                    }
                } catch (IOException ex) {
                    System.err.println("JAVA - " + ex);
                    response = ERROR;
                }
            }
        } catch(IOException e) {
            System.err.println("JAVA - " + e);
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

//        new Thread(() -> {
//            BufferedWriter bw;
//            try {
//                bw = new BufferedWriter(new FileWriter(new File("/home/pi/table/PythonOutput.txt")));
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//            while (true) {
//                BufferedReader br = null;
//                try {
//                    br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                    String line = br.readLine();
//                    bw.write(line);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    break;
//                } finally {
//                    try {
//                        if (br != null) {
//                            br.close();
//                        }
//                        bw.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

    public void close() {
        if (process != null) {
            process.destroy();
        }
    }

}
