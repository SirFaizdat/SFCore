/*
 * Copyright (C) 2015 SirFaizdat
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.sirfaizdat.sfcore;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author SirFaizdat
 */
public class SFLogger {

    // == Variables

    private SFPlugin plugin;
    private String logPrefix;
    private boolean debugMode = false;
    private ConsoleCommandSender sender;

    // == Constructor

    public SFLogger(SFPlugin plugin) {
        this.plugin = plugin;
        this.logPrefix = "&8[&3" + plugin.getDescription().getName() + "&8]&r";
        this.sender = plugin.getServer().getConsoleSender();
    }

    // == Methods

    /**
     * Logs a message to the console.
     *
     * @param level   The SFLevel of this message.
     * @param message The message to log to the console. <b>Color may be included.</b>
     * @param t       The exception that was thrown, or null if no exception was thrown.
     */
    public void log(SFLevel level, String message, Throwable t) {
        Validate.notNull(level);
        Validate.notNull(message);

        String fullMessage = logPrefix + level.color + "[" + level.toString() + "] " + message;
        fullMessage = SFCore.color(fullMessage);
        sender.sendMessage(fullMessage);

        // Crash file if it's a SEVERE or CRITICAL error, or if t != null.
        if (level == SFLevel.SEVERE || level == SFLevel.CRITICAL || t != null) {
            String fileName = saveCrashFile(SFCore.color(message), t);
            if (fileName == null) sender.sendMessage(ChatColor.RED + "A crash file could not be saved.");
            else sender.sendMessage(ChatColor.RED + "A crash file was saved to " + fileName);
        }

        // For CRITICAL errors, turn off the plugin to avoid further errors.
        if (level == SFLevel.CRITICAL) {
            sender.sendMessage(ChatColor.RED + "The plugin will now disable to avoid further errors.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    /**
     * Logs a message to the console.
     *
     * @param level   The SFLevel of this message.
     * @param message The message to log to the console. <b>Color may be included.</b>
     */
    public void log(SFLevel level, String message) {
        log(level, message, null);
    }

    // == Private methods

    private String saveCrashFile(String message, Throwable t) {
        // Construct the crash file's name.
        SimpleDateFormat crashDateFormat = new SimpleDateFormat("MM-dd-yyyy_HH.mm.ss");
        String dateString = crashDateFormat.format(Calendar.getInstance().getTime());
        String fileName = "crash_" + dateString + ".txt";

        // Create the crash file
        File crashFile = new File(plugin.getDataFolder(), fileName);
        if (!crashFile.exists()) {
            try {
                crashFile.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }

        // Write to the crash file
        String nl = System.lineSeparator();
        try {
            FileWriter writer = new FileWriter(crashFile);
            writer.append(fileName).append(nl);
            writer.append("Plugin Name: ").append(plugin.getDescription().getFullName()).append(nl);
            writer.append("Plugin Author: ").append(plugin.getDescription().getAuthors().get(0)).append(nl);
            writer.append("Server Version").append(plugin.getServer().getVersion()).append(nl);
            writer.append("Log message: ").append(ChatColor.stripColor(message)).append(nl);
            if (t != null) {
                writer.append("Exception thrown: ").append(t.getClass().getName()).append(nl);
                writer.append("Exception message: ").append(t.getMessage()).append(nl);
                writer.append("Exception cause: ").append(t.getCause() != null ? t.getClass().getName() : "None").append(nl);
                writer.append("Stack trace:").append(nl);
                // Unfortunately, Java needs us to make a PrintWriter for this. Sigh, Java.
                PrintWriter printWriter = new PrintWriter(writer);
                t.printStackTrace(printWriter);
                printWriter.close();
            }
            writer.append("// == End of crash file.").append(nl).append("Please submit this file to the developer ASAP!");
            writer.close();
        } catch (IOException e) {
            return null;
        }

        return fileName;
    }

    // == Getters and setters

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }


    // == Enums

    public enum SFLevel {

        // == Cases

        /**
         * DEBUG log messages only show if the logger has verbose enabled.
         * They are colored yellow.
         */
        DEBUG("&e"),
        /**
         * INFO messages are messages that don't contain any error information.
         * They are colored green.
         */
        INFO("&a"),
        /**
         * WARNING messages are messages that contain error information, but info
         * that doesn't immediately require user action. They are colored gold.
         */
        WARNING("&6"),
        /**
         * SEVERE messages are messages that contain error information and
         * require immediate user reaction. They are colored light red and <b>output crash files.</b>
         */
        SEVERE("&c"),
        /**
         * CRITICAL messsages are messages that contain error information. Once logged,
         * they <b>ouput crash files</b> and then <b>disable the plugin</b>. They are colored dark red.
         */
        CRITICAL("&4");

        // == Variables

        String color;

        // == Constructor

        SFLevel(String color) {
            this.color = color;
        }
    }
}
