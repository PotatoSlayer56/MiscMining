package potatoslayer56.miscmining.config.screen;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;

import java.util.Map;

public class ScreenUtils {

  public enum CATEGORIES {
    CRYSTALHOLLOWS,
    MISC
  }

  public static Map<String, AbstractWidget> configOption(int x, int y, String title, String description, boolean option, Map<String, AbstractWidget> widgetMap, Font font, String id) {
    ToggleWidget toggleWidget = new ToggleWidget(x,y,20,20,option);
    StringWidget titleWidget = stringWidget(x+26,y+6,title,font);
    StringWidget descriptionWidget = stringWidget(x,y+24,description,font);
    widgetMap.put("%stoggle".formatted(id), toggleWidget);
    widgetMap.put("%stitle".formatted(id), titleWidget);
    widgetMap.put("%sdescription".formatted(id), descriptionWidget);
    return widgetMap;
  }

  private static StringWidget stringWidget(int x, int y, String msg, Font font) {
    return new StringWidget(x,y,font.width(msg),font.lineHeight, Component.literal(msg),font);
  }
}
