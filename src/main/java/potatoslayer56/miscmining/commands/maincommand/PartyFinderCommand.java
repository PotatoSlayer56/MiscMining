package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.partyfinder.PartyFinder;
import potatoslayer56.miscmining.partyfinder.PartyFinderScreen;

public class PartyFinderCommand implements CustomCommand {
  @Override
  public void execute() {
    PartyFinder.getParties();
    Minecraft.getInstance().execute(() -> {
      Minecraft.getInstance().gui.setScreen(new PartyFinderScreen(Component.literal("dev spawned"), Minecraft.getInstance().gui.screen()));
    });
  }
}
