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

import static potatoslayer56.miscmining.config.Variables.nucRunPB;
import static potatoslayer56.miscmining.config.screen.ScreenUtils.configOption;

public class CrystalHollowsConfigScreen extends Screen{
  private final @Nullable Screen parent;

  int effectiveWindowWidth;
  int effectiveWindowHeight;

  static Map<String, AbstractWidget> widgetMap = new HashMap<>();

  Minecraft client;

  public CrystalHollowsConfigScreen(@Nullable Screen parent) {
    super(Component.literal("MiscMiningCrystalHollowsConfigScreen"));
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

    widgetMap = configOption(114,14,"Crystal Notify","Send crystal progress in party chat",Variables.crystalNotify,widgetMap,this.font,"crystalnotify");
    widgetMap = configOption(114,54,"Profit Share","Sends SkyHanni's Nucleus Run profit calculation in party chat",Variables.nucleusRunProfitShare,widgetMap,this.font,"profitshare");
    widgetMap = configOption(114,94,"Low Tools Notify","Prompts you to refill on tools whenever you run out",Variables.lowToolsNotify,widgetMap,this.font,"lowtoolsnotify");
    widgetMap = configOption(114,134,"Mole Reminder","Reminds you to equip mole after grabbing all 5 crystals",Variables.moleRemind,widgetMap,this.font,"moleremind");
    widgetMap = configOption(114,174,"Nucleus Run PBs","Tracks your nucleus run pb - Current PB: %.2f".formatted(((float)nucRunPB)/1000),Variables.nucRunPBTracking,widgetMap,this.font,"nucrunpb");

    SidebarWidget sidebarWidget = new SidebarWidget(100, effectiveWindowHeight, ScreenUtils.CATEGORIES.CRYSTALHOLLOWS, this.font);

    widgetMap.forEach((label, value) -> {
      this.addRenderableWidget(value);
    });
    this.addRenderableWidget(sidebarWidget);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    graphics.fill(100, 0, effectiveWindowWidth, effectiveWindowHeight, 0xff3d3c42);
    graphics.fill(100, 4, effectiveWindowWidth-4, effectiveWindowHeight-4, 0xff232226);
    super.extractRenderState(graphics, mouseX, mouseY, delta);
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().gui.setScreen(this.parent);
  }

  public static void save() {
    Variables.crystalNotify = ((ToggleWidget) widgetMap.get("crystalnotifytoggle")).getState();
    Variables.nucleusRunProfitShare = ((ToggleWidget) widgetMap.get("profitsharetoggle")).getState();
    Variables.lowToolsNotify = ((ToggleWidget) widgetMap.get("lowtoolsnotifytoggle")).getState();
    Variables.moleRemind = ((ToggleWidget) widgetMap.get("moleremindtoggle")).getState();
    Variables.nucRunPBTracking = ((ToggleWidget) widgetMap.get("nucrunpbtoggle")).getState();
    SaveLoad.save();
  }

  private StringWidget stringWidget(int x, int y, String msg) {
    return new StringWidget(x,y,this.font.width(msg),this.font.lineHeight,Component.literal(msg),this.font);
  }
}
