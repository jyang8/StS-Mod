package schedulemod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import schedulemod.character.Entropy;

public class PlayerOnUseCardPatch {

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class EntropyGameActionManagerPatch {
        private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
          Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "cardsPlayedThisTurn");
          return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
      }
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(GameActionManager __instance) {
            if (AbstractDungeon.player instanceof Entropy) {
                Entropy entropy = (Entropy) AbstractDungeon.player;
                entropy.onPlayCard(__instance.cardQueue.get(0).card, __instance.cardQueue.get(0).monster);
            }
        }
    }
}
