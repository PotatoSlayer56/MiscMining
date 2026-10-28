package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.LapisParty;
import potatoslayer56.miscmining.commands.CustomCommand;

public class ToggleLapisParty implements CustomCommand {
  @Override
  public void execute() {
    LapisParty.inLapisParty = true;
  }
}
