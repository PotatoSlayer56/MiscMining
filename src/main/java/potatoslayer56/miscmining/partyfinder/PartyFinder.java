package potatoslayer56.miscmining.partyfinder;

import org.json.JSONArray;
import org.json.JSONObject;
import potatoslayer56.miscmining.MiscMining;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PartyFinder {
  public static String pfDataString;
  public static JSONArray pfDataArray;

  public static List<Party> parties = new ArrayList<>();

  public static boolean inPartyFinder = false;

  public static String serverAddress = "http://[2001:19f0:5400:1286:5400:07ff:fe06:2a19]/api/data";
  //public static String serverAddress = "http://127.0.0.1:5678/api/data";

  public static int partySize;

  public static int currentPartyID;

  public static void init() {
    updatePartyFinderData();
  }

  public static void updatePartyFinderData(){
    try {
      pfDataString = getPartyFinderData();
      pfDataArray = new JSONArray(pfDataString);
    } catch (Exception e) {
    }
  }

  public static void getParties(){
    try {
      updatePartyFinderData();
      parties = new ArrayList<>();
      pfDataArray.forEach(party -> {
        JSONObject data = (JSONObject) party;
        int id = data.getInt("id");
        String note = data.getString("note");
        String owner = data.getString("owner");
        int size = data.getInt("size");
        parties.add(new Party(id, note, owner, size));
      });
    } catch (Exception e) {
    }
  }

  private static String getPartyFinderData() {
    String fullJsonString = "";
    try {
      URL url = new URL(serverAddress);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      // Read the response
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      String line;
      while ((line = in.readLine()) != null) {
        fullJsonString += line;
      }
      in.close();

      return fullJsonString;

    } catch (Exception e) {
    }

    return "";
  }

  public static void registerParty(Party party) {
    JSONObject json = new JSONObject();
    currentPartyID = ThreadLocalRandom.current().nextInt(1, 1000000000);
    json.append("id", currentPartyID);
    json.append("note", party.note);
    json.append("owner", party.owner);
    json.append("size", party.size);

    try {
      URL url = new URL(serverAddress);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json; utf-8");
      connection.setRequestProperty("Accept", "application/json");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);

      connection.setDoOutput(true);

      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }

      MiscMining.LOGGER.info(connection.getResponseMessage());

      connection.disconnect();
    } catch (Exception e) {
    }
  }

  public static void unregisterParty(int id){
    try {
      URL url = new URL("%s/unregister".formatted(serverAddress));
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json; utf-8");
      connection.setRequestProperty("Accept", "application/json");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);

      connection.setDoOutput(true);

      JSONObject json = new JSONObject();
      json.append("id", id);

      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }

      MiscMining.LOGGER.info(connection.getResponseMessage());

      connection.disconnect();
    } catch (Exception e) {
    }
  }
}
