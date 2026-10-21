package potatoslayer56.miscmining;

import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.clientbound.event.ClientboundLocationPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static potatoslayer56.miscmining.MiscMining.client;
import static potatoslayer56.miscmining.MiscMining.sendCommandFromPlayer;

public class ShaftParty {

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
    "CIRT_2",
    "CIRT_3",
    "AQUA_1",
    "AQUA_2",
    "AQUA_C",
    "FAIR_1",
    "LITT_L"
  );

  public static boolean inShaftParty = false;
  public static String shaftType = "N/A";
  public static Map<String, String> shaftPartyMemebers = new HashMap<>();

  public static String waitingForJoin = "";

  public static void init() {

    HypixelModAPI.getInstance().createHandler(ClientboundLocationPacket.class, clientboundLocationPacket -> {
      shaftType = getPlayerShaftType();

      if (inShaftParty) {
        shaftPartyMemebers.forEach((member, type) -> {
          if (type.contains(shaftType)) {
            sendCommandFromPlayer("p %s".formatted(member));
          }
        });
      }
    });

    HypixelModAPI.getInstance().subscribeToEventPacket(ClientboundLocationPacket.class);
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
