package potatoslayer56.miscmining;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.resources.Identifier;

import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerScoreEntry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import potatoslayer56.miscmining.commands.*;
import potatoslayer56.miscmining.commands.maincommand.MainCommand;
import potatoslayer56.miscmining.commands.maincommand.MetalPrices;
import potatoslayer56.miscmining.commands.maincommand.RefreshPrices;
import potatoslayer56.miscmining.commands.maincommand.SkeletonKeyProfit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MiscMining implements ModInitializer {
	public static final String MOD_ID = "misc-mining";

	public static JSONObject bazaarData;
	public static JSONObject bazaarProducts;

	public static float locketPrice;
	public static float wispPrice;
	public static float frostDyePrice;

	public static Minecraft client;

	public static Map<String, CustomCommand> subCommands = new HashMap<>();

	public static boolean inShaftParty = false;
	public static String lobbyType;

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		client = Minecraft.getInstance();

		locketPrice = getLowestBin("SHATTERED_PENDANT");
		wispPrice = getLowestBin("CAGED_WISP");
		frostDyePrice = getLowestBin("DYE_FROSTBITTEN");

		try {
			bazaarData = new JSONObject(getBazaarData());
			bazaarProducts = bazaarData.getJSONObject("products");
		} catch (JSONException e) {
			MiscMining.LOGGER.error("Failed to fetch bazaar products");
		}

		registerSubCommand("metalprices", new MetalPrices());
		registerSubCommand("skeletonkeyprofit", new SkeletonKeyProfit());
		registerSubCommand("refreshprices", new RefreshPrices());

		MainCommand.register();

		HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket.class, clientboundLocationPacket -> {
			
		});

		HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket.class);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static String getBazaarData(){
		MiscMining.LOGGER.info("Fetching bazaar prices");
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
			e.printStackTrace();
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
			e.printStackTrace();
		}

		return 0;

	}

	public static float getInstaBuy(String productID) {
		try {
			BigDecimal price = (BigDecimal) bazaarProducts.getJSONObject(productID).getJSONObject("quick_status").get("buyPrice");
			return price.floatValue();
		} catch (JSONException e) {
			MiscMining.LOGGER.error("Failed to fetch price for %s".formatted(productID));
			return 0;
		}
	}

	public static float getInstaSell(String productID) {
		try {
			BigDecimal price = (BigDecimal) bazaarProducts.getJSONObject(productID).getJSONObject("quick_status").get("sellPrice");
			return price.floatValue();
		} catch (JSONException e) {
			MiscMining.LOGGER.error("Failed to fetch price for %s".formatted(productID));
			return 0;
		}
	}

	public static void clientMessage(String message) {
		client.gui.hud.getChat().addClientSystemMessage(Component.literal(message));
	}

	public static void registerSubCommand(String name, CustomCommand command) {
		subCommands.put(name, command);
	}
}
