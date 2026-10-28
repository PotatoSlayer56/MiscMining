package potatoslayer56.miscmining.commands.shaftparty;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import potatoslayer56.miscmining.ShaftParty;

import java.util.HashMap;
import java.util.List;

import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;

public class ShaftMiningCommand {
  public static void register() {
    ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, commandBuildContext) -> {
      commandDispatcher.register(ClientCommands.literal("toggleshaftparty")
        .executes(commandContext -> {
          ShaftParty.inShaftParty = !ShaftParty.inShaftParty;
          if (!ShaftParty.inShaftParty) {
              ShaftParty.shaftPartyMembers = new HashMap<>();
              ShaftParty.waitingForJoin = List.of();
              clientPrefixedMessage("§aDisbanded shaft party");
          }
          else {
            clientPrefixedMessage("§aCreated shaft party");
          }
          return 0;
      }));
    }));

    ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, commandBuildContext) -> {
      commandDispatcher.register(ClientCommands.literal("registershaftpartymember")
        .then(ClientCommands.argument("ign", StringArgumentType.word())
        .then(ClientCommands.argument("type", StringArgumentType.word())
        .suggests(new ShaftPartyTypeSuggestionsProvider())
        .executes(commandContext -> {
          String ign =  StringArgumentType.getString(commandContext, "ign");
          String type =  StringArgumentType.getString(commandContext, "type");
          ShaftParty.shaftPartyMembers.put(ign, type);
          clientPrefixedMessage("§aAdded §6%s §ato the shaft party, they are set to mine §6%s".formatted(ign, type));
          return 0;
        }))));
    }));

    ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, buildContext) -> {
      commandDispatcher.register(ClientCommands.literal("removeshaftpartymember")
        .then(ClientCommands.argument("ign", StringArgumentType.word())
        .suggests(new ShaftPartyMemberSuggestionProvider())
        .executes(commandContext -> {
          String ign = StringArgumentType.getString(commandContext, "ign");
          ShaftParty.shaftPartyMembers.remove(ign);
          clientPrefixedMessage("§aRemoved §6%s §afrom the shaft party".formatted(ign));
          return 0;
        })));
    }));
  }
}
