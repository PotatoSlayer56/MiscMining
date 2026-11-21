package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import potatoslayer56.miscmining.config.Variables;

import static potatoslayer56.miscmining.MiscMining.*;

public class SidebarWidget extends AbstractWidget {

  private final int activeColour = 0xFF4D4E54;
  private final int inactiveColour = 0xFF3D3C42;
  private final int backgroundColour = 0xFF2B2A2E;

  private final ScreenUtils.CATEGORIES activeCategory;
  private final Font font;

  public SidebarWidget(int width, int height, ScreenUtils.CATEGORIES activeCategory, Font font) {
    this.activeCategory = activeCategory;
    this.font = font;
    super(0,0,width,height,Component.empty());
  }

  public void onClick(MouseButtonEvent event, boolean doubleClick) {
    if (between(4,getWidth()-4,(float) event.x()) && between(4,44,(float) event.y()) && this.activeCategory != ScreenUtils.CATEGORIES.CRYSTALHOLLOWS) {
      Minecraft.getInstance().gui.setScreen(new CrystalHollowsConfigScreen(null));
      CrystalHollowsConfigScreen.save();
      MiscConfigScreen.save();
    }
    else if (between(4,getWidth()-4,(float) event.x()) && between(48,88,(float) event.y()) && this.activeCategory != ScreenUtils.CATEGORIES.MISC) {
      Minecraft.getInstance().gui.setScreen(new MiscConfigScreen(null));
      CrystalHollowsConfigScreen.save();
      MiscConfigScreen.save();
    }
  }

  @Override
  protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    graphics.fill(0,0,getWidth(),getHeight(),backgroundColour);
    switch (this.activeCategory){
      case CRYSTALHOLLOWS -> {
        graphics.fill(4,4,getWidth()-4,44,activeColour);
        graphics.fill(4,48,getWidth()-4,88,inactiveColour);
      }
      case MISC -> {
        graphics.fill(4,4,getWidth()-4,44,inactiveColour);
        graphics.fill(4,48,getWidth()-4,88,activeColour);
      }
    }
    if (Variables.updateChecker) {
      graphics.fill(4,getHeight()-44,getWidth()-4,getHeight()-4,activeColour);
      graphics.text(this.font,"Version",8,getHeight()-40,0xFFFFFFFF);
      graphics.text(this.font,"Current: %s".formatted(version),8,getHeight()-28,0xFFFFFFFF);
      graphics.text(this.font,"Latest: %s".formatted(latestVersion),8,getHeight()-16,0xFFFFFFFF);
    }
    graphics.text(this.font,"Crystal Hollows",8,4+(40-this.font.lineHeight)/2,0xFFFFFFFF);
    graphics.text(this.font,"Misc",8,48+(40-this.font.lineHeight)/2,0xFFFFFFFF);
  }

  @Override
  protected void updateWidgetNarration(NarrationElementOutput output) {
    return;
  }
}
