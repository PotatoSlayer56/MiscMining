package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.client.Minecraft;
import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.config.screen.LapisCorpseCountConfigScreen;

import static potatoslayer56.miscmining.MiscMining.clientMessage;

public class ConfigSubcommand implements CustomCommand {
  @Override
  public void execute() {
    clientMessage(Minecraft.getInstance().gui.screen().getTitle().getString());
    Minecraft.getInstance().gui.setScreen(new LapisCorpseCountConfigScreen(null));
    clientMessage(Minecraft.getInstance().gui.screen().getTitle().getString());
    clientMessage("config");
  }
}
