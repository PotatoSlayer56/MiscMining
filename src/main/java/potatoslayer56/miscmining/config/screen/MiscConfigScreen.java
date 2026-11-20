package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import potatoslayer56.miscmining.config.SaveLoad;
import potatoslayer56.miscmining.config.Variables;

public class MiscConfigScreen extends Screen{
  private final @Nullable Screen parent;

  int effectiveWindowWidth;
  int effectiveWindowHeight;

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

    ToggleWidget shamelessPlugToggle = new ToggleWidget(14,32,20,20, Variables.shamelessPlug);
    StringWidget shamelessPlugTitle = new StringWidget(40,38,this.font.width("Shameless Plug"),this.font.lineHeight,Component.literal("Shameless Plug"),this.font);
    StringWidget shamelessPlugDescription = new StringWidget(14,60,this.font.width("Advertises in party chat whenever the mod automatically warps"),this.font.lineHeight,Component.literal("Advertises in party chat whenever the mod automatically warps"),this.font);

    Button backButton = Button.builder(Component.literal("Back"), (button -> {
      Variables.shamelessPlug = shamelessPlugToggle.getState();
      SaveLoad.save();
      Minecraft.getInstance().gui.setScreen(new MainConfigScreen(this.parent));
    })).bounds(30,(int) Math.floor(effectiveWindowHeight * 0.85),32,20).build();

    this.addRenderableWidget(shamelessPlugToggle);
    this.addRenderableWidget(shamelessPlugTitle);
    this.addRenderableWidget(shamelessPlugDescription);
    this.addRenderableWidget(backButton);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    graphics.fill(0, 0, effectiveWindowWidth, effectiveWindowHeight, 0xff3d3c42);
    graphics.fill(4, 4, effectiveWindowWidth-4, effectiveWindowHeight-4, 0xff232226);
    super.extractRenderState(graphics, mouseX, mouseY, delta);
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().gui.setScreen(this.parent);
  }
}
