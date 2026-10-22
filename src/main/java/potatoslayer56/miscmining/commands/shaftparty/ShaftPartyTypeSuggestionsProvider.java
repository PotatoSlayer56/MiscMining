package potatoslayer56.miscmining.commands.shaftparty;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import potatoslayer56.miscmining.ShaftParty;

import java.util.concurrent.CompletableFuture;

import static potatoslayer56.miscmining.ShaftParty.shaftCategories;
import static potatoslayer56.miscmining.ShaftParty.shaftTypes;

public class ShaftPartyTypeSuggestionsProvider implements SuggestionProvider<FabricClientCommandSource> {
  @Override
  public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> commandContext, SuggestionsBuilder suggestionsBuilder) throws CommandSyntaxException {
    shaftCategories.forEach((type, category) -> {
      suggestionsBuilder.suggest(type);
    });
    return suggestionsBuilder.buildFuture();
  }
}
