package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;
import org.jspecify.annotations.Nullable;
import potatoslayer56.miscmining.config.SaveLoad;
import potatoslayer56.miscmining.config.Variables;

import java.util.HashMap;
import java.util.Map;

public class HudDragScreen extends Screen {
  private final Screen parent;
  private final Map<String, AbstractWidget> widgetMap = new HashMap<>();

  public HudDragScreen(@Nullable Screen parent) {
    this.parent = parent;
    super(Component.literal("HudDragScreen"));
  }

  @Override
  protected void init() {
    int guiScale;
    if (Minecraft.getInstance().options.guiScale().get() != 0) {
      guiScale = Minecraft.getInstance().options.guiScale().get();
    } else {
      guiScale = 2;
    }

    widgetMap.put("dayCounter", new DraggableHudWidget(Variables.dayCounterPosition.x(),Variables.dayCounterPosition.y(),this.font.width("Day Counter")+8, this.font.lineHeight+4, Component.literal("Day Counter"), this.font));

    widgetMap.forEach((name, widget) -> {
      this.addRenderableWidget(widget);
    });
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    super.extractRenderState(graphics, mouseX, mouseY, delta);
  }

  @Override
  public void onClose() {
    Variables.dayCounterPosition = new Vector2i(widgetMap.get("dayCounter").getX(), widgetMap.get("dayCounter").getY());
    SaveLoad.save();
    Minecraft.getInstance().gui.setScreen(this.parent);
  }
}
