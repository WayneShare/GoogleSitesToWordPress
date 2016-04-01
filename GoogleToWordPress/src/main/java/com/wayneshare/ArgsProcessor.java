package com.wayneshare;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wayneshare.common.CommonConst;
/**
 *  unused class in this project, as we read the input from config file
 * @author xiaowei
 *
 */
public class ArgsProcessor {

	public static final Logger LOG = LoggerFactory
			.getLogger(ArgsProcessor.class);

	// create the Options
	private static Options options = new Options();

	public static CommandLine processArgs(String[] args) {

		CommandLine line = null;

		// create the command line parser
		CommandLineParser parser = new GnuParser();

		options.addOption("h", "help", false, "print help for usage.");
		// options.addOption("l", "list-session", false,
		// "list all designed session.");
		options.addOption(OptionBuilder
				.withLongOpt("create-plan")
				.withArgName("gold.normalized file and all.reference file")
				.hasArg()
				.withDescription(
						"create test plan based on two files: annotated file and reference file.")
				.create("c"));
		// options.addOption("t", "test", false, "test a test session.");
		options.addOption(OptionBuilder.withLongOpt("test")
				// .withArgName("").hasArg()
				.withDescription("make a test by default test case.")
				.create("t"));
		options.addOption(OptionBuilder.withLongOpt("analyze")
				.withDescription("make analysis to test log folder.")
				.withArgName("Optional one logFolder").hasOptionalArg()
				.create("a"));
		try {
			// parse the command line arguments
			line = parser.parse(options, args);

			if (line.hasOption("h")) {
				help();
			} else if (line.hasOption("a")) {
			} else if (line.hasOption("t")) {
			} else if (line.hasOption("c")) {
				String aa = StringUtils.trimToNull(line.getOptionValue("c"));
				LOG.debug("print option with " + aa);
			} else {
				LOG.error("Missing option input args.");
				help();
			}

		} catch (ParseException exp) {
			exp.printStackTrace();
			LOG.error("Failed to parse comand line properties");
			help();
		}
		return line;
	}

	public static void help() {
		System.out.println(CommonConst.S_dashSeperatorBreak);

		String header = "\nSites to WordPress tool.\n";
		String footer = "\n";

		HelpFormatter formater = new HelpFormatter();
		formater.printHelp(120, "SitestoWordPress ", header, options, footer,
				true);
		System.out.println("	");
		System.exit(0);
	}

}
