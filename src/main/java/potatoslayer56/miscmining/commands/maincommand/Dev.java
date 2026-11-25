package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.commands.CustomCommand;
import potatoslayer56.miscmining.rendering.ModRenderPipeline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dev implements CustomCommand {
  @Override
  public void execute() {
    String data = "Party > [MVP+] PotatoSlayer56: {MiscMiningWP-x:100-y:100-z:100}";
    Pattern pattern = Pattern.compile("\\{MiscMiningWP-x:\\d{3}-y:\\d{3}-z:\\d{3}\\}");
    Matcher matcher = pattern.matcher(data);
    if (matcher.find()){
      String wpMessage = matcher.group(0);
      String[] splits = wpMessage.split("-");
      String xString = splits[1].replace("x:","");
      String yString = splits[2].replace("y:","");
      String zString = splits[3].replace("z:","").replace("}","");
      int x = Integer.parseInt(xString);
      int y = Integer.parseInt(yString);
      int z = Integer.parseInt(zString);
      ModRenderPipeline.waypoints.add(new ModRenderPipeline.WaypointRenderState(x,y,z,0f,0f,1f,1f, ModRenderPipeline.waypointTypes.SINGLE));
    }
  }
}
