/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

The Kinect Controller makes use of the J4K library and Esper and we have done
nothing to change their source.

By using J4K we are required to site their research article:
A. Barmpoutis. 'Tensor Body: Real-time Reconstruction of the Human Body and Avatar Synthesis from RGB-D',
IEEE Transactions on Cybernetics, Special issue on Computer Vision for RGB-D Sensors: Kinect and Its
Applications, October 2013, Vol. 43(5), Pages: 1347-1356.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.lcsc.hackathon.kinectcontroller;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cli {
	private static final Logger 	_logger     = LoggerFactory.getLogger(Cli.class);
	private 			 String[] 	_args 		= null;
	private 			 Options 	_options 	= new Options();

	public Cli(String[] args) {

		_args = args;

		_options.addOption("h", "help", false, "Show help");
		_options.addOption("f", "filename", true, "Name the configuration file");
		_options.addOption("d", "debug", false, "Display debug skeleton");

	}

	public CommandLine parse() {
		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(_options, _args);

			if (cmd.hasOption("h"))
				help();

			if (cmd.hasOption("f")) {
				_logger.info("Using cli argument -f=" + cmd.getOptionValue("f"));
				// Whatever you want to do with the setting goes here
				System.out.println("Thank you for entering the config name");
			} else {
				_logger.error("Missing configuration filename");
				System.out.println("Warning: Missing configuration file.");
				help();
			}
		} catch (ParseException e) {
			_logger.error("Failed to parse comand line properties", e);
			help();
		}
		
		return cmd;
	}

	private void help() {
		// This prints out some help
		HelpFormatter formater = new HelpFormatter();

		formater.printHelp("Main", _options);
		System.exit(0);
	}
}