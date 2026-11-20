package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class ToggleWidget extends AbstractWidget {
  private boolean state;

  public ToggleWidget(int x, int y, int width, int height, boolean state) {
    this.state = state;
    super(x, y, width, height, Component.empty());
  }

  @Override
  public void onClick(MouseButtonEvent event, boolean doubleClick) {
    this.state = !this.state;
  }

  @Override
  protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    int onColour = 0xFF00FF00;
    int offColour = 0xFFFF0000;
    int backgroundColour = 0xFF000000;
    graphics.fill(getX(),getY(),getX()+getWidth(),getY()+getHeight(),backgroundColour);
    if (this.state) {
      graphics.fill(getX()+2,getY()+2,getX()+getWidth()-2,getY()+getHeight()-2,onColour);
    }
    else {
      graphics.fill(getX()+2,getY()+2,getX()+getWidth()-2,getY()+getHeight()-2,offColour);
    }
  }

  @Override
  protected void updateWidgetNarration(NarrationElementOutput output) {
    return;
  }

  public boolean getState() { return this.state; }
}
