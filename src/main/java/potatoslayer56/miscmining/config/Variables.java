package potatoslayer56.miscmining.config;

import java.util.HashMap;
import java.util.Map;

public class Variables {
  public static Map<String, Integer> shaftLapisCounts = new HashMap<>();

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


}
