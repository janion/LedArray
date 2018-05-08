package array.led;

public class PixelUpdaterThread extends Thread {
	
	public static final String NAME = "PixelUpdaterThread";
	
	private PixelUpdater updater;
	
	public PixelUpdaterThread(PixelUpdater updater) {
		super(() -> updater.updateLoop(), NAME);
		this.updater = updater;
	}
	
	public void stopUpdater() {
		updater.stop();
	}

}
