package dev.anye.mc.cores.am.listen;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.arguments.IdentifierArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

public class ListenArgument extends IdentifierArgument {

	public static ListenArgument listen() {
		return new ListenArgument();
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		ListenRegister.LISTEN_REGISTER.getRegistry().asHolderIdMap().forEach(listenHolder -> {
			Identifier k = listenHolder.getKey().identifier();
			builder.suggest(k.toString(), Component.translatable("listen." + k.toLanguageKey()));
		});
		return builder.buildFuture();
	}
}
