package potatoslayer56.miscmining.config;

import org.json.JSONObject;
import potatoslayer56.miscmining.MiscMining;

import java.io.*;
import java.util.HashMap;

public class SaveLoad {

  public static String configPath = "config/miscmining.json";

  public static void save(){
    JSONObject jsonData = new JSONObject();
    jsonData.put("lapiscorpses", Variables.shaftLapisCounts);
    jsonData.put("crystalnotify", Variables.crystalNotify);
    jsonData.put("profitshare", Variables.nucleusRunProfitShare);
    jsonData.put("shamelessplug", Variables.shamelessPlug);
    String jsonString = jsonData.toString();
    try {
      FileWriter fileWriter = new FileWriter(configPath);
      fileWriter.write(jsonString);
      fileWriter.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void load(){
    try {
      File configFile = new File(configPath);
      BufferedReader bufferedReader = new BufferedReader(new FileReader(configFile));
      StringBuilder data = new StringBuilder();
      String st;

      while ((st = bufferedReader.readLine()) != null){
        data.append(st);
      }

      JSONObject jsonData = new JSONObject(data.toString());

      Variables.crystalNotify = jsonData.getBoolean("crystalnotify");
      Variables.nucleusRunProfitShare = jsonData.getBoolean("profitshare");
      Variables.shamelessPlug = jsonData.getBoolean("shamelessplug");

      Variables.shaftLapisCounts = new HashMap<>();

      jsonData.getJSONObject("lapiscorpses").toMap().forEach((type, count) -> {
        Variables.shaftLapisCounts.put(type, (Integer) count);
      });

      Variables.defaultLapisCountsConfig().forEach((type, count) -> {
        Variables.shaftLapisCounts.putIfAbsent(type, count);
      });

    } catch (Exception e) {
      MiscMining.LOGGER.info("Error with config file");
      Variables.setDefaults();
      save();
    }
  }
}
