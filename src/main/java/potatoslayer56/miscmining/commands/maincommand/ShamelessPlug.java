package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.commands.CustomCommand;

import static potatoslayer56.miscmining.MiscMining.*;

public class ShamelessPlug implements CustomCommand {
  @Override
  public void execute() {
    shamelessPlug = !shamelessPlug;
    clientPrefixedMessage("Shameless Plug: %b".formatted(shamelessPlug));
  }
}
