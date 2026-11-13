package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.client.Minecraft;
import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.rendering.ModRenderPipeline;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    scheduler.shutdown();
    ModRenderPipeline.waypoints.add(new ModRenderPipeline.WaypointRenderState(x+4,y+10,z+65,0f,1f,0f,1f));
    ModRenderPipeline.waypoints.add(new ModRenderPipeline.WaypointRenderState(x+29,y-32,z+65,0f,1f,0f,1f));
    ModRenderPipeline.waypoints.add(new ModRenderPipeline.WaypointRenderState(x+29,y-32,z+48,0f,1f,0f,1f));
  }
}
