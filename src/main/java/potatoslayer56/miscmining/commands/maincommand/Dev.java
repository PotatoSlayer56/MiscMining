package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import potatoslayer56.miscmining.commands.CustomCommand;

import java.util.Collection;

import static potatoslayer56.miscmining.MiscMining.client;

public class Dev implements CustomCommand {
  @Override
  public void execute() {
    Scoreboard scoreboard = client.level.getScoreboard();

    Collection<PlayerTeam> playerTeams = scoreboard.getPlayerTeams();

    StringBuilder scoreboardTextBuilder = new StringBuilder();

    playerTeams.forEach(playerTeam -> {
      Component prefix = playerTeam.getPlayerPrefix();
      Component suffix = playerTeam.getPlayerSuffix();
      client.player.sendSystemMessage(prefix);
      client.player.sendSystemMessage(suffix);

      scoreboardTextBuilder.append(prefix.getString());
      scoreboardTextBuilder.append(suffix.getString());
    });

    String scoreboardText = scoreboardTextBuilder.toString();
  }
}
