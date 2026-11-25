package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.client.Minecraft;
import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.config.screen.HudDragScreen;

public class HudCommand implements CustomCommand {
  @Override
  public void execute() {
    Minecraft.getInstance().execute(() -> {
      Minecraft.getInstance().gui.setScreen(new HudDragScreen(Minecraft.getInstance().gui.screen()));
    });
  }
}
