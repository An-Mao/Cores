package dev.anye.mc.cores.am.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.config.MixinConfigs;
import dev.anye.mc.cores.am.listen.ListenArgument;
import dev.anye.mc.cores.am.listen.ListenCore;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.Permissions;

public class CommandList {
	private CommandList() {
	}

	public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
		commandDispatcher.register(Commands.literal(Cores.MOD_ID)
				.then(Commands.literal("mixin")
						.then(Commands.literal("playerLevel")
								.executes(CommandList::mixinPlayerLvl))
				)
				.then(Commands.literal("listen")
						.requires(CommandSourceStack::isPlayer)
						.requires(commandSourceStack -> commandSourceStack.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
						.then(Commands.literal("start").executes(CommandList::startListen))
						.then(Commands.literal("close").executes(CommandList::closeListen))


						.then(Commands.argument("handle", ListenArgument.listen())
								.then(Commands.literal("stat").executes(CommandList::listenStat))
								.then(Commands.literal("activate").executes(CommandList::listenActivate))
								.then(Commands.literal("close").executes(CommandList::listenClose))
						)
				)
		);
	}

	private static int startListen(CommandContext<CommandSourceStack> context) {
		if (ListenCore.server != null) {
			context.getSource().sendFailure(Component.translatable("command.cores.listen.is_run"));
			return 0;
		} else {
			ListenCore.server = ListenCore.createServer(context.getSource().getPlayer());
			ListenCore.initServer();
			ListenCore.startServer();
			context.getSource().sendSuccess(() -> Component.translatable("command.cores.listen.has_run"), false);
			return 1;
		}
	}

	private static int closeListen(CommandContext<CommandSourceStack> context) {
		if (ListenCore.server == null) {
			context.getSource().sendFailure(Component.translatable("command.cores.listen.not_run"));
			return 0;
		} else {
			ListenCore.stopServer();
			ListenCore.server = null;
			context.getSource().sendSuccess(() -> Component.translatable("command.cores.listen.has_close"), false);
			return 1;
		}
	}

	public static int mixinPlayerLvl(CommandContext<CommandSourceStack> context) {
		if (context != null) {
			MixinConfigs.EnableFixLevel = !MixinConfigs.EnableFixLevel;
			context.getSource().sendSuccess(() -> Component.translatable("command.cores.mixin.player_level").append(Component.translatable(MixinConfigs.EnableFixLevel ? "command.cores.mixin.player_level.enable" : "command.cores.mixin.player_level.disable")), false);
			return 1;
		}
		return 0;
	}

	public static int listenStat(CommandContext<CommandSourceStack> context) {
		if (context != null) {
			context.getSource().sendSuccess(() -> {
				Identifier key = ListenArgument.getId(context, "handle");
				return Component.translatable("listen." + key.toLanguageKey()).append(":").append(String.valueOf(ListenCore.isActivity(key)));
			}, false);
			return 1;
		}
		return 0;
	}

	public static int listenActivate(CommandContext<CommandSourceStack> context) {
		if (context != null) {
			context.getSource().sendSuccess(() -> {
				Identifier key = ListenArgument.getId(context, "handle");
				return Component.translatable("listen." + key.toLanguageKey()).append(":").append(String.valueOf(ListenCore.activate(key)));
			}, false);
			return 1;
		}
		return 0;
	}

	public static int listenClose(CommandContext<CommandSourceStack> context) {
		if (context != null) {
			context.getSource().sendSuccess(() -> {
				Identifier key = ListenArgument.getId(context, "handle");
				return Component.translatable("listen." + key.toLanguageKey()).append(":").append(String.valueOf(ListenCore.close(key)));
			}, false);
			return 1;
		}
		return 0;
	}
}
