package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;


public class MainConfigScreen extends Screen {
  private final @Nullable Screen parent;

  int effectiveWindowWidth;
  int effectiveWindowHeight;

  Minecraft client;

  public MainConfigScreen(@Nullable Screen parent) {
    super(Component.literal("MiscMiningMainConfigScreen"));
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

    Button lapisCorpseConfigButton = Button.builder(Component.literal("Lapis Corpse Counts"), (button -> {
      client.gui.setScreen(new LapisCorpseCountConfigScreen(this.parent));
    })).bounds(14,32,this.font.width("Lapis Corpse Counts")+16,this.font.lineHeight+8).build();

    Button crystalHollowsConfigButton = Button.builder(Component.literal("Crystal Hollows"), (button -> {
      client.gui.setScreen(new CrystalHollowsConfigScreen(this.parent));
    })).bounds(14,64,this.font.width("Lapis Corpse Counts")+16,this.font.lineHeight+8).build();

    Button miscConfigButton = Button.builder(Component.literal("Misc"), (button -> {
      client.gui.setScreen(new MiscConfigScreen(this.parent));
    })).bounds(14,96,this.font.width("Lapis Corpse Counts")+16,this.font.lineHeight+8).build();

    this.addRenderableWidget(lapisCorpseConfigButton);
    this.addRenderableWidget(crystalHollowsConfigButton);
    this.addRenderableWidget(miscConfigButton);
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
