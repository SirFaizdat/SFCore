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

package me.sirfaizdat.sfcore.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Command Framework - Command <br>
 * The command annotation used to designate methods as commands. All methods
 * should have a single CommandArgs argument
 *
 * @author minnymin3
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    /**
     * The name of the command. If it is a sub command then its values would be
     * separated by periods. ie. a command that would be a subcommand of test
     * would be 'test.subcommandname'
     *
     * @return
     */
    String name();

    /**
     * Gets the required permission of the command
     *
     * @return
     */
    String permission() default "";

    /**
     * The message sent to the player when they do not have permission to
     * execute it
     *
     * @return
     */
    String noPerm() default "You do not have permission to perform that action";

    /**
     * A list of alternate names that the command is executed under. See
     * name() for details on how names work
     *
     * @return
     */
    String[] aliases() default {};

    /**
     * The description that will appear in /help of the command
     *
     * @return
     */
    String description() default "";

    /**
     * The usage that will appear in /help (commandname)
     *
     * @return
     */
    String usage() default "";

    /**
     * Whether or not the command is available to players only
     *
     * @return
     */
    boolean inGameOnly() default false;
}
