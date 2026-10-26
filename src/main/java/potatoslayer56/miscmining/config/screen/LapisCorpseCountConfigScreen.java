package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;
import potatoslayer56.miscmining.config.Variables;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static potatoslayer56.miscmining.ShaftParty.shaftCategories;

public class LapisCorpseCountConfigScreen extends Screen {
  private final @Nullable Screen parent;

  public LapisCorpseCountConfigScreen(@Nullable Screen parent) {
    super(Component.literal("MiscMiningLapisConfigScreen"));
    this.parent = parent;
  }

  @Override
  protected void init() {
    Map<String, Map<StringWidget, EditBox>> widgets = new HashMap<>();
    AtomicInteger x = new AtomicInteger();
    AtomicInteger y = new AtomicInteger();
    x.set(30);
    y.set(30);
    Variables.shaftLapisCounts.forEach((type, count) -> {
      widgets.put(type, new HashMap<>());
      widgets.get(type).put(
        new StringWidget(
          x.get(),
          y.get(),
          32,
          16,
          Component.literal(type),
          this.font
        ),
        new EditBox(
          this.font,
          x.get() + 48,
          y.get(),
          25,
          16,
          Component.literal("1")
        )
      );
      widgets.get(type).forEach((label, editBox) -> {
        this.addRenderableOnly(label);
        this.addRenderableOnly(editBox);
      });
      y.addAndGet(32);
      if (y.get() >= 500) {
        y.set(30);
        x.addAndGet(100);
      }
    });

    Button saveButton = Button.builder(Component.literal("Save"), (button -> {
      widgets.forEach((type, map) -> {
        map.forEach((label, editbox) -> {
          Variables.shaftLapisCounts.replace(type, Integer.valueOf(editbox.getValue()));
        });
      });
      Variables.shaftLapisCounts.forEach((type, count) -> {
        shaftCategories.get("GEMS").put(type, count);
      });
    })).bounds(
      750,
      30,
      32,
      16
    ).build();

    this.addRenderableWidget(saveButton);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    graphics.text(this.font,Component.literal("yay"), 10, 10, 0xFFFFFFFF, false);
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().gui.setScreen(this.parent);
  }
}
