package potatoslayer56.miscmining.mixin;

import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatoslayer56.miscmining.ShaftParty;

import java.util.Objects;

import static potatoslayer56.miscmining.MiscMining.sendCommandFromPlayer;

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
    if (!Objects.equals(ShaftParty.waitingForJoin, "") && message.getString().contains("has entered the mineshaft")) {
      sendCommandFromPlayer("p disband");
      ShaftParty.waitingForJoin = "";
    }
  }

  @Inject(method="handleSystemMessage", at = @At("TAIL"))
  private void autoJoinShaft(Component message, boolean remote, CallbackInfo ci) {
    if (ShaftParty.inShaftParty) {
      ShaftParty.shaftPartyMemebers.forEach((member, type) -> {
        if (message.getString().contains(member) && message.getString().contains("you to join their party")) {
          sendCommandFromPlayer("p %s".formatted(member));
        }
      });
    }
  }
}
