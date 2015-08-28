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

import me.sirfaizdat.sfcore.commands.CommandFramework;
import me.sirfaizdat.sfcore.json.JsonConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * The main class for any SFCore-utilizing plugin must use this in order
 * to access the API's features.
 *
 * @author SirFaizdat
 */
public abstract class SFPlugin extends JavaPlugin {

    // == Variables

    private CommandFramework commandFramework;
    private JsonConfiguration config;
    private SFLogger logger;
    private Lang lang;

    // == Overriden methods

    // Create the data folder
    @Override
    public void onLoad() {
        if (!getDataFolder().exists()) getDataFolder().mkdir();
    }

    @Override
    public void onEnable() {
        // Init logger and print the enabling message
        logger = new SFLogger(this);
        long startTime = System.currentTimeMillis();
        logger.log(SFLogger.SFLevel.INFO, "&bEnabling " + getDescription().getFullName() + "...");

        // API initialization
        lang = new Lang(getDataFolder());
        commandFramework = new CommandFramework(this);
        config = new JsonConfiguration(new File(getDataFolder(), "config.json"));

        // Plugin enable
        enable();

        // Print enabled message
        long elapsedTime = System.currentTimeMillis() - startTime;
        logger.log(SFLogger.SFLevel.INFO, "&bEnabled in " + elapsedTime + "ms.");
    }

    @Override
    public void onDisable() {
        disable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandFramework.onCommand(sender, command, label, args);
    }

    // == Abstract Methods

    public abstract void enable();

    public abstract void disable();

    // == Methods

    /**
     * Register all annotated commands from the specified object.
     *
     * @param obj The Object to load commands from.
     */
    public void registerCommands(Object obj) {
        commandFramework.registerCommands(obj);
    }

    // == Getters and setters

    public JsonConfiguration getJsonConfig() {
        return config;
    }

    public SFLogger getSFLogger() {
        return logger;
    }

    public Lang getLang() {
        return lang;
    }
}
