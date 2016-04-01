package com.wayneshare.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wayneshare.common.CommonConst;

public class FileFolderUtils {
	public static final Logger LOG = LoggerFactory
			.getLogger(FileFolderUtils.class);

	public static TreeMap<String, String> readFileAsTabSeperatedPairs(
			String referenceFileName) {
		TreeMap<String, String> rt = new TreeMap<String, String>();
		List<String> lineList = readFileAsStringList(referenceFileName);
		for (String line : lineList) {
			String[] pairs = line.split("\\t");
			if (pairs != null && pairs.length == 2) {
				rt.put(pairs[0].trim(), pairs[1].trim());
				// LOG.debug("in reference file found pair: " + pairs[0].trim()
				// + " ==== " + pairs[1].trim());
			}
		}
		return rt;
	}

	public static List<String> readFileAsStringList(String filename) {
		String fileContent = readFileAsString(filename);
		String[] lines = fileContent.split("\\r?\\n");
		return Arrays.asList(lines);
	}

	public static String readFileAsString(String filename) {

		String str = null;
		try {
			str = FileUtils.readFileToString(new File(filename));
			LOG.info("readFileAsString : " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	public static byte[] readFileAsBin(String filename) {
		byte[] binData = null;
		try {
			Path path = Paths.get(filename);
			binData = Files.readAllBytes(path);
			// LOG.debug("readFileAsBin read file : " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return binData;
	}

	/*
	 * for test use only, if user input a wav file name we try to read a file
	 * and get the byte data to send
	 */

	public static byte[] readFileAsBin(String folderName, String fileName) {
		byte[] rt = null;
		if (!StringUtils.isBlank(fileName)) {
			rt = FileFolderUtils.readFileAsBin(folderName + CommonConst.S_Slash
					+ fileName);

		}
		return rt;
	}

	public static boolean writeBinToFile(byte[] binData, String filename) {
		boolean rt = false;
		try {
			Path path = Paths.get(filename);

			Files.write(path, binData); // creates, overwrites
			LOG.info("writeBinToFile saved as file : " + filename);
			path = null;
			rt = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rt;
	}

	public static String writeTextToFile(String data, String fileName) {
		String rt = null;
		try {
			// Path path = Paths.get(filename);
			// Files.write(path, data); // creates, overwrites
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(data);

			// Always close files.
			bufferedWriter.close();
			// Or we could just do this:
			// ex.printStackTrace();

			LOG.info("writeTextToFile saved as file : " + fileName);
			rt = fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rt;
	}

	public static boolean writeLineByLineToFile(List<String> data,
			String fileName) {

		boolean rt = false;
		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			for (String s : data) {
				bufferedWriter.write(s);
				bufferedWriter.newLine();
			}
			// Always close files.
			bufferedWriter.close();
			LOG.info("writeTextToFile saved as file : " + fileName);
			rt = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rt;
	}

	public static String createFolder(String folderName) {
		File file = new File(folderName);
		if (!file.exists()) {
			if (file.mkdir()) {
				LOG.info("Directory is created : " + folderName);
			} else {
				LOG.warn("Failed to create directory : " + folderName);
			}
		}

		return folderName;
	}

	public static String createFolders(String foldersName) {

		File file = new File(foldersName);
		if (!file.exists()) {
			if (file.mkdirs()) {
				// LOG.debug("Directory are created!");
			} else {
				LOG.error("Failed to create directory : " + foldersName);
			}
		}

		return foldersName;
	}

	public static File getFile(String fileFullName) {
		File file = null;
		file = new File(fileFullName);
		if (!file.exists()) {
			LOG.error("File not found: " + fileFullName);
		}
		return file;
	}

	/**
	 * list only sub folder under one folder
	 * 
	 * @param folderName
	 * @return
	 */
	public static List<String> listSubFolders(String folderName) {
		File dir = new File(folderName);
		String[] files = dir.list(DirectoryFileFilter.INSTANCE);
		// for (int i = 0; i < files.length; i++) {
		// LOG.debug(files[i]);
		// }
		List<String> rtList = Arrays.asList(files);
		rtList = removeUnwantedNames(rtList);

		Collections.sort(rtList);
		return rtList;
	}

	public static List<String> removeUnwantedNames(List<String> immutableL) {
		List<String> rtL = new ArrayList<String>();

		for (int i = 0; i < immutableL.size(); i++) {
			if (CommonConst.FOLDER_SVN.equalsIgnoreCase(immutableL.get(i))) {
			} else {
				rtL.add(immutableL.get(i));
			}
		}
		return rtL;
	}

	public static List<File> findAllSubDirs(File dir) {

		List<File> files = (List<File>) FileUtils.listFilesAndDirs(dir,
				new NotFileFilter(TrueFileFilter.INSTANCE),
				DirectoryFileFilter.DIRECTORY);
		// remove unwanted names .svn
		List<File> rtL = new ArrayList<File>();
		for (int i = 0; i < files.size(); i++) {
			if (files.get(i).getAbsolutePath().contains(CommonConst.FOLDER_SVN)) {
			} else {
				rtL.add(files.get(i));
			}
		}

		return rtL;
	}

	public static boolean isFolderExisting(String fullFolderName) {
		return Files.isDirectory(Paths.get(fullFolderName));
	}

	public static boolean isFileExisting(String file) {
		File aa = new File(file);
		return aa.exists();
	}

	public static List<File> findFilefromNameList(List<String> list) {

		List<File> rtList = new ArrayList<File>();
		for (String f : list) {
			// LOG.debug("Found file: " + f.getName());
			rtList.add(new File(f));
		}
		Collections.sort(rtList);
		return rtList;

	}

	public static List<File> findFilesByWildCardInFolder(File dir, String reg) {

		FileFilter fnf = new WildcardFileFilter(reg);
		// FilenameFilter fnf = new FileRegFilter(reg);
		File[] rt = dir.listFiles(fnf);
		if (rt == null)
			return null;
		List<File> rtList = Arrays.asList(rt);
		Collections.sort(rtList);
		return rtList;
	}

	public static List<File> findAllFilesByExtensionUnderFolder(File dir,
			String[] exts, boolean includeSubFolder) {
		List<File> rtList = (List<File>) FileUtils.listFiles(dir, exts,
				includeSubFolder);
		return rtList;
	}

}
