package potatoslayer56.miscmining.commands.maincommand;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;

import static potatoslayer56.miscmining.MiscMining.subCommands;

public class MainCommand {
  public static void register() {
    ClientCommandRegistrationCallback.EVENT.register(((commandDispatcher, commandBuildContext) -> {
      commandDispatcher.register(ClientCommands.literal("miscmining")
        .then(ClientCommands.argument("subcommand", StringArgumentType.word())
        .suggests(new MainCommandSuggestionsProvider())
        .executes(commandContext -> {
          String arg = StringArgumentType.getString(commandContext, "subcommand");
          subCommands.forEach((key, value) -> {
            if (arg.equals(key)) {
              value.execute();
            }
          });
          return 0;
        })));
    }));
  }
}
