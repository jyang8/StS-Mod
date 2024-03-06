package schedulemod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtBehavior;

public class ReturnToHandResetPatch {

  @SpirePatch(clz = UseCardAction.class, method = "update")
  public static class ReturnToHandActionPatch {

    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "exhaustOnUseOnce");
        return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
      }
    }

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(UseCardAction __instance, float ___duration, AbstractCard ___targetCard) {
      ___targetCard.returnToHand = false;
    }
  }
}
