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

import static potatoslayer56.miscmining.ShaftParty.shaftCategories;

public class CrystalHollowsConfigScreen extends Screen{
  private final @Nullable Screen parent;

  int effectiveWindowWidth;
  int effectiveWindowHeight;

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

    ToggleWidget crystalNotifyToggle = new ToggleWidget(14,32,20,20,Variables.crystalNotify);
    StringWidget crystalNotifyTitle = new StringWidget(40,38,this.font.width("Crystal Notify"),this.font.lineHeight,Component.literal("Crystal Notify"),this.font);
    StringWidget crystalNotifyDescription = new StringWidget(14,60,this.font.width("Send crystal progress in party chat"),this.font.lineHeight,Component.literal("Send crystal progress in party chat"),this.font);
    ToggleWidget profitShareToggle = new ToggleWidget(14,72,20,20,Variables.nucleusRunProfitShare);
    StringWidget profitShareTitle = new StringWidget(40,78,this.font.width("Profit Share"),this.font.lineHeight,Component.literal("Profit Share"),this.font);
    StringWidget profitShareDescription = new StringWidget(14,100,this.font.width("Sends SkyHanni's Nucleus Run profit calculation in party chat"),this.font.lineHeight,Component.literal("Sends SkyHanni's Nucleus Run profit calculation in party chat"),this.font);

    Button backButton = Button.builder(Component.literal("Back"), (button -> {
      Variables.crystalNotify = crystalNotifyToggle.getState();
      Variables.nucleusRunProfitShare = profitShareToggle.getState();
      SaveLoad.save();
      Minecraft.getInstance().gui.setScreen(new MainConfigScreen(this.parent));
    })).bounds(30,(int) Math.floor(effectiveWindowHeight * 0.85),32,20).build();

    this.addRenderableWidget(crystalNotifyToggle);
    this.addRenderableWidget(crystalNotifyTitle);
    this.addRenderableWidget(crystalNotifyDescription);
    this.addRenderableWidget(profitShareToggle);
    this.addRenderableWidget(profitShareTitle);
    this.addRenderableWidget(profitShareDescription);
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
