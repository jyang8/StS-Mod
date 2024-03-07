package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.MakeTempCardAtBottomOfDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.actions.utility.ChooseOneColorless;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.interfaces.OnCardCreatedPower;

public class OnCardCreatedPatch {

  protected static void triggerPowers() {
    for (AbstractPower pow : AbstractDungeon.player.powers) {
      if (pow instanceof OnCardCreatedPower) {
        ((OnCardCreatedPower) pow).onCardCreated();
      }
    }
  }

  @SpirePatch(clz = MakeTempCardInDrawPileAction.class, method = "update")
  public static class OnCardCreatedDrawPilePatch {

    @SpirePrefixPatch
    public static void Prefix(MakeTempCardInDrawPileAction __instance, float ___duration, float ___startDuration,
        int ___amount) {
      if (___duration == ___startDuration) {
        for (int i = 0; i < ___amount; i++) {
          triggerPowers();
        }
      }
    }
  }

  @SpirePatch(clz = MakeTempCardInDiscardAction.class, method = "update")
  public static class OnCardCreatedDiscardPilePatch {

    @SpirePrefixPatch
    public static void Prefix(MakeTempCardInDiscardAction __instance, float ___duration, float ___startDuration,
        int ___amount) {
      if (___duration == ___startDuration) {
        for (int i = 0; i < ___amount; i++) {
          triggerPowers();
        }
      }
    }
  }

  @SpirePatch(clz = MakeTempCardAtBottomOfDeckAction.class, method = "update")
  public static class OnCardCreatedBottomOfDeckPatch {

    @SpirePrefixPatch
    public static void Prefix(MakeTempCardAtBottomOfDeckAction __instance, float ___duration, float ___startDuration,
        int ___amount) {
      if (___duration == ___startDuration) {
        for (int i = 0; i < ___amount; i++) {
          triggerPowers();
        }
      }
    }

    @SpirePatch(clz = MakeTempCardInHandAction.class, method = "update")
    public static class OnCardCreatedHandPatch {

      @SpirePrefixPatch
      public static void Prefix(MakeTempCardInHandAction __instance, int ___amount) {
        for (int i = 0; i < ___amount; i++) {
          triggerPowers();
        }
      }
    }

    @SpirePatch(clz = ChooseOneColorless.class, method = "update")
    public static class OnCardCreatedChooseOneColorlessPatch {

      @SpirePrefixPatch
      public static void Prefix(ChooseOneColorless __instance, boolean ___retrieveCard) {
        if (___retrieveCard) {
          triggerPowers();
        }
      }
    }

    @SpirePatch(clz = DiscoveryAction.class, method = "update")
    public static class OnCardCreatedDiscoveryPatch {

      @SpirePrefixPatch
      public static void Prefix(DiscoveryAction __instance, float ___duration) {
        if (___duration == Settings.ACTION_DUR_FAST) {
          triggerPowers();
        }
      }
    }
  }
}
