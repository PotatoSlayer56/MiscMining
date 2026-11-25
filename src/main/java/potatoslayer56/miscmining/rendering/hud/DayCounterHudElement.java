package potatoslayer56.miscmining.rendering.hud;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import potatoslayer56.miscmining.config.Variables;

public class DayCounterHudElement {
  public static void register(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
    if (Minecraft.getInstance().level != null && Variables.dayCounter) {
      int day = (int) (Minecraft.getInstance().level.getOverworldClockTime() / 24000L);
      graphics.text(Minecraft.getInstance().font, Component.literal("Day %s%d".formatted(dayCounterColour(day),day)), Variables.dayCounterPosition.x(), Variables.dayCounterPosition.y(), 0xFFFFFFFF);
    }
  }

  private static String dayCounterColour(int d) {
    if (d < 19) {
      return "§a";
    }
    else {
      return "§c";
    }
  }
}
