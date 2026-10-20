package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.commands.CustomCommand;

import static potatoslayer56.miscmining.MiscMining.*;

public class MetalPrices implements CustomCommand {
  @Override
  public void execute() {
    float enchantedTungPrice = getInstaBuy	("ENCHANTED_TUNGSTEN");
    float tungPlatePrice = getInstaBuy("TUNGSTEN_PLATE");
    float tungKeyPrice = getInstaBuy("TUNGSTEN_KEY");
    float refTungPrice = getInstaBuy("REFINED_TUNGSTEN");
    float enchantedUmberPrice = getInstaBuy("ENCHANTED_UMBER");
    float umberPlatePrice = getInstaBuy("UMBER_PLATE");
    float umberKeyPrice = getInstaBuy("UMBER_KEY");
    float refUmberPrice = getInstaBuy("REFINED_UMBER");
    float handlePrice = getInstaBuy("BEJEWELED_HANDLE");
    float glacAmalgamPrice = getInstaBuy("GLACITE_AMALGAMATION");
    clientMessage("§d--------------------------------");
    clientMessage("§7§lTungsten Profit");
    clientMessage("§f §7Enchanted Tungsten\n§f   - §6%,.2f (%,.2f/ench)".formatted(enchantedTungPrice, enchantedTungPrice));
    clientMessage("§f §7Refined Tungsten\n§f   - §6%,.2f (%,.2f/ench)".formatted(refTungPrice, refTungPrice/160));
    clientMessage("§f §7Tungsten Key\n§f   - §6%,.2f (%,.2f/ench)".formatted((tungKeyPrice-handlePrice),(tungKeyPrice-handlePrice)/192));
    clientMessage("§f §7Tungsten Plate\n§f   - §6%,.2f (%,.2f/ench)".formatted((tungPlatePrice-glacAmalgamPrice),(tungPlatePrice-glacAmalgamPrice)/640));
    clientMessage("§c§lUmber Profit");
    clientMessage("§f §cEnchanted Umber\n§f   - §6%,.2f (%,.2f/ench)".formatted(enchantedUmberPrice, enchantedUmberPrice));
    clientMessage("§f §cRefined Umber\n§f   - §6%,.2f (%,.2f/ench)".formatted(refUmberPrice, refUmberPrice/160));
    clientMessage("§f §cUmber Key\n§f   - §6%,.2f (%,.2f/ench)".formatted((umberKeyPrice-handlePrice),(umberKeyPrice-handlePrice)/192));
    clientMessage("§f §cUmber Plate\n§f   - §6%,.2f (%,.2f/ench)".formatted((umberPlatePrice-glacAmalgamPrice),(umberPlatePrice-glacAmalgamPrice)/640));
    clientMessage("§d--------------------------------");
  }
}
