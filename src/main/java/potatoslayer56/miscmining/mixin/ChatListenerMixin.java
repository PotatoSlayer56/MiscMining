package potatoslayer56.miscmining.mixin;

import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPartyInfoPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatoslayer56.miscmining.MiscMining;
import potatoslayer56.miscmining.ShaftParty;
import potatoslayer56.miscmining.rendering.ModRenderPipeline;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static potatoslayer56.miscmining.MiscMining.*;
import static potatoslayer56.miscmining.ShaftParty.*;
import static potatoslayer56.miscmining.LapisParty.*;
import static potatoslayer56.miscmining.config.Variables.*;

@Mixin(ChatListener.class)
public class ChatListenerMixin {

  @Unique
  private static boolean nucRunActive;
  @Unique
  private static long nucRunStartTime;

  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void messageHandling(Component message, boolean remote, CallbackInfo ci) {
    if (!message.getString().contains("§9Party §8> ") && !message.getString().contains("§2Guild > ") && !message.getString().contains("§bCo-op > ")) {
      if (!waitingForJoin.isEmpty() && message.getString().contains("joined the party")) {
        waitingForJoin.forEach(ign -> {
          if (message.getString().contains(ign)) {
            sendCommandFromPlayer("p warp");
            if (shamelessPlug) {
              ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
              scheduler.schedule(() -> {
                sendToPartyChat("Automatically warped into 2l+ shaft");
              }, 1000L, TimeUnit.MILLISECONDS);
              scheduler.shutdown();
            }
          }
        });
      }

      if (!waitingForJoin.isEmpty() && message.getString().contains("entered the mineshaft")) {
        waitingForJoin.forEach(ign -> {
          if (message.getString().contains(ign)) {
            waitingForJoin.remove(ign);
          }
          if (waitingForJoin.isEmpty()) {
            sendCommandFromPlayer("p disband");
          }
        });
      }

      if (inShaftParty) {
        ShaftParty.shaftPartyMembers.forEach((member, type) -> {
          if (message.getString().contains(member) && message.getString().contains("you to join their party")) {
            HypixelModAPI.getInstance().sendPacket(new ServerboundPartyInfoPacket());
            if (isInOtherParty) {
              ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
              sendToPartyChat("!reinvite");
              scheduler.schedule(() -> {
                sendCommandFromPlayer("p leave");
              }, 1000L, TimeUnit.MILLISECONDS);
              scheduler.schedule(() -> {
                sendCommandFromPlayer("p %s".formatted(member));
              }, 2000L, TimeUnit.MILLISECONDS);
              scheduler.shutdown();
            } else {
              sendCommandFromPlayer("p %s".formatted(member));
            }
          }
        });
      }

      if (message.getString().startsWith("§aYou are playing on profile:")) {
        ModRenderPipeline.waypoints = new ArrayList<>();
        atBalPosition = null;
        balWaypoint = null;

        if (updateChecker && !sentUpdateNotification && !Objects.equals(latestVersion, version)){
          clientPrefixedMessage("New version available! Your version: %s Latest Version: %s".formatted(version,latestVersion));
          sentUpdateNotification = true;
        }

        HypixelModAPI.getInstance().sendPacket(new ServerboundPartyInfoPacket());
        shaftType = getPlayerShaftType();

        if (inShaftParty) {
          ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
          AtomicInteger x = new AtomicInteger();
          shaftPartyMembers.forEach((member, type) -> {
            if (shaftCategories.get(type).containsKey(shaftType) && getLapisCorpseCount() >= shaftCategories.get(type).get(shaftType)) {
              scheduler.schedule(() -> {
                sendCommandFromPlayer("p %s".formatted(member));
                waitingForJoin.add(member);
              }, 1000L * x.get(), TimeUnit.MILLISECONDS);
              x.addAndGet(1);
            }
          });
          scheduler.shutdown();
        } else if (inLapisParty && getLapisCorpseCount() >= 2) {
          sendToPartyChat("!pt");
          waitingForPartyTransfer = true;
        }
      }

      if (inLapisParty && waitingForPartyTransfer && message.getString().contains("The party was transferred to") && message.getString().contains(Minecraft.getInstance().player.nameAndId().name())) {
        sendCommandFromPlayer("p warp");
        if (shamelessPlug) {
          ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
          scheduler.schedule(() -> {
            sendToPartyChat("Automatically warped into 2l+ shaft");
          }, 1000L, TimeUnit.MILLISECONDS);
          scheduler.shutdown();
        }
        waitingForPartyTransfer = false;
      }

      if (crystalNotify) {
        if (message.getString().contains("✦ CRYSTAL FOUND (1/5)")) {
          sendToPartyChat("1/5");
        } else if (message.getString().contains("✦ CRYSTAL FOUND (2/5)")) {
          sendToPartyChat("2/5");
        } else if (message.getString().contains("✦ CRYSTAL FOUND (3/5)")) {
          sendToPartyChat("3/5");
        } else if (message.getString().contains("✦ CRYSTAL FOUND (4/5)")) {
          sendToPartyChat("4/5");
        } else if (message.getString().contains("✦ CRYSTAL FOUND (5/5)")) {
          sendToPartyChat("5/5");
        }
      }

      if (lowToolsNotify) {
        if (message.getString().contains("Jade Crystal")) {
          boolean hasTools = false;
          for(int i = 0; i <= 36; i++) {
            if (Minecraft.getInstance().player.getInventory().getItem(i).getCustomName() != null && Minecraft.getInstance().player != null){
              if (Minecraft.getInstance().player.getInventory().getItem(i).getCustomName().getString().contains("Scavenged Golden Hammer")) {
                hasTools = true;
              }
            }
          }
          if (!hasTools) {
            Minecraft.getInstance().gui.hud.setTimes(5,20,5);
            Minecraft.getInstance().gui.hud.setTitle(Component.literal("§c§lNo Tools"));
            clientPrefixedCommandMessage("§c§l[OPEN STORAGE]", "/storage");
          }
        }
      }

      if (nucRunPBTracking) {
        if (message.getString().contains("CRYSTAL NUCLEUS LOOT BUNDLE")) {
          if (!nucRunActive) {
            nucRunStartTime = Instant.now().toEpochMilli();
            nucRunActive = true;
          }
          else {
            long endTime = Instant.now().toEpochMilli();
            long elapsed = endTime - nucRunStartTime;
            if (elapsed < nucRunPB) {
              nucRunPB = elapsed;
              clientPrefixedMessage("Run took %.2fs (§d§lNEW PB§f)".formatted(((float)nucRunPB)/1000));
            }
            else {
              clientPrefixedMessage("Run took %.2fs".formatted(((float)nucRunPB)/1000));
            }
          }
        }
      }

      if (moleRemind) {
        if (message.getString().contains("✦ CRYSTAL FOUND (5/5)")) {
          Minecraft.getInstance().gui.hud.setTimes(5,20,5);
          Minecraft.getInstance().gui.hud.setTitle(Component.literal("§c§lEquip Mole"));
        }
      }
    }
  }
}
