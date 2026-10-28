package potatoslayer56.miscmining.commands.maincommand;

import potatoslayer56.miscmining.commands.CustomCommand;

import static potatoslayer56.miscmining.MiscMining.*;

public class CorpseKeyProfit implements CustomCommand {
  @Override
  public void execute() {
    float fineOnyxPrice = getInstaBuy("FINE_ONYX_GEM");
    float fineCitrinePrice = getInstaBuy("FINE_CITRINE_GEM");
    float finePeridotPrice = getInstaBuy("FINE_PERIDOT_GEM");
    float fineAquamarinePrice = getInstaBuy("FINE_AQUAMARINE_GEM");
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
    float expectedTungUmberKeyValue = 0;
    float expectedTungUmberKeyPityValue = 0;
    float expectedTungUmberKeyValueWithoutPity = 0;
    float expectedSkeletonKeyValue = 0;
    float expectedSkeletonKeyPityValue = 0;
    float expectedSkeletonKeyValueWithoutPity = 0;
    float[][] pityTable = {
      {flawlessAquamarinePrice, 60000},
      {flawlessCitrinePrice, 60000},
      {flawlessOnyxPrice, 60000},
      {flawlessPeridotPrice, 60000},
      {refUmberPrice, 60000},
      {refTungPrice, 60000},
      {glacAmalgamPrice, 60000},
      {iceColdPrice, 60000},
      {blueGobPrice, 60000},
      {aquamarineCrystalPrice, 75000},
      {citrineCrystalPrice, 75000},
      {onyxCrystalPrice, 75000},
      {opalCrystalPrice, 75000},
      {peridotCrystalPrice, 75000},
      {dwarvenMinisPrice, 75000},
      {umberKeyPrice, 100000},
      {tungKeyPrice, 100000},
      {mithPlatePrice, 150000},
      {umberPlatePrice, 300000},
      {tungPlatePrice, 300000},
      {scutePrice, 750000},
      {skeleKeyPrice, 1250000},
      {wispPrice, 1250000},
      {locketPrice, 2500000},
      {frostDyePrice, 5000000},
    };
    float[][] tungUmberCorpseLootTable = {
      {fineOnyxPrice, 4, 0.4873f},
      {fineOnyxPrice, 8, 0.2772f},
      {fineOnyxPrice, 16, 0.1479f},
      {flawlessOnyxPrice, 1, 0.0224f},
      {fineCitrinePrice, 4, 0.4873f},
      {fineCitrinePrice, 8, 0.2772f},
      {fineCitrinePrice, 16, 0.1479f},
      {flawlessCitrinePrice, 1, 0.0224f},
      {finePeridotPrice, 4, 0.4873f},
      {finePeridotPrice, 8, 0.2772f},
      {finePeridotPrice, 16, 0.1479f},
      {flawlessPeridotPrice, 1, 0.0224f},
      {fineAquamarinePrice, 4, 0.4873f},
      {fineAquamarinePrice, 8, 0.2772f},
      {fineAquamarinePrice, 16, 0.1479f},
      {flawlessAquamarinePrice, 1, 0.0224f},
      {scrapPrice, 2, 0.4873f},
      {scrapPrice, 4, 0.2272f},
      {iceColdPrice, 1, 0.0701f},
      {blueGobPrice, 1, 0.0701f},
      {refUmberPrice, 1, 0.0179f},
      {refTungPrice, 1, 0.0179f},
      {glacAmalgamPrice, 1, 0.0179f},
      {umberPlatePrice, 1, 0.0045f},
      {tungPlatePrice, 1, 0.0045f},
      {umberKeyPrice, 1, 0.009f},
      {tungKeyPrice, 1, 0.009f},
      {mithPlatePrice, 1, 0.009f},
      {scutePrice, 1, 0.0036f},
      {dwarvenMinisPrice, 1, 0.0179f},
      {skeleKeyPrice, 1, 0.0036f},
      {wispPrice, 1, 0.0018f},
      {frostDyePrice, 1, 0.00001f},
      {opalCrystalPrice, 1, 0.0356f},
      {onyxCrystalPrice, 1, 0.0356f},
      {peridotCrystalPrice, 1, 0.0356f},
      {citrineCrystalPrice, 1, 0.0356f},
      {aquamarineCrystalPrice, 1, 0.0356f},
    };
    float[][] skeletonCorpseLootTable = {
      {flawlessOnyxPrice, 1, 0.275f},
      {flawlessOnyxPrice, 2, 0.1469f},
      {flawlessCitrinePrice, 1, 0.275f},
      {flawlessCitrinePrice, 2, 0.1469f},
      {flawlessPeridotPrice, 1, 0.275f},
      {flawlessPeridotPrice, 2, 0.1469f},
      {flawlessAquamarinePrice, 1, 0.275f},
      {flawlessAquamarinePrice, 2, 0.1469f},
      {dwarvenMinisPrice, 1, 0.2132f},
      {scrapPrice, 8, 0.275f},
      {scrapPrice, 16, 0.1469f},
      {iceColdPrice, 1, 0.275f},
      {blueGobPrice, 1, 0.275f},
      {blueGobPrice, 2, 0.1469f},
      {refUmberPrice, 1, 0.275f},
      {refUmberPrice, 2, 0.1469f},
      {refTungPrice, 1, 0.257f},
      {refTungPrice, 2, 0.1469f},
      {glacAmalgamPrice, 1, 0.275f},
      {glacAmalgamPrice, 2, 0.1469f},
      {glacAmalgamPrice, 4, 0.076f},
      {mithPlatePrice, 1, 0.112f},
      {umberPlatePrice, 1, 0.0574f},
      {tungPlatePrice, 1, 0.0574f},
      {umberKeyPrice, 1, 0.1469f},
      {umberKeyPrice, 2, 0.076f},
      {umberKeyPrice, 4, 0.0386f},
      {tungKeyPrice, 1, 0.1469f},
      {tungKeyPrice, 2, 0.076f},
      {tungKeyPrice, 4, 0.0386f},
      {skeleKeyPrice, 1, 0.0195f},
      {scutePrice, 1, 0.0195f},
      {wispPrice, 1, 0.0195f},
      {locketPrice, 1, 0.0098f},
      {frostDyePrice, 1, 0.0001f},
      {opalCrystalPrice, 1, 0.2132f},
      {onyxCrystalPrice, 1, 0.2132f},
      {peridotCrystalPrice, 1, 0.2132f},
      {citrineCrystalPrice, 1, 0.2132f},
      {aquamarineCrystalPrice, 1, 0.2132f}
    };
    for (float[] item : tungUmberCorpseLootTable) {
      float temp;
      expectedTungUmberKeyValueWithoutPity += item[0] * item[1] * item[2];
      for (float[] pity : pityTable) {
        temp = pity[0] * (2750 / pity[1]);
        if (temp > expectedTungUmberKeyPityValue) expectedTungUmberKeyPityValue = temp;
      }
    }
    for (float[] item : skeletonCorpseLootTable) {
      float temp;
      expectedSkeletonKeyValueWithoutPity += item[0] * item[1] * item[2];
      for (float[] pity : pityTable) {
        temp = pity[0] * (27500 / pity[1]);
        if (temp > expectedSkeletonKeyPityValue) expectedSkeletonKeyPityValue = temp;
      }
    }
    expectedTungUmberKeyValue = expectedTungUmberKeyPityValue + expectedTungUmberKeyValueWithoutPity;
    expectedSkeletonKeyValue = expectedSkeletonKeyPityValue + expectedSkeletonKeyValueWithoutPity;
    clientPrefixedMessage("§aExpected Tungsten Key Profit: §6%,.2f".formatted(expectedTungUmberKeyValue-tungKeyPrice));
    clientPrefixedMessage("§aExpected Umber Key Profit: §6%,.2f".formatted(expectedTungUmberKeyValue-umberKeyPrice));
    clientPrefixedMessage("§aExpected Skeleton Key Profit: §6%,.2f".formatted(expectedSkeletonKeyValue-skeleKeyPrice));
  }
}
