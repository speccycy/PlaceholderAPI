/*
 The MIT License (MIT)

 Copyright (c) 2017 Wundero

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */
package me.rojo8399.placeholderapi.impl.commands;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import me.rojo8399.placeholderapi.impl.PlaceholderAPIPlugin;
import me.rojo8399.placeholderapi.impl.PlaceholderServiceImpl;
import me.rojo8399.placeholderapi.impl.configs.Messages;

public class RefreshCommand implements CommandExecutor {

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		String placeholderid = args.<String>getOne("id").orElse(null);
		if (placeholderid == null) {
			try {
				PlaceholderAPIPlugin.getInstance().reloadConfig();
				PlaceholderServiceImpl.get().refreshAll();
				src.sendMessage(Messages.get().plugin.reloadSuccess.t());
			} catch (Exception e) {
				throw new CommandException(Messages.get().plugin.reloadFailed.t());
			}
			return CommandResult.success();
		}
		if (!PlaceholderServiceImpl.get().isRegistered(placeholderid)) {
			throw new CommandException(Messages.get().placeholder.invalidPlaceholder.t());
		}
		boolean s = PlaceholderServiceImpl.get().refreshPlaceholder(placeholderid);
		if (!s) {
			src.sendMessage(Messages.get().placeholder.reloadFailed.t());
		} else {
			src.sendMessage(Messages.get().placeholder.reloadSuccess.t());
		}
		return s ? CommandResult.success() : CommandResult.successCount(0);
	}

}
