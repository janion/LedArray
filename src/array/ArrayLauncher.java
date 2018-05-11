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

        MathematicalFunctionPixelWriterFactory writerFactory = new MathematicalFunctionPixelWriterFactory(Constants.LED_COUNT_X, Constants.LED_COUNT_Y);
        //writer = TextPixelWriter(LED_COUNT_X, LED_COUNT_Y, PIXEL_MODE_2D)
        //writer.setTextContent(getIpAddress())
        //strip = NeoPixel(LED_COUNT_X * LED_COUNT_Y, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)
        //strip.begin()

        //updater = PixelUpdater(writer, strip)
        PixelUpdater updater = new MockPixelUpdater();
        //updaterThread = PixelUpdaterThread(updater)

//        PatternManager patterns = new PatternManager(writerFactory);
        PatternManager patterns = new PatternManager(writerFactory);

        WebServer server = new WebServer(updater, writerFactory, patterns);
        Thread serverThread = new Thread(server::serve);

        //updaterThread.start();
        serverThread.start();

        //PhysicalButtons(lambda: updater.setPixelWriter(writer), lambda: stop(updaterThread));

        Runtime.getRuntime().addShutdownHook(new Thread(ArrayLauncher::shutdown));

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
