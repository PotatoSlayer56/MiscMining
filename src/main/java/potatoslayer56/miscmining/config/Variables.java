package potatoslayer56.miscmining.config;

import org.joml.Vector2i;

import java.util.HashMap;
import java.util.Map;

public class Variables {
  public static Map<String, Integer> shaftLapisCounts = new HashMap<>();

  public static boolean shamelessPlug = false;
  public static boolean crystalNotify = false;
  public static boolean lowToolsNotify = false;
  public static boolean updateChecker = true;
  public static boolean nucRunPBTracking = false;
  public static long nucRunPB = 99999999;
  public static boolean moleRemind = false;
  public static boolean dayCounter;
  public static Vector2i dayCounterPosition = new Vector2i(100,100);
  public static boolean parseChatWaypoints = true;
  public static boolean balSpawningMessage = false;

  public static Map<String, Integer> defaultLapisCountsConfig() {
    Map<String, Integer> config = new HashMap<>();
    config.put("JADE_1", 3);
    config.put("AMBE_1", 3);
    config.put("AMET_1", 3);
    config.put("SAPP_1", 3);
    config.put("RUBY_1", 3);
    config.put("RUBY_2", 3);
    config.put("RUBY_C", 3);
    config.put("TOPA_1", 4);
    config.put("JASP_1", 0);
    config.put("JASP_C", 0);
    config.put("PERI_1", 3);
    config.put("PERI_2", 3);
    config.put("PERI_C", 3);
    config.put("CITR_1", 4);
    config.put("CITR_2", 4);
    config.put("CITR_C", 4);
    config.put("OPAL_1", 4);
    config.put("OPAL_2", 4);
    config.put("OPAL_C", 4);
    config.put("AQUA_1", 4);
    config.put("AQUA_2", 4);
    config.put("AQUA_C", 4);
    config.put("ONYX_1", 4);
    config.put("ONYX_2", 4);
    config.put("ONYX_C", 4);
    return config;
  }

  public static void setDefaults() {
    shaftLapisCounts = defaultLapisCountsConfig();
    shamelessPlug = false;
    crystalNotify = false;
  }
}
