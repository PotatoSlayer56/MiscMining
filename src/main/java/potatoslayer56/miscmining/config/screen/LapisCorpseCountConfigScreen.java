package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import potatoslayer56.miscmining.config.SaveLoad;
import potatoslayer56.miscmining.config.Variables;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static potatoslayer56.miscmining.ShaftParty.shaftCategories;

public class LapisCorpseCountConfigScreen extends Screen {
  private final @Nullable Screen parent;

  int effectiveWindowWidth;
  int effectiveWindowHeight;

  public LapisCorpseCountConfigScreen(@Nullable Screen parent) {
    super(Component.literal("MiscMiningLapisConfigScreen"));
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

    effectiveWindowWidth = Minecraft.getInstance().getWindow().getWidth() / guiScale;
    effectiveWindowHeight = Minecraft.getInstance().getWindow().getHeight() / guiScale;

    Map<String, Map<StringWidget, EditBox>> widgets = new HashMap<>();
    AtomicInteger x = new AtomicInteger();
    AtomicInteger y = new AtomicInteger();
    x.set(34);
    y.set(44);
    Variables.shaftLapisCounts.forEach((type, count) -> {
      widgets.put(type, new HashMap<>());
      widgets.get(type).put(
        new StringWidget(
          x.get(),
          y.get(),
          this.font.width(type),
          16,
          Component.literal(type),
          this.font
        ),
        new EditBox(
          this.font,
          x.get() + this.font.width(type) + 16,
          y.get(),
          25,
          16,
          Component.literal("")
        )
      );
      widgets.get(type).forEach((label, editBox) -> {
        editBox.setValue(String.valueOf(Variables.shaftLapisCounts.get(type)));
        this.addRenderableOnly(label);
        this.addRenderableWidget(editBox);
      });
      y.addAndGet(32);
      if (y.get() >= effectiveWindowHeight * 0.75) {
        y.set(44);
        x.addAndGet(100);
      }
    });

    Button backButton = Button.builder(Component.literal("Back"), (button -> {
      widgets.forEach((type, map) -> {
        map.forEach((label, editbox) -> {
          Variables.shaftLapisCounts.replace(type, Integer.valueOf(editbox.getValue()));
        });
      });
      Variables.shaftLapisCounts.forEach((type, count) -> {
        shaftCategories.get("GEMS").put(type, count);
      });
      SaveLoad.save();
      Minecraft.getInstance().gui.setScreen(this.parent);
    })).bounds(
      30,
      (int) Math.floor(effectiveWindowHeight * 0.85),
      32,
      20
    ).build();

    this.addRenderableWidget(backButton);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    graphics.fill(0, 0, effectiveWindowWidth, effectiveWindowHeight, 0xff3d3c42);
    graphics.fill(4, 4, effectiveWindowWidth-4, effectiveWindowHeight-4, 0xff232226);
    graphics.text(font, Component.literal("MiscMining"), 14, 14, 0xFFFFFF, false);
    super.extractRenderState(graphics, mouseX, mouseY, delta);
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().gui.setScreen(this.parent);
  }
}
