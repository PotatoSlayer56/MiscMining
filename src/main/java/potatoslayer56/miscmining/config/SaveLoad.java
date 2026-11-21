package potatoslayer56.miscmining.config;

import org.json.JSONException;
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
    jsonData.put("lowtoolnotify", Variables.lowToolsNotify);
    jsonData.put("updatechecker", Variables.updateChecker);
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

      try { Variables.crystalNotify = jsonData.getBoolean("crystalnotify"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.nucleusRunProfitShare = jsonData.getBoolean("profitshare"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.shamelessPlug = jsonData.getBoolean("shamelessplug"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.lowToolsNotify = jsonData.getBoolean("lowtoolsnotify"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.updateChecker = jsonData.getBoolean("updatechecker"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }

      Variables.shaftLapisCounts = new HashMap<>();

      try {
        jsonData.getJSONObject("lapiscorpses").toMap().forEach((type, count) -> {
          Variables.shaftLapisCounts.put(type, (Integer) count);
        });
      } catch (JSONException e) {
        MiscMining.log("Entry missing from config");
      }

      Variables.defaultLapisCountsConfig().forEach((type, count) -> {
        Variables.shaftLapisCounts.putIfAbsent(type, count);
      });

    } catch (Exception e) {
      MiscMining.log("Error with config file, setting default");
      Variables.setDefaults();
      save();
    }
  }
}
