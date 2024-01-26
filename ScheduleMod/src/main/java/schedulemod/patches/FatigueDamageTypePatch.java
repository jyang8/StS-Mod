package schedulemod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import schedulemod.powers.FatiguePower;

public class FatigueDamageTypePatch {

  @SpireEnum
  public static DamageInfo.DamageType FATIGUE;

  @SpirePatch(clz = AbstractCreature.class, method = "decrementBlock")
  public static class FatigueDTBlockPatch {

    public static SpireReturn<Integer> Prefix(AbstractCreature __instance, DamageInfo info, int damageAmount) {
      if (info.type == FatigueDamageTypePatch.FATIGUE) {
        return SpireReturn.Return(damageAmount);
      }
      return SpireReturn.Continue();
    }
  }



    @SpirePatch(clz = AbstractMonster.class, method = "damage")
    public static class FatigueDTDamagePatch {

      private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
          Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "currentHealth");
          int[] matches = LineFinder.findAllInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
          return new int[]{matches[2]};
        }
      }

      @SpireInsertPatch(locator = Locator.class, localvars={"damageAmount"})
      public static SpireReturn<Void> Insert(AbstractMonster obj, DamageInfo info, int damageAmount) {
        if (info.type == FatigueDamageTypePatch.FATIGUE) {
          AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(obj, info.owner, new FatiguePower(obj, info.owner, damageAmount)));
          return SpireReturn.Return();
        }
        return SpireReturn.Continue();
      }
    }

    @SpirePatch(clz = PlatedArmorPower.class, method = "wasHPLost")
    public static class FatigueDTWasHPLostPatch {

      @SpirePrefixPatch
      public static SpireReturn<Void> Prefix(PlatedArmorPower obj, DamageInfo info, int damageAmount) {
        if (info.type == FatigueDamageTypePatch.FATIGUE) {
          return SpireReturn.Return();
        }
        return SpireReturn.Continue();
      }
    }
}
