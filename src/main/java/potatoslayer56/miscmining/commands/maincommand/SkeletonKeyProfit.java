package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.commands.CustomCommand;

import static potatoslayer56.miscmining.MiscMining.*;

public class SkeletonKeyProfit implements CustomCommand {
  @Override
  public void execute() {
    float flawlessOnyxPrice = getInstaBuy("FLAWLESS_ONYX_GEM");
    float flawlessCitrinePrice = getInstaBuy("FLAWLESS_CITRINE_GEM");
    float flawlessPeridotPrice = getInstaBuy("FLAWLESS_PERIDOT_GEM");
    float flawlessAquamarinePrice = getInstaBuy("FLAWLESS_AQUAMARINE_GEM");
    float dwarvenMinisPrice = getInstaBuy("DWARVEN_OS_METALLIC_MINIS");
    float scrapPrice = getInstaBuy("SUSPICIOUS_SCRAP");
    float iceColdPrice = getInstaBuy("ENCHANTMENT_ICE_COLD_1");
    float blueGobPrice = getInstaBuy("GOBLIN_EGG_BLUE");
    float refUmberPrice = getInstaBuy("REFINED_UMBER");
    float refTungPrice = getInstaBuy("REFINED_TUNGSTEN");
    float glacAmalgamPrice = getInstaBuy("GLACITE_AMALGAMATION");
    float mithPlatePrice = getInstaBuy("MITHRIL_PLATE");
    float umberPlatePrice = getInstaBuy("UMBER_PLATE");
    float tungPlatePrice = getInstaBuy("TUNGSTEN_PLATE");
    float umberKeyPrice = getInstaBuy("UMBER_KEY");
    float tungKeyPrice = getInstaBuy("TUNGSTEN_KEY");
    float skeleKeyPrice = getInstaBuy("SKELETON_KEY");
    float scutePrice = getInstaBuy("FROZEN_SCUTE");
    float opalCrystalPrice = getInstaBuy("PERFECT_OPAL_GEM") - (5 * getInstaBuy("FLAWLESS_OPAL_GEM"));
    float onyxCrystalPrice = getInstaBuy("PERFECT_ONYX_GEM") - (5 * getInstaBuy("FLAWLESS_ONYX_GEM"));
    float peridotCrystalPrice = getInstaBuy("PERFECT_PERIDOT_GEM") - (5 * getInstaBuy("FLAWLESS_PERIDOT_GEM"));
    float citrineCrystalPrice = getInstaBuy("PERFECT_CITRINE_GEM") - (5 * getInstaBuy("FLAWLESS_CITRINE_GEM"));
    float aquamarineCrystalPrice = getInstaBuy("PERFECT_AQUAMARINE_GEM") - (5 * getInstaBuy("FLAWLESS_AQUAMARINE_GEM"));
    float expectedValue = 0;
    float expectedPityValue = 0;
    float expectedValueWithoutPity = 0;
    float[][] lootTable = {
      {flawlessOnyxPrice, 1, 0.275f, 60000},
      {flawlessOnyxPrice, 2, 0.1469f},
      {flawlessCitrinePrice, 1, 0.275f, 60000},
      {flawlessCitrinePrice, 2, 0.1469f},
      {flawlessPeridotPrice, 1, 0.275f, 60000},
      {flawlessPeridotPrice, 2, 0.1469f},
      {flawlessAquamarinePrice, 1, 0.275f, 60000},
      {flawlessAquamarinePrice, 2, 0.1469f},
      {dwarvenMinisPrice, 1, 0.2132f, 75000},
      {scrapPrice, 8, 0.275f},
      {scrapPrice, 16, 0.1469f},
      {iceColdPrice, 1, 0.275f, 60000},
      {blueGobPrice, 1, 0.275f, 60000},
      {blueGobPrice, 2, 0.1469f},
      {refUmberPrice, 1, 0.275f, 60000},
      {refUmberPrice, 2, 0.1469f},
      {refTungPrice, 1, 0.257f, 60000},
      {refTungPrice, 2, 0.1469f},
      {glacAmalgamPrice, 1, 0.275f, 60000},
      {glacAmalgamPrice, 2, 0.1469f},
      {glacAmalgamPrice, 4, 0.076f},
      {mithPlatePrice, 1, 0.112f, 150000},
      {umberPlatePrice, 1, 0.0574f, 300000},
      {tungPlatePrice, 1, 0.0574f, 300000},
      {umberKeyPrice, 1, 0.1469f, 100000},
      {umberKeyPrice, 2, 0.076f},
      {umberKeyPrice, 4, 0.0386f},
      {tungKeyPrice, 1, 0.1469f, 100000},
      {tungKeyPrice, 2, 0.076f},
      {tungKeyPrice, 4, 0.0386f},
      {skeleKeyPrice, 1, 0.0195f, 1250000},
      {scutePrice, 1, 0.0195f, 750000},
      {wispPrice, 1, 0.0195f, 1250000},
      {locketPrice, 1, 0.0098f, 2500000},
      {frostDyePrice, 1, 0.0001f, 5000000f},
      {opalCrystalPrice, 1, 0.2132f, 75000},
      {onyxCrystalPrice, 1, 0.2132f, 75000},
      {peridotCrystalPrice, 1, 0.2132f, 75000},
      {citrineCrystalPrice, 1, 0.2132f, 75000},
      {aquamarineCrystalPrice, 1, 0.2132f, 75000}
    };
    for (float[] item : lootTable) {
      float temp;
      expectedValueWithoutPity += item[0] * item[1] * item[2];
      if (item.length == 4){
        temp = item[0] * item[1] * (27500 / item[3]);
        if (temp >= expectedPityValue) {
          expectedPityValue = temp;
        }
      }
    }
    expectedValue = expectedPityValue + expectedValueWithoutPity;
    clientPrefixedMessage("§aExpected Skeleton Key Profit: §6%,.2f".formatted(expectedValue-skeleKeyPrice));
  }
}
