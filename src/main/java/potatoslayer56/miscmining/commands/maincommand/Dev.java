package potatoslayer56.miscmining.commands.maincommand;

import net.hypixel.modapi.HypixelModAPI;
import net.hypixel.modapi.packet.impl.serverbound.ServerboundPartyInfoPacket;
import potatoslayer56.miscmining.commands.CustomCommand;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static potatoslayer56.miscmining.LapisParty.inLapisParty;
import static potatoslayer56.miscmining.LapisParty.waitingForPartyTransfer;
import static potatoslayer56.miscmining.MiscMining.clientMessage;
import static potatoslayer56.miscmining.MiscMining.sendCommandFromPlayer;
import static potatoslayer56.miscmining.ShaftParty.*;
import static potatoslayer56.miscmining.ShaftParty.waitingForJoin;

public class Dev implements CustomCommand {
  @Override
  public void execute() {
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    sendCommandFromPlayer("pc !reinvite");
    scheduler.schedule(() -> { sendCommandFromPlayer("p leave"); },1000L, TimeUnit.MILLISECONDS);
    scheduler.schedule(() -> { sendCommandFromPlayer("p %s".formatted("member")); },2000L, TimeUnit.MILLISECONDS);
    scheduler.shutdown();
  }
}
