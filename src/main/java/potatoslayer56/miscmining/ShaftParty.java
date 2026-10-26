package potatoslayer56.miscmining;

import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import potatoslayer56.miscmining.config.Variables;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static potatoslayer56.miscmining.MiscMining.*;

public class ShaftParty {

  public static Map<String, Map<String, Integer>> shaftCategories = new HashMap<>();

  public static List<String> shaftTypes = List.of(
    "TITA_1",
    "TUNG_1",
    "UMBE_1",
    "JADE_1",
    "AMBE_1",
    "TOPA_1",
    "SAPP_1",
    "AMET_1",
    "JASP_1",
    "JASP_C",
    "RUBY_1",
    "RUBY_2",
    "RUBY_C",
    "OPAL_1",
    "OPAL_C",
    "ONYX_1",
    "ONYX_2",
    "ONYX_C",
    "PERI_1",
    "PERI_2",
    "PERI_C",
    "CITR_1",
    "CITR_2",
    "CITR_C",
    "AQUA_1",
    "AQUA_2",
    "AQUA_C",
    "FAIR_1",
    "LITT_L"
  );

  public static boolean inShaftParty = false;
  public static String shaftType = "N/A";
  public static Map<String, String> shaftPartyMembers = new HashMap<>();

  public static String waitingForJoin = "";

  public static void init() {
    shaftCategories.put("UMBER", new HashMap<>());
    shaftCategories.get("UMBER").put("UMBE_1", 0);
    shaftCategories.put("TUNGSTEN", new HashMap<>());
    shaftCategories.get("TUNGSTEN").put("TUNG_1", 0);
    shaftCategories.put("HYBRID", new HashMap<>());
    shaftCategories.get("HYBRID").put("UMBE_1", 0);
    shaftCategories.get("HYBRID").put("TUNG_1", 0);
    shaftCategories.put("TITANIUM", new HashMap<>());
    shaftCategories.get("TITANIUM").put("TITA_1", 0);
    shaftCategories.put("GEMS", new HashMap<>());
    Variables.shaftLapisCounts.forEach((type, count) -> {
      shaftCategories.get("GEMS").put(type, count);
    });
  }

  public static int getLapisCorpseCount() {
    AtomicInteger count = new AtomicInteger();
    Collection<PlayerInfo> tabList = client.player.connection.getListedOnlinePlayers();
    tabList.forEach(playerInfo -> {
      if (playerInfo != null && playerInfo.getTabListDisplayName() != null) {
        if (playerInfo.getTabListDisplayName().getString().contains("Lapis")) {
          count.addAndGet(1);
        }
      }
    });
    return count.get();
  }

  public static String getPlayerShaftType() {
    Scoreboard scoreboard = client.level.getScoreboard();

    Collection<PlayerTeam> playerTeams = scoreboard.getPlayerTeams();

    StringBuilder scoreboardTextBuilder = new StringBuilder();

    playerTeams.forEach(playerTeam -> {
      Component prefix = playerTeam.getPlayerPrefix();
      Component suffix = playerTeam.getPlayerSuffix();

      scoreboardTextBuilder.append(prefix.getString());
      scoreboardTextBuilder.append(suffix.getString());
    });

    String scoreboardText = scoreboardTextBuilder.toString();

    for (String type : shaftTypes) {
      if (scoreboardText.contains(type)) {
        return type;
      }
    }

    return "N/A";
  }
}

// TITA_1 TUNG_1 UMBE_1 JADE_1 AMBE_1 TOPA_1 SAPP_1 AMET_1 JASP_1 JASP_C RUBY_1 RUBY_2 RUBY_C OPAL_1 OPAL_C ONYX_1 ONYX_2 ONYX_C PERI_1 PERI_2 PERI_C CITR_1 CITR_2 CITR_C AQUA_1 AQUA_2 AQUA_C FAIR_1 LITT_L
