package potatoslayer56.miscmining.partyfinder;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;

public class PartyGuiElement {
  public StringWidget noteWidget;
  public StringWidget membersWidget;
  public Button joinButton;

  public PartyGuiElement(StringWidget noteWidget, StringWidget membersWidget, Button joinButton) {
    this.noteWidget = noteWidget;
    this.membersWidget = membersWidget;
    this.joinButton = joinButton;
  }
}
