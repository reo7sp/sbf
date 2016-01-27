package net.sbfmc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Download {
	private int buffer = 1024 * 1024; // 1 MB
	private boolean running;
	private boolean paused;
	private File file;
	private URL url;

	public Download(URL url, String file) {
		this(url, new File(file));
	}

	public Download(URL url, File file) {
		this.url = url;
		this.file = file;
	}

	public void startDownload() throws IOException {
		if (url == null) {
			throw new NullPointerException("URL can't be null!");
		}
		running = true;

		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		} catch (Exception err) {
			err.printStackTrace();
		}

		try {
			BufferedInputStream in = new BufferedInputStream(url.openStream());
			BufferedOutputStream bout = new BufferedOutputStream(
					new FileOutputStream(file), buffer);
			byte[] data = new byte[buffer];
			int x = 0;
			while (running && (x = in.read(data, 0, buffer)) >= 0) {
				while (paused) {
				}
				bout.write(data, 0, x);
			}
			bout.close();
			in.close();
		} catch (IOException err) {
			throw err;
		} finally {
			running = false;
		}
	}

	public void stopDownload() {
		running = false;
		if (file != null && file.exists()) {
			file.delete();
		}
	}

	public void pauseDownload() {
		paused = true;
	}

	public void resumeDownload() {
		paused = false;
	}

	public void setBuffer(int number) {
		buffer = number;
	}

	public int getBuffer() {
		return buffer;
	}

	public int getDownloaded() {
		if (file != null && file.exists()) {
			return (int) (file.length() / 1024 / 1024);
		} else {
			return 0;
		}
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isPaused() {
		return paused;
	}

	public File getFile() {
		return file;
	}

	public URL getUrl() {
		return url;
	}
}
