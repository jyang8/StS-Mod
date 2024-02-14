package schedulemod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import schedulemod.cards.EventCard;

public class EventsPlayedPatch {

  @SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
  public static class EventsPlayedThisTurnField {
    public static SpireField<ArrayList<EventCard>> eventsPlayedThisTurn = new SpireField<>(
        () -> new ArrayList<EventCard>());
  }

  @SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
  public static class EventsPlayedThisCombatField {
    public static SpireField<ArrayList<EventCard>> eventsPlayedThisCombat = new SpireField<>(
        () -> new ArrayList<EventCard>());
  }

  @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
  public static class ClearEventsPlayedThisTurnPatch {
    private static class Locator extends SpireInsertLocator {
      public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
        Matcher finalMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "damageReceivedThisTurn");
        return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
      }
    }

    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(GameActionManager __instance) {
      EventsPlayedThisTurnField.eventsPlayedThisTurn.get(__instance).clear();
    }
  }

  @SpirePatch(clz = GameActionManager.class, method = "clear")
  public static class ClearEventsPlayedThisCombatPatch {
    @SpirePostfixPatch
    public static void PostFix(GameActionManager __instance) {
      EventsPlayedThisCombatField.eventsPlayedThisCombat.get(__instance).clear();
    }
  }
}
