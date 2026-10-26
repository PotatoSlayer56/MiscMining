package potatoslayer56.miscmining.mixin;

import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatoslayer56.miscmining.ShaftParty;

import java.util.Objects;

import static potatoslayer56.miscmining.MiscMining.clientMessage;
import static potatoslayer56.miscmining.MiscMining.sendCommandFromPlayer;
import static potatoslayer56.miscmining.ShaftParty.*;

@Mixin(ChatListener.class)
public class ChatListenerMixin {
  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void shaftWarp(Component message, boolean remote, CallbackInfo ci) {
    if (!Objects.equals(ShaftParty.waitingForJoin, "") && message.getString().contains(ShaftParty.waitingForJoin) && message.getString().contains("joined the party")) {
      sendCommandFromPlayer("p warp");
    }
  }

  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void disbandAfterShaftWarp(Component message, boolean remote, CallbackInfo ci) {
    if (!Objects.equals(ShaftParty.waitingForJoin, "") && message.getString().contains("entered the mineshaft")) {
      sendCommandFromPlayer("p disband");
      ShaftParty.waitingForJoin = "";
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
        shaftPartyMembers.forEach((member, type) -> {
          if (shaftCategories.get(type).containsKey(shaftType) && getLapisCorpseCount() >= shaftCategories.get(type).get(shaftType)) {
            sendCommandFromPlayer("p %s".formatted(member));
            waitingForJoin = member;
          }
        });
      }
    }
  }
}
