package potatoslayer56.miscmining.commands.maincommand;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import potatoslayer56.miscmining.MiscMining;

import java.util.concurrent.CompletableFuture;

public class MainCommandSuggestionsProvider implements SuggestionProvider<FabricClientCommandSource> {
  @Override
  public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
    MiscMining.subCommands.forEach((key, value) -> {
      suggestionsBuilder.suggest(key);
    });
    return suggestionsBuilder.buildFuture();
  }
}
