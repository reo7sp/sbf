package net.sbfmc.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AsyncDownload extends Thread {
	private Download download;

	public AsyncDownload(URL url, String file) {
		this(url, new File(file));
	}

	public AsyncDownload(URL url, File file) {
		download = new Download(url, file);
	}

	public void startDownload() {
		start();
	}

	public void stopDownload() {
		download.stopDownload();
	}

	public void pauseDownload() {
		download.pauseDownload();
	}

	public void resumeDownload() {
		download.resumeDownload();
	}

	public void setBuffer(int number) {
		download.setBuffer(number);
	}

	public int getBuffer() {
		return download.getBuffer();
	}

	public int getDownloaded() {
		return download.getDownloaded();
	}

	public boolean isRunning() {
		return download.isRunning();
	}

	public boolean isPaused() {
		return download.isPaused();
	}

	@Override
	public void run() {
		try {
			download.startDownload();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}
}
