package potatoslayer56.miscmining.commands.maincommand;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.server.permissions.PermissionSet;
import potatoslayer56.miscmining.MiscMining;
import potatoslayer56.miscmining.commands.CustomCommand;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;
import static potatoslayer56.miscmining.MiscMining.sendCommandFromPlayer;

public class JungleCheese implements CustomCommand {
  @Override
  public void execute() {
    assert Minecraft.getInstance().player != null;
    int x = (int) Minecraft.getInstance().player.position().x;
    int y = (int) Minecraft.getInstance().player.position().y;
    int z = (int) Minecraft.getInstance().player.position().z;


    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    sendCommandFromPlayer("pc x:%d,y:%d,z:%d".formatted(x+4, y+10, z+65));
    scheduler.schedule(() -> { sendCommandFromPlayer("pc x:%d,y:%d,z:%d".formatted(x+29, y-32, z+65)); },1000L, TimeUnit.MILLISECONDS);
    scheduler.schedule(() -> { sendCommandFromPlayer("pc x:%d,y:%d,z:%d".formatted(x+29, y-32, z+48)); },2000L, TimeUnit.MILLISECONDS);
    scheduler.schedule(() -> {
      try {
        Minecraft.getInstance().getConnection().getCommands().execute("waypointer add at %d %d %d 1".formatted(x+4, y+10, z+65), new ClientSuggestionProvider(Minecraft.getInstance().getConnection(), Minecraft.getInstance(), PermissionSet.ALL_PERMISSIONS));
      } catch (Exception e) {
        MiscMining.LOGGER.info(e.toString());
        clientPrefixedMessage(e.toString());
      }
    },3000L, TimeUnit.MILLISECONDS);
    scheduler.schedule(() -> {
      try {
        Minecraft.getInstance().getConnection().getCommands().execute("waypointer add at %d %d %d 2".formatted(x+29, y-32, z+65), new ClientSuggestionProvider(Minecraft.getInstance().getConnection(), Minecraft.getInstance(), PermissionSet.ALL_PERMISSIONS));
      } catch (Exception e) {
        MiscMining.LOGGER.info(e.toString());
        clientPrefixedMessage(e.toString());
      }
    },4000L, TimeUnit.MILLISECONDS);
    scheduler.schedule(() -> {
      try {
        Minecraft.getInstance().getConnection().getCommands().execute("waypointer add at %d %d %d 3".formatted(x+29, y-32, z+48), new ClientSuggestionProvider(Minecraft.getInstance().getConnection(), Minecraft.getInstance(), PermissionSet.ALL_PERMISSIONS));
      } catch (Exception e) {
        MiscMining.LOGGER.info(e.toString());
        clientPrefixedMessage(e.toString());
      }
    },5000L, TimeUnit.MILLISECONDS);
    scheduler.shutdown();
  }
}
