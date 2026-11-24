package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import potatoslayer56.miscmining.config.SaveLoad;
import potatoslayer56.miscmining.config.Variables;

import java.util.HashMap;
import java.util.Map;

import static potatoslayer56.miscmining.config.screen.ScreenUtils.configOption;

public class MiscConfigScreen extends Screen{
  private final @Nullable Screen parent;

  int effectiveWindowWidth;
  int effectiveWindowHeight;

  static Map<String, AbstractWidget> widgetMap = new HashMap<>();

  Minecraft client;

  public MiscConfigScreen(@Nullable Screen parent) {
    super(Component.literal("MiscMiningMiscConfigScreen"));
    this.parent = parent;
  }

  @Override
  protected void init() {
    int guiScale;
    if (Minecraft.getInstance().options.guiScale().get() != 0) {
      guiScale = Minecraft.getInstance().options.guiScale().get();
    } else {
      guiScale = 2;
    }

    client = Minecraft.getInstance();

    effectiveWindowWidth = Minecraft.getInstance().getWindow().getWidth() / guiScale;
    effectiveWindowHeight = Minecraft.getInstance().getWindow().getHeight() / guiScale;

    SidebarWidget sidebarWidget = new SidebarWidget(100, effectiveWindowHeight, ScreenUtils.CATEGORIES.MISC, this.font);

    widgetMap = configOption(114,14,"Shameless Plug","Advertises whenever the mod sends something to party chat",Variables.shamelessPlug,widgetMap,this.font,"shamelessplug");
    widgetMap = configOption(114,54,"Update Checker","Checks for new updates on launch",Variables.updateChecker,widgetMap,this.font,"updatechecker");

    widgetMap.forEach((label, value) -> {
      this.addRenderableWidget(value);
    });
    this.addRenderableWidget(sidebarWidget);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    graphics.fill(0, 0, effectiveWindowWidth, effectiveWindowHeight, 0xff3d3c42);
    graphics.fill(4, 4, effectiveWindowWidth-4, effectiveWindowHeight-4, 0xff232226);
    super.extractRenderState(graphics, mouseX, mouseY, delta);
  }

  @Override
  public void onClose() {
    save();
    Minecraft.getInstance().gui.setScreen(this.parent);
  }

  public static void save() {
    Variables.shamelessPlug = ((ToggleWidget)widgetMap.get("shamelessplugtoggle")).getState();
    Variables.updateChecker = ((ToggleWidget)widgetMap.get("updatecheckertoggle")).getState();
    SaveLoad.save();
  }

  private StringWidget stringWidget(int x, int y, String msg) {
    return new StringWidget(x,y,this.font.width(msg),this.font.lineHeight,Component.literal(msg),this.font);
  }
}
