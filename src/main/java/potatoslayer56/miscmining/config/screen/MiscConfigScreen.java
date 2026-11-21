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
    StringWidget shamelessPlugTitle = stringWidget(40,38,"Shameless Plug");
    StringWidget shamelessPlugDescription = stringWidget(14,60,"Advertises in party chat whenever the mod automatically warps");
    ToggleWidget updateCheckerToggle = new ToggleWidget(14,72,20,20, Variables.updateChecker);
    StringWidget updateCheckerTitle = stringWidget(40,78,"Update Checker");
    StringWidget updateCheckerDescription = stringWidget(14,100,"Checks for new updates on launch");

    Button backButton = Button.builder(Component.literal("Back"), (button -> {
      Variables.shamelessPlug = shamelessPlugToggle.getState();
      Variables.updateChecker = updateCheckerToggle.getState();
      SaveLoad.save();
      Minecraft.getInstance().gui.setScreen(new MainConfigScreen(this.parent));
    })).bounds(30,(int) Math.floor(effectiveWindowHeight * 0.85),32,20).build();

    this.addRenderableWidget(shamelessPlugToggle);
    this.addRenderableWidget(shamelessPlugTitle);
    this.addRenderableWidget(shamelessPlugDescription);
    this.addRenderableWidget(updateCheckerToggle);
    this.addRenderableWidget(updateCheckerTitle);
    this.addRenderableWidget(updateCheckerDescription);
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

  private StringWidget stringWidget(int x, int y, String msg) {
    return new StringWidget(x,y,this.font.width(msg),this.font.lineHeight,Component.literal(msg),this.font);
  }
}
