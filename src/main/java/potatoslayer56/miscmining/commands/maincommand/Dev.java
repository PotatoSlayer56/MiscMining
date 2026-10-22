package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import potatoslayer56.miscmining.commands.CustomCommand;

import java.util.Collection;

import static potatoslayer56.miscmining.MiscMining.client;
import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;
import static potatoslayer56.miscmining.ShaftParty.getLapisCorpseCount;

public class Dev implements CustomCommand {
  @Override
  public void execute() {
    clientPrefixedMessage(String.valueOf(getLapisCorpseCount()));
  }
}
