package potatoslayer56.miscmining;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.loader.api.FabricLoader;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.ClientboundPartyInfoPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

import net.minecraft.world.phys.Vec3;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import potatoslayer56.miscmining.commands.*;
import potatoslayer56.miscmining.commands.maincommand.*;
import potatoslayer56.miscmining.commands.shaftparty.ShaftMiningCommand;
import potatoslayer56.miscmining.config.SaveLoad;
import potatoslayer56.miscmining.config.Variables;
import potatoslayer56.miscmining.partyfinder.PartyFinder;
import potatoslayer56.miscmining.rendering.ModRenderPipeline;
import potatoslayer56.miscmining.rendering.hud.DayCounterHudElement;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MiscMining implements ModInitializer {
  public static final String MOD_ID = "misc-mining";

  public static JSONObject bazaarData;
  public static JSONObject bazaarProducts;

  public static String version = FabricLoader.getInstance().getModContainer(MOD_ID).map(modContainer -> modContainer.getMetadata().getVersion().getFriendlyString()).orElse("unknown");
  public static String latestVersion = "1";

  public static float locketPrice;
  public static float wispPrice;
  public static float frostDyePrice;

  public static boolean sentUpdateNotification = false;

  public static ModRenderPipeline.WaypointRenderState balWaypoint = null;
  public static int[] atBalPosition = null;
  public static boolean inBalPosition = false;

  public static Map<String, CustomCommand> subCommands = new HashMap<>();

  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    locketPrice = getLowestBin("SHATTERED_PENDANT");
    wispPrice = getLowestBin("CAGED_WISP");
    frostDyePrice = getLowestBin("DYE_FROSTBITTEN");

    try {
      bazaarData = new JSONObject(getBazaarData());
      bazaarProducts = bazaarData.getJSONObject("products");
    } catch (JSONException e) {
      MiscMining.error("Failed to fetch bazaar products");
    }

    HypixelModAPI.getInstance().createHandler(ClientboundPartyInfoPacket.class, clientBoundPartyInfoPacket -> {
      ShaftParty.isInOtherParty = clientBoundPartyInfoPacket.isInParty();
      if(clientBoundPartyInfoPacket.isInParty()){
        PartyFinder.partySize = clientBoundPartyInfoPacket.getMemberMap().size();
        if (PartyFinder.partySize >= 4){
          PartyFinder.unregisterParty(PartyFinder.currentPartyID);
        }
      }
    });

    registerSubCommand("metalprices", new MetalPrices());
    registerSubCommand("corpsekeyprofit", new CorpseKeyProfit());
    registerSubCommand("refreshprices", new RefreshPrices());
    registerSubCommand("partyfinder", new PartyFinderCommand());
    registerSubCommand("junglecheese", new JungleCheese());
    registerSubCommand("config", new ConfigSubcommand());
    registerSubCommand("atbal", new AtBalCommand());
    registerSubCommand("hud", new HudCommand());
    //registerSubCommand("dev", new Dev());

    MainCommand.register();
    ShaftParty.init();
    ShaftMiningCommand.register();
    PartyFinder.init();

    SaveLoad.load();

    HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, id("day_counter"), DayCounterHudElement::register);

    ClientTickEvents.END_CLIENT_TICK.register((client) -> {
      if (client.player != null && atBalPosition != null){
        Vec3 pos = client.player.position();
        if (between(atBalPosition[0], atBalPosition[3], (float) pos.x()) && between(atBalPosition[1], atBalPosition[4], (float) pos.y()) && between(atBalPosition[2], atBalPosition[5], (float) pos.z())) {
          if (!inBalPosition) {
            sendToPartyChat("at bal");
          }
          inBalPosition = true;
        }
        else {
          inBalPosition = false;
        }
      }
    });

    String fullJsonString = "";
    try {
      URL url = new URL("https://api.modrinth.com/v2/project/qYu3vA2S/version");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      // Read the response
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        fullJsonString += line;
      }
      in.close();
    } catch (Exception e) {
      MiscMining.log(e.toString());
    }
    try {
      JSONArray jsonData = new JSONArray(fullJsonString);
      latestVersion = jsonData.getJSONObject(0).getString("version_number");
    } catch (JSONException e) {
      MiscMining.log(e.toString());
    }
  }

  public static Identifier id(String path) {
    return Identifier.fromNamespaceAndPath(MOD_ID, path);
  }

  public static String getBazaarData(){
    MiscMining.log("Fetching bazaar prices");
    String fullJsonString = "";
    try {
      URL url = new URL("https://api.hypixel.net/v2/skyblock/bazaar");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      // Read the response
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        fullJsonString += line;
      }
      in.close();
    } catch (Exception e) {
      MiscMining.log(e.toString());
    }
    return fullJsonString;
  }

  public static float getLowestBin(String productID){
    String fullJsonString = "";
    try {
      URL url = new URL("https://sky.coflnet.com/api/auctions/tag/%s/active/bin".formatted(productID));
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");

      // Read the response
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

      String line;
      while ((line = in.readLine()) != null) {
        fullJsonString += line;
      }
      in.close();

      JSONArray lbinData = new JSONArray(fullJsonString);

      return lbinData.getJSONObject(0).getFloat("startingBid");

    } catch (Exception e) {
      MiscMining.log(e.toString());
    }

    return 0;
  }

  public static float getInstaBuy(String productID) {
    try {
      BigDecimal price = (BigDecimal) bazaarProducts.getJSONObject(productID).getJSONObject("quick_status").get("buyPrice");
      return price.floatValue();
    } catch (JSONException e) {
      MiscMining.error("Failed to fetch price for %s".formatted(productID));
      return 0;
    }
  }

  public static float getInstaSell(String productID) {
    try {
      BigDecimal price = (BigDecimal) bazaarProducts.getJSONObject(productID).getJSONObject("quick_status").get("sellPrice");
      return price.floatValue();
    } catch (JSONException e) {
      MiscMining.error("Failed to fetch price for %s".formatted(productID));
      return 0;
    }
  }

  public static void clientMessage(String message) {
    if (Minecraft.getInstance().player != null) Minecraft.getInstance().player.sendSystemMessage(Component.literal(message));
  }

  public static void clientPrefixedMessage(String message) {
    if (Minecraft.getInstance().player != null) Minecraft.getInstance().player.sendSystemMessage(Component.literal("§8[§bMisc§dMining§8]§f %s".formatted(message)));
  }

  public static void clientPrefixedCommandMessage(String message, String command) {
    if (Minecraft.getInstance().player != null) Minecraft.getInstance().player.sendSystemMessage(Component.literal("§8[§bMisc§dMining§8]§f %s".formatted(message)).setStyle(Style.EMPTY.withClickEvent(new ClickEvent.RunCommand(command))));
  }

  public static void sendCommandFromPlayer(String command) {
    if (Minecraft.getInstance().player != null) Minecraft.getInstance().player.connection.sendCommand(command);
  }

  public static void sendToPartyChat(String msg) {
    if (Minecraft.getInstance().player != null) {
      if (Variables.shamelessPlug) {
        Minecraft.getInstance().player.connection.sendCommand("pc [MiscMining] %s".formatted(msg));
      }
      else {
        Minecraft.getInstance().player.connection.sendCommand("pc %s".formatted(msg));
      }
    }
  }

  public static void registerSubCommand(String name, CustomCommand command) {
    subCommands.put(name, command);
  }

  public static boolean between(float p1, float p2, float num) {
    return num >= p1 && p2 >= num;
  }

  public static void log(String msg) {
    MiscMining.LOGGER.info("[MiscMining] %s".formatted(msg));
  }

  public static void error(String msg) {
    MiscMining.LOGGER.error("[MiscMining] %s".formatted(msg));
  }
}
