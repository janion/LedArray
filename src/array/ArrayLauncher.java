package array;

import array.led.MockPixelUpdater;
import array.led.PixelUpdater;
import array.led.writer.MathematicalFunctionPixelWriterFactory;
import array.pattern.PatternManager;
import array.web.WebServer;
import fn.operator.singlearg.Constant;

public class ArrayLauncher {

//    public static void main(String[] args) throws Exception {
//        new WebServer();
//    }


    public static void main(String[] args) throws Exception {
        //os.chdir(os.path.dirname(__file__))

        //WifiConnectionSetup().connect()

        System.out.println("Creating writer factory");
        MathematicalFunctionPixelWriterFactory writerFactory = new MathematicalFunctionPixelWriterFactory(Constants.LED_COUNT_X, Constants.LED_COUNT_Y);
        //writer = TextPixelWriter(LED_COUNT_X, LED_COUNT_Y, PIXEL_MODE_2D)
        //writer.setTextContent(getIpAddress())
        //strip = NeoPixel(LED_COUNT_X * LED_COUNT_Y, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)
        //strip.begin()

        System.out.println("Creating pixel updater");
        //updater = PixelUpdater(writer, strip)
        PixelUpdater updater = new MockPixelUpdater();
        //updaterThread = PixelUpdaterThread(updater)

        System.out.println("Creating pattern manager");
//        PatternManager patterns = new PatternManager(writerFactory);
        PatternManager patterns = new PatternManager(writerFactory);

        System.out.println("Creating web server");
        WebServer server = new WebServer(updater, writerFactory, patterns);
        System.out.println("Creating web server thread");
        Thread serverThread = new Thread(server::serve);

        System.out.println("Starting web server thread");
        //updaterThread.start();
        serverThread.start();

        //PhysicalButtons(lambda: updater.setPixelWriter(writer), lambda: stop(updaterThread));

        System.out.println("Creating shutdown hook");
        Runtime.getRuntime().addShutdownHook(new Thread(ArrayLauncher::shutdown));


        System.out.println("Looping");
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException exptn) {
                // Do nothing
            }
        }
    }

    private static void shutdown() {
        System.out.println("Stopping");
        //updaterThread.stop();
        //updaterThread.join();
        //server.stop();
        //serverThread.stop()
        //serverThread.join()
    }

}
