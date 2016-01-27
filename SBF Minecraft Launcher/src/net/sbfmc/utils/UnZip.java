package net.sbfmc.utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnZip {
	public static void start(String szZipFilePath, String szExtractPath) {
		System.out.println("UnZip started!");
		int i;

		File f = new File(szZipFilePath);
		if (!f.exists()) {
			System.out.println("Not found: " + szZipFilePath);
		}

		if (f.isDirectory()) {
			System.out.println("Not file: " + szZipFilePath);
		}

		File f1 = new File(szExtractPath);
		if (!f1.exists()) {
			System.out.println("Not found: " + szExtractPath);
		}

		if (!f1.isDirectory()) {
			System.out.println("Not directory: " + szExtractPath);
		}

		ZipFile zf;
		Vector<ZipEntry> zipEntries = new Vector<ZipEntry>();

		try {
			zf = new ZipFile(szZipFilePath);
			Enumeration<?> en = zf.entries();

			while (en.hasMoreElements()) {
				zipEntries.addElement((ZipEntry) en.nextElement());
			}

			for (i = 0; i < zipEntries.size(); i++) {
				ZipEntry ze = zipEntries.elementAt(i);

				extractFromZip(szExtractPath, ze.getName(), zf, ze);
			}

			zf.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		System.out.println("Done!");
	}

	// ============================================
	// extractFromZip
	// ============================================
	private static void extractFromZip(String szExtractPath, String szName, ZipFile zf, ZipEntry ze) {
		if (ze.isDirectory()) {
			return;
		}

		String szDstName = slash2sep(szName);

		String szEntryDir;

		if (szDstName.lastIndexOf(File.separator) != -1) {
			szEntryDir = szDstName.substring(0,
					szDstName.lastIndexOf(File.separator));
		} else {
			szEntryDir = "";
		}

		System.out.print(szExtractPath + File.separator + szDstName);
		long nSize = ze.getSize() / 1024;
		long nCompressedSize = ze.getCompressedSize() / 1024;

		System.out.println(" " + nSize + " KB (" + nCompressedSize + " KB)");

		try {
			File newDir = new File(szExtractPath + File.separator + szEntryDir);

			newDir.mkdirs();

			FileOutputStream fos = new FileOutputStream(szExtractPath
					+ File.separator + szDstName);

			InputStream is = zf.getInputStream(ze);
			byte[] buf = new byte[1024];

			int nLength;

			while (true) {
				try {
					nLength = is.read(buf);
				} catch (EOFException ex) {
					break;
				}

				if (nLength < 0) {
					break;
				}
				fos.write(buf, 0, nLength);
			}

			is.close();
			fos.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	// ============================================
	// slash2sep
	// ============================================
	private static String slash2sep(String src) {
		int i;
		char[] chDst = new char[src.length()];
		String dst;

		for (i = 0; i < src.length(); i++) {
			if (src.charAt(i) == '/') {
				chDst[i] = File.separatorChar;
			} else {
				chDst[i] = src.charAt(i);
			}
		}
		dst = new String(chDst);
		return dst;
	}
}