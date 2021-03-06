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

import me.sirfaizdat.sfcore.commands.Command;
import me.sirfaizdat.sfcore.commands.CommandArgs;
import org.bukkit.ChatColor;

import java.util.Map;

/**
 * The /sfcore command.
 *
 * @author SirFaizdat
 */
public class SFCoreCommand {

    SFCore core;

    public SFCoreCommand(SFCore core) {
        this.core = core;
    }

    @Command(name = "sfcore.config")
    public void showConfigEntries(CommandArgs args) {
        core.getLang().send(args.getSender(), "config-header");
        for (Map.Entry<String, Object> keys : core.getJsonConfig().getEntries().entrySet()) {
            args.getSender().sendMessage(ChatColor.RED + keys.getKey() + " " + ChatColor.GRAY + " = " + ChatColor.RED + keys.getValue());
        }
    }

}
