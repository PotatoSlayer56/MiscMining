package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.config.screen.CustomScreen;
import potatoslayer56.miscmining.config.screen.LapisCorpseCountConfigScreen;

import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;
import static potatoslayer56.miscmining.ShaftParty.getLapisCorpseCount;

public class Dev implements CustomCommand {
  @Override
  public void execute() {
    Minecraft.getInstance().gui.setScreen(null);
    Minecraft.getInstance().gui.setScreen(new LapisCorpseCountConfigScreen(null));
  }
}
