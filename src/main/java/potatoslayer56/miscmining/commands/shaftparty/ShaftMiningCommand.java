package potatoslayer56.miscmining.commands.shaftparty;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;

import static potatoslayer56.miscmining.MiscMining.inShaftParty;

public class ShaftMiningCommand {
  public static void register() {
    ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, commandBuildContext) -> {
      commandDispatcher.register(ClientCommands.literal("toggleshaftparty")
        .executes(commandContext -> {
          inShaftParty = true;
          return 0;
      }));
    }));

    ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, commandBuildContext) -> {
      commandDispatcher.register(ClientCommands.literal("registershaftpartymember")
        .then(ClientCommands.argument("ign", StringArgumentType.word())
        .then(ClientCommands.argument("type", StringArgumentType.word())
        .suggests(new ShaftPartyTypeSuggestionsProvider()).executes(commandContext -> {
          return 0;
        }))));
    }));
  }
}
