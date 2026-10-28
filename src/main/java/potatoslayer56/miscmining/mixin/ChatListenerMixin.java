package potatoslayer56.miscmining.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatoslayer56.miscmining.ShaftParty;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static potatoslayer56.miscmining.MiscMining.*;
import static potatoslayer56.miscmining.ShaftParty.*;
import static potatoslayer56.miscmining.LapisParty.*;

@Mixin(ChatListener.class)
public class ChatListenerMixin {
  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void shaftWarp(Component message, boolean remote, CallbackInfo ci) {
    if (!waitingForJoin.isEmpty() && message.getString().contains("joined the party")) {
      waitingForJoin.forEach(ign -> {
        if (message.getString().contains(ign)) {
          sendCommandFromPlayer("p warp");
        }
      });
    }
  }

  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void disbandAfterShaftWarp(Component message, boolean remote, CallbackInfo ci) {
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
  }

  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void autoJoinShaft(Component message, boolean remote, CallbackInfo ci) {
    if (inShaftParty) {
      ShaftParty.shaftPartyMembers.forEach((member, type) -> {
        if (message.getString().contains(member) && message.getString().contains("you to join their party")) {
          sendCommandFromPlayer("p %s".formatted(member));
        }
      });
    }
  }

  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void shaftDetection(Component message, boolean remote, CallbackInfo ci) {
    if (message.getString().startsWith("§aYou are playing on profile:")) {
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
      } else if (inLapisParty && getLapisCorpseCount() >= 2) {
        sendCommandFromPlayer("pc !pt");
        waitingForPartyTransfer = true;
      }
    }
  }

  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void autoWarpLapisParty(Component message, boolean remote, CallbackInfo ci) {
    if (inLapisParty && message.getString().contains("The party was transferred to") && message.getString().contains(Minecraft.getInstance().player.nameAndId().name())) {
      sendCommandFromPlayer("p warp");
    }
  }
}
