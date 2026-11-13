package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.LapisParty;
import potatoslayer56.miscmining.commands.CustomCommand;

import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;

public class ToggleLapisParty implements CustomCommand {
  @Override
  public void execute() {
    LapisParty.inLapisParty = !LapisParty.inLapisParty;
    clientPrefixedMessage("Lapis Party: %s".formatted(LapisParty.inLapisParty));
  }
}
