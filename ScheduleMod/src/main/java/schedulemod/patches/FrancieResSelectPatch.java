package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.vfx.FloatyEffect;

import schedulemod.relics.FrancieRes;

public class FrancieResSelectPatch {

  @SpirePatch(clz = AbstractRelic.class, method = "bossObtainLogic")
  public static class FrancieResBossObtainLogicPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(AbstractRelic __instance, FloatyEffect ___f_effect) {
      if (__instance.relicId.equals(FrancieRes.ID)) {
        __instance.isObtained = true;
        ___f_effect.x = 0.0F;
        ___f_effect.y = 0.0F;
        return SpireReturn.Return();
      }
      return SpireReturn.Continue();
    }
  }

  @SpirePatch(clz = BossRelicSelectScreen.class, method = "relicObtainLogic")
  public static class FrancieResRelicObtainLogicPatch {
    @SpirePrefixPatch
    public static void Prefix(BossRelicSelectScreen __instance, AbstractRelic r) {
      if (r.relicId.equals(FrancieRes.ID)) {
        r.instantObtain(AbstractDungeon.player, 0, true);
        (AbstractDungeon.getCurrRoom()).rewardPopOutTimer = 0.25F;
      }
    }
  }
}
