package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import potatoslayer56.miscmining.LapisParty;
import potatoslayer56.miscmining.config.SaveLoad;
import potatoslayer56.miscmining.config.Variables;

import java.util.HashMap;
import java.util.Map;

import static potatoslayer56.miscmining.config.Variables.nucRunPB;
import static potatoslayer56.miscmining.config.screen.ScreenUtils.configOption;

public class GlaciteTunnelsConfigScreen extends Screen{
  private final @Nullable Screen parent;

  int effectiveWindowWidth;
  int effectiveWindowHeight;

  static Map<String, AbstractWidget> widgetMap = new HashMap<>();

  Minecraft client;

  public GlaciteTunnelsConfigScreen(@Nullable Screen parent) {
    super(Component.literal("MiscMiningGlaciteTunnelsConfigScreen"));
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

    widgetMap = configOption(114,14,"Lapis Party","Toggles lapis party functionality", LapisParty.inLapisParty,widgetMap,this.font,"lapisparty");

    Button lapisCorpseConfigButton = Button.builder(Component.literal("Lapis Corpse Config"), button -> {
      save();
      Minecraft.getInstance().gui.setScreen(new LapisCorpseCountConfigScreen(Minecraft.getInstance().gui.screen()));
    }).bounds(114,54,this.font.width("Lapis Corpse Config")+16,16).build();

    SidebarWidget sidebarWidget = new SidebarWidget(100, effectiveWindowHeight, ScreenUtils.CATEGORIES.GLACITETUNNELS, this.font);

    widgetMap.forEach((label, value) -> {
      this.addRenderableWidget(value);
    });
    this.addRenderableWidget(sidebarWidget);
    this.addRenderableWidget(lapisCorpseConfigButton);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    graphics.fill(100, 0, effectiveWindowWidth, effectiveWindowHeight, 0xff3d3c42);
    graphics.fill(100, 4, effectiveWindowWidth-4, effectiveWindowHeight-4, 0xff232226);
    super.extractRenderState(graphics, mouseX, mouseY, delta);
  }

  @Override
  public void onClose() {
    save();
    Minecraft.getInstance().gui.setScreen(this.parent);
  }

  public static void save() {
    LapisParty.inLapisParty = ((ToggleWidget) widgetMap.get("lapispartytoggle")).getState();
    SaveLoad.save();
  }

  private StringWidget stringWidget(int x, int y, String msg) {
    return new StringWidget(x,y,this.font.width(msg),this.font.lineHeight,Component.literal(msg),this.font);
  }
}
