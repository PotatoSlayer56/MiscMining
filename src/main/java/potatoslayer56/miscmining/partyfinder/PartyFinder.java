package potatoslayer56.miscmining.partyfinder;

import net.minecraft.client.Minecraft;
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
import java.util.Objects;

import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;


public class PartyFinder {
  public static String pfDataString;
  public static JSONArray pfDataArray;

  public static List<Party> parties = new ArrayList<>();

  public static boolean inPartyFinder = false;

  public static String serverAddress = "http://[2001:19f0:5400:1286:5400:07ff:fe06:2a19]/api/data";

  public static List<List<String>> partyMembers = new ArrayList<>();
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
      MiscMining.LOGGER.info(e.toString());
      clientPrefixedMessage(e.toString());
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
        List<List<String>> members = new ArrayList<>();
        data.getJSONArray("members").forEach(member -> {
          JSONObject memberData = (JSONObject) member;
          assert Minecraft.getInstance().player != null;
          if (Objects.equals(memberData.getString("username"), Minecraft.getInstance().player.getPlainTextName())) {
            currentPartyID = id;
          }
          members.add(List.of(memberData.getString("username"), memberData.getString("displayName")));
        });
        parties.add(new Party(id, note, members));
      });
    } catch (Exception e) {
      MiscMining.LOGGER.info(e.toString());
      clientPrefixedMessage(e.toString());
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
      MiscMining.LOGGER.error(e.toString());
      clientPrefixedMessage(e.toString());
    }

    return "";
  }

  public static void registerParty(Party party) {
    JSONObject json = new JSONObject();
    JSONArray members = new JSONArray();
    json.append("note", party.note);
    party.members.forEach(member -> {
      members.put(new JSONObject().append("username", member.getFirst()).append("displayName", member.getLast()));
    });
    json.append("members", members);

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
      MiscMining.LOGGER.error(e.toString());
      clientPrefixedMessage(e.toString());
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

      try (OutputStream os = connection.getOutputStream()) {
        os.write(id);
      }

      MiscMining.LOGGER.info(connection.getResponseMessage());

      connection.disconnect();
    } catch (Exception e) {
      MiscMining.LOGGER.error(e.toString());
      clientPrefixedMessage(e.toString());
    }
  }
}
