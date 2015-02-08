//http://www.thinkplexx.com/blog/simple-apache-commons-cli-example-java-command-line-arguments-parsing
package com.lcsc.hackathon;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import edu.ufl.digitalworlds.j4k.Skeleton;

public class Cli {
	private static final Logger log = Logger.getLogger(Cli.class.getName());
	private String[] args = null;
	private Options options = new Options();

	public Cli(String[] args) {

		this.args = args;

		options.addOption("h", "help", false, "show help.");
		options.addOption("f", "filename", true, "Name the configuration file");
		options.addOption("d", "debug", false, "Display debug skeleton");

	}

	public CommandLine parse() {
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);

			if (cmd.hasOption("h"))
				help();

			if (cmd.hasOption("f")) {
				log.log(Level.INFO, "Using cli argument -f=" + cmd.getOptionValue("f"));
				// Whatever you want to do with the setting goes here
				System.out.println("Thank you for entering the config name");
			} else {
				log.log(Level.SEVERE, "Missing configuration filename");
				System.out.println("Warning: Missing configuration file.");
				help();
			}
		} catch (ParseException e) {
			log.log(Level.SEVERE, "Failed to parse comand line properties", e);
			help();
		}
		
		return cmd;
	}

	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("Main", options);
		System.exit(0);
	}
}