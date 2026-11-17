package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.commands.CustomCommand;

import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;
import static potatoslayer56.miscmining.MiscMining.crystalNotify;

public class CrystalNotify implements CustomCommand {
  @Override
  public void execute() {
    crystalNotify = !crystalNotify;
    clientPrefixedMessage("Crystal Notify: %b".formatted(crystalNotify));
  }
}
