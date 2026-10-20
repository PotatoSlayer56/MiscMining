package potatoslayer56.miscmining.commands.maincommand;

import org.json.JSONException;
import org.json.JSONObject;
import potatoslayer56.miscmining.commands.CustomCommand;

import static potatoslayer56.miscmining.MiscMining.*;

public class RefreshPrices implements CustomCommand {
  @Override
  public void execute() {
    try {
      bazaarData = new JSONObject(getBazaarData());
      bazaarProducts = bazaarData.getJSONObject("products");
      locketPrice = getLowestBin("SHATTERED_PENDANT");
      wispPrice = getLowestBin("CAGED_WISP");
      frostDyePrice = getLowestBin("DYE_FROSTBITTEN");
    } catch (JSONException e) {
      clientMessage("§8[§bMisc§dMining§8] §cFailed to refresh prices");
    }
  }
}
