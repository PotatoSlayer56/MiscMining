package potatoslayer56.miscmining.config;

import org.apache.logging.log4j.core.jackson.Log4jJsonObjectMapper;
import org.json.JSONObject;
import potatoslayer56.miscmining.MiscMining;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveLoad {

  public static String configPath = "config/miscmining.json";

  public static void save(){
    String jsonData = new JSONObject(Variables.shaftLapisCounts).toString();
    try {
      FileWriter fileWriter = new FileWriter(configPath);
      fileWriter.write(jsonData);
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

      Variables.shaftLapisCounts = new HashMap<>();

      jsonData.toMap().forEach((type, count) -> {
        Variables.shaftLapisCounts.put(type, (Integer) count);
      });

      Variables.defaultLapisCountsConfig().forEach((type, count) -> {
        Variables.shaftLapisCounts.putIfAbsent(type, count);
      });

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
