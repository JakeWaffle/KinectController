/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

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

package com.wafflesoft.kinectcontroller;

import com.wafflesoft.kinectcontroller.config.generated.ControllerFSMFactory;
import com.wafflesoft.kinectcontroller.config.generated.ParseException;
import com.wafflesoft.kinectcontroller.controller.ControllerStateMachine;
import com.wafflesoft.kinectcontroller.kinect.KinectUserTracker;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;


public class Main {
    private static final Logger _logger      = LoggerFactory.getLogger(Main.class);

    private CommandLine             _arguments;
    private ControllerStateMachine  _csm;
    private JFrame                  _frame;
    private boolean                 _done;
	private JTextArea				_eventWindow;
    
    public static void main(String[] args) {
        Main main = new Main(args);
        main.run();
    }
    
    public Main(String[] args) {
        _arguments  = new Cli(args).parse();
        _csm        = parseConfig(_arguments.getOptionValue("f"));
    }

    private ControllerStateMachine parseConfig(String configFilename) {
        ControllerStateMachine csm = null;
        try {
            FileInputStream in = new FileInputStream(configFilename);
            ControllerFSMFactory csf = new ControllerFSMFactory(in);
            csm = csf.create();
        } catch (FileNotFoundException e) {
            _logger.error("Config file not found: "+configFilename, e);
        } catch (ParseException e) {
            _logger.error("Problem parsing config file: "+configFilename, e);
        }

        return csm;
    }
    
    public void run() {
        KinectUserTracker kinectUserTracker = new KinectUserTracker(_csm);

        _done   = false;
        _frame  = new JFrame("Kinect Controller");

        // register to key events
        _frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent arg) {
            }

            public void keyReleased(KeyEvent arg) {
            }

            public void keyPressed(KeyEvent arg) {
                if (arg.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    _done = true;
                }
            }
        });

        // register to closing event
        _frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                _done = true;
            }
        });

        kinectUserTracker.kinectWindow.setSize(800, 600);
		
        _frame.add("Center", kinectUserTracker.kinectWindow);
		
		_eventWindow = new JTextArea();
		_eventWindow.setVisible(!_arguments.hasOption('d'));
		_frame.add(new JScrollPane(_eventWindow));
		
        _frame.setSize(kinectUserTracker.kinectWindow.getWidth(), kinectUserTracker.kinectWindow.getHeight());
        _frame.setVisible(true);

        while (!_done) {
            _frame.repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}