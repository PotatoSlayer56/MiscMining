package potatoslayer56.miscmining.commands.maincommand;

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
    shaftType = "UMBE_1";
    int lapisCount = 3;

    if (inShaftParty) {
      ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      AtomicInteger x = new AtomicInteger();
      shaftPartyMembers.forEach((member, type) -> {
        if (shaftCategories.get(type).containsKey(shaftType) && getLapisCorpseCount() >= shaftCategories.get(type).get(shaftType)) {
          scheduler.schedule(() -> {
            clientMessage(member);
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
