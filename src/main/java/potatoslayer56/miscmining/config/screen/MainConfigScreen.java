package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

public class MainConfigScreen extends Screen {
  private final @Nullable Screen parent;

  public MainConfigScreen(@Nullable Screen parent) {
    super(Component.literal("MiscMiningMainConfigScreen"));
    this.parent = parent;
  }
}
