/*
 * Copyright 2006-2015 The MZmine 3 Development Team
 * 
 * This file is part of MZmine 2.
 * 
 * MZmine 2 is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * MZmine 2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * MZmine 2; if not, write to the Free Software Foundation, Inc., 51 Franklin St,
 * Fifth Floor, Boston, MA 02110-1301 USA
 */

package io.github.mzmine.main;

import java.io.InputStream;
import java.util.Locale;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import io.github.mzmine.gui.MZmineGUI;
import javafx.application.Application;

/**
 * MZmine main class
 */
public final class MZmineMain {

    private static Logger logger = Logger.getLogger(MZmineMain.class.getName());

    public static void main(String args[]) {

        /*
         * In the beginning, set the default locale to English, to avoid
         * problems with conversion of numbers etc. (e.g. decimal separator may
         * be . or , depending on the locale)
         */
        Locale.setDefault(new Locale("en", "US"));

        /*
         * Configure the logging properties before we start logging
         */
        try {
            ClassLoader cl = MZmineCore.class.getClassLoader();
            InputStream loggingProperties = cl
                    .getResourceAsStream("logging.properties");
            LogManager logMan = LogManager.getLogManager();
            logMan.readConfiguration(loggingProperties);
            loggingProperties.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Cleanup old temporary files on a new thread
         */
        TmpFileCleanup cleanupClass = new TmpFileCleanup();
        Thread cleanupThread = new Thread(cleanupClass);
        cleanupThread.setPriority(Thread.MIN_PRIORITY);
        cleanupThread.start();

        /*
         * Register shutdown hook
         */
        ShutDownHook shutDownHook = new ShutDownHook();
        Thread shutDownThread = new Thread(shutDownHook);
        Runtime.getRuntime().addShutdownHook(shutDownThread);

        /*
         * Load modules on a new thread after the GUI has started
         */
        MZmineStarter moduleStarter = new MZmineStarter();
        Thread moduleStarterThread = new Thread(moduleStarter);
        moduleStarterThread.setPriority(Thread.MIN_PRIORITY);
        moduleStarterThread.start();

        /*
         * Start the JavaFX GUI
         */
        logger.info("Starting MZmine GUI");
        Application.launch(MZmineGUI.class, args);

    }

}
