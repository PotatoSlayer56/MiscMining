package potatoslayer56.miscmining.commands.maincommand;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.rendering.ModRenderPipeline;

import static potatoslayer56.miscmining.MiscMining.atBalPosition;
import static potatoslayer56.miscmining.MiscMining.balWaypoint;

public class AtBalCommand implements CustomCommand {
  @Override
  public void execute() {

    Vec3 pos = Minecraft.getInstance().player.position();
    int x = (int) pos.x();
    int y = (int) pos.y();
    int z = (int) pos.z();
    atBalPosition = new int[]{x-1, y, z-1, x+2, y+1, z+2};
    balWaypoint = new ModRenderPipeline.WaypointRenderState(x, y, z, 1, 0, 0, 1, ModRenderPipeline.waypointTypes.THREEBYTHREE);
  }
}
