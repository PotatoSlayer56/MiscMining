package potatoslayer56.miscmining.partyfinder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static potatoslayer56.miscmining.MiscMining.clientPrefixedMessage;
import static potatoslayer56.miscmining.MiscMining.sendCommandFromPlayer;

public class PartyFinderScreen extends Screen {
  private final @Nullable Screen parent;

  private int effectiveWindowWidth;
  private int effectiveWindowHeight;

  public PartyFinderScreen(Component title, @Nullable Screen parent) {
    this.parent = parent;
    super(title);
  }

  @Override
  public void init(){
    List<PartyGuiElement> partyGuiElements = new ArrayList<>();

    int guiScale;
    if (Minecraft.getInstance().options.guiScale().get() != 0) {
      guiScale = Minecraft.getInstance().options.guiScale().get();
    } else {
      guiScale = 2;
    }

    effectiveWindowWidth = Minecraft.getInstance().getWindow().getWidth() / guiScale;
    effectiveWindowHeight = Minecraft.getInstance().getWindow().getHeight() / guiScale;

    int x = 10;
    AtomicInteger y = new AtomicInteger();
    y.set(10);

    PartyFinder.parties.forEach(party -> {
      StringWidget tempNoteWidget = new StringWidget(x, y.get(), 100, font.lineHeight, Component.literal(party.note.replace("&", "§").replace("§§", "&")), font);
      StringWidget tempMembersWidget = new StringWidget(x + 150, y.get(), 400, font.lineHeight, Component.literal(party.getPartyDisplayNames()), font);
      Button tempJoinButton = Button.builder(Component.literal("Join"), (button -> {
        sendCommandFromPlayer("p %s".formatted(party.members.getFirst().getFirst()));
      })).bounds(x + 700, y.get(), font.width("Join") + 16, font.lineHeight + 8).build();
      y.addAndGet(32);
      partyGuiElements.add(new PartyGuiElement(tempNoteWidget, tempMembersWidget, tempJoinButton));
    });

    partyGuiElements.forEach(element -> {
      this.addRenderableWidget(element.noteWidget);
      this.addRenderableWidget(element.membersWidget);
      this.addRenderableWidget(element.joinButton);
    });

    EditBox noteBox = new EditBox(this.font, 800, 10, effectiveWindowWidth-810, 20, Component.empty());

    Button registerPartyButton = Button.builder(Component.literal("Register Party"), (button -> {
      if (!PartyFinder.inPartyFinder) {
        PartyFinder.registerParty(new Party(0, noteBox.getValue(), PartyFinder.partyMembers));
        PartyFinder.inPartyFinder = true;
      }
    })).bounds(800, 40, font.width("Register Party")+16, font.lineHeight + 8).build();

    Button refreshButton = Button.builder(Component.literal("Refresh Party Finder"), (button -> {
      PartyFinder.getParties();
      Minecraft.getInstance().gui.setScreen(new PartyFinderScreen(Component.literal("2LPartyFinder"), this.parent));
    })).bounds(800, 70, font.width("Refresh Party Finder")+16, font.lineHeight + 8).build();

    this.addRenderableWidget(noteBox);
    this.addRenderableWidget(registerPartyButton);
    this.addRenderableWidget(refreshButton);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    graphics.fill(0, 0, effectiveWindowWidth, effectiveWindowHeight, 0xff3d3c42);
    graphics.fill(4, 4, effectiveWindowWidth-4, effectiveWindowHeight-4, 0xff232226);
    super.extractRenderState(graphics, mouseX, mouseY, delta);
  }

  @Override
  public void onClose() {
    Minecraft.getInstance().gui.setScreen(this.parent);
  }
}
