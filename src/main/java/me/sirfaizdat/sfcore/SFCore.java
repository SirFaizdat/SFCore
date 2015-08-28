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

import org.bukkit.ChatColor;

import java.io.IOException;

/**
 * Main class for the SFCore plugin. Initializes some parts of the API.
 *
 * @author SirFaizdat
 */
public class SFCore extends SFPlugin {

    // == Variables
    private static SFCore instance;

    // == Overriden methods

    /**
     * Translates the &-prefixed color codes to color codes
     * MC can understand.
     *
     * @param txt The uncolored text to be colored.
     */
    public static String color(String txt) {
        return ChatColor.translateAlternateColorCodes('&', txt);
    }

    public static SFCore get() {
        return instance;
    }

    @Override
    public void enable() {
        instance = this;
        loadLang();
        loadConfig();
        registerCommands(new SFCoreCommand(this));
        getLogger().info("Enabled.");
    }

    // == Private methods

    @Override
    public void disable() {

    }

    private void loadConfig() {
        getJsonConfig().addDefault("version", "1.0-SNAPSHOT");
        getJsonConfig().addDefault("foo", "bar");
        getJsonConfig().addDefault("plane", "fun");
        try {
            getJsonConfig().load();
        } catch (IOException e) {
            // TODO Colored logger message
            e.printStackTrace();
        }
    }

    // == Static methods

    private void loadLang() {
        getLang().addDefault("config-header", "&bConfiguration Variables&7 -");
        getLang().load();
    }

}