package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

public class DraggableHudWidget extends AbstractWidget {
  private final Font font;
  private boolean dragging = false;
  private int xOffset = 0;
  private int yOffset = 0;

  public DraggableHudWidget(int x, int y, int width, int height, Component message, Font font) {
    this.font = font;
    super(x, y, width, height, message);
  }

  @Override
  public void onClick(MouseButtonEvent event, boolean doubleClick) {
    this.xOffset = (int) (event.x()-getX());
    this.yOffset = (int) (event.y()-getY());
    dragging = true;
  }

  @Override
  public void onRelease(MouseButtonEvent event) {
    dragging = false;
    this.setPosition((int) (event.x()-xOffset),(int) (event.y()-yOffset));
  }

  @Override
  protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    if (dragging) {
      graphics.fill(mouseX-xOffset,mouseY-yOffset,mouseX-xOffset+getWidth(),mouseY-yOffset+getHeight(),0xFF2B2A2E);
      graphics.text(this.font,getMessage(),mouseX-xOffset+1,mouseY-yOffset+1,0xFFFFFFFF);
    }
    else {
      graphics.fill(getX(),getY(),getX()+getWidth(),getY()+getHeight(),0xFF2B2A2E);
      graphics.text(this.font,getMessage(),getX()+1,getY()+1,0xFFFFFFFF);
    }
  }

  @Override
  protected void updateWidgetNarration(NarrationElementOutput output) {
    return;
  }
}
