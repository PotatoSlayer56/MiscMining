package potatoslayer56.miscmining.config;

import org.joml.Vector2i;
import org.json.JSONException;
import org.json.JSONObject;
import potatoslayer56.miscmining.LapisParty;
import potatoslayer56.miscmining.MiscMining;

import java.io.*;
import java.util.HashMap;

public class SaveLoad {

  public static String configPath = "config/miscmining.json";

  public static void save(){
    JSONObject jsonData = new JSONObject();
    jsonData.put("lapiscorpses", Variables.shaftLapisCounts);
    jsonData.put("crystalnotify", Variables.crystalNotify);
    jsonData.put("shamelessplug", Variables.shamelessPlug);
    jsonData.put("lowtoolsnotify", Variables.lowToolsNotify);
    jsonData.put("updatechecker", Variables.updateChecker);
    jsonData.put("nucrunpbtracking", Variables.nucRunPBTracking);
    jsonData.put("nucrunpb", Variables.nucRunPB);
    jsonData.put("moleremind", Variables.moleRemind);
    jsonData.put("daycounter", Variables.dayCounter);
    jsonData.put("daycounterposx", Variables.dayCounterPosition.x);
    jsonData.put("daycounterposy", Variables.dayCounterPosition.y);
    jsonData.put("lapisparty", LapisParty.inLapisParty);
    jsonData.put("parsechatwaypoints", Variables.parseChatWaypoints);
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
      try { Variables.shamelessPlug = jsonData.getBoolean("shamelessplug"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.lowToolsNotify = jsonData.getBoolean("lowtoolsnotify"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.updateChecker = jsonData.getBoolean("updatechecker"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.moleRemind = jsonData.getBoolean("moleremind"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.nucRunPBTracking = jsonData.getBoolean("nucrunpbtracking"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.nucRunPB = jsonData.getLong("nucrunpb"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { LapisParty.inLapisParty = jsonData.getBoolean("lapisparty"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.dayCounter = jsonData.getBoolean("daycounter"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.dayCounterPosition.x = jsonData.getInt("daycounterposx"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.dayCounterPosition.y = jsonData.getInt("daycounterposy"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }
      try { Variables.parseChatWaypoints = jsonData.getBoolean("parsechatwaypoints"); } catch (JSONException e) { MiscMining.log("Entry missing from config"); }

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
