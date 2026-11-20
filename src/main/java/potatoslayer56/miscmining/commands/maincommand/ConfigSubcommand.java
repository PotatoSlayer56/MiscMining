package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.client.Minecraft;
import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.config.screen.LapisCorpseCountConfigScreen;
import potatoslayer56.miscmining.config.screen.MainConfigScreen;

import static potatoslayer56.miscmining.MiscMining.clientMessage;

public class ConfigSubcommand implements CustomCommand {
  @Override
  public void execute() {
    Minecraft.getInstance().execute(() -> {
      Minecraft.getInstance().gui.setScreen(new MainConfigScreen(Minecraft.getInstance().gui.screen()));
    });
  }
}
