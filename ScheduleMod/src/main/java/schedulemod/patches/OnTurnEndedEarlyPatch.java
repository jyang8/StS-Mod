package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import schedulemod.interfaces.OnTurnEndedEarlyPower;

public class OnTurnEndedEarlyPatch {

  
  @SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
  public static class PrevTurnEndedEarlyField {
    public static SpireField<Boolean> endedEarly = new SpireField<>(()-> false);
  }

  @SpirePatch(clz = GameActionManager.class, method = SpirePatch.CLASS)
  public static class PrevTurnEndedEarlyShouldResetField {
    public static SpireField<Boolean> resetEndedEarly = new SpireField<>(()-> true);
  }

  @SpirePatch(clz = AbstractPlayer.class, method = "applyPreCombatLogic")
  public static class PreCombatEndedEarlyPatch {
      @SpirePrefixPatch
      public static void Prefix(AbstractPlayer __instance) {
        PrevTurnEndedEarlyField.endedEarly.set(AbstractDungeon.actionManager, false);
        PrevTurnEndedEarlyShouldResetField.resetEndedEarly.set(AbstractDungeon.actionManager, true);
      }
  }

  @SpirePatch(clz = GameActionManager.class, method = "endTurn")
    public static class OnTurnEndedGameActionManagerPatch {
        @SpirePrefixPatch
        public static void Prefix(GameActionManager __instance) {
          if (PrevTurnEndedEarlyShouldResetField.resetEndedEarly.get(__instance)) {
            PrevTurnEndedEarlyField.endedEarly.set(__instance, false);
          }
          PrevTurnEndedEarlyShouldResetField.resetEndedEarly.set(AbstractDungeon.actionManager, true);
        }
    }


    @SpirePatch(clz = GameActionManager.class, method = "callEndTurnEarlySequence")
    public static class OnTurnEndedEarlyGameActionManagerPatch {
        @SpirePrefixPatch
        public static void Prefix(GameActionManager __instance) {
          PrevTurnEndedEarlyField.endedEarly.set(__instance, true);
          PrevTurnEndedEarlyShouldResetField.resetEndedEarly.set(AbstractDungeon.actionManager, false);
          for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow instanceof OnTurnEndedEarlyPower) {
              ((OnTurnEndedEarlyPower)pow).onTurnEndedEarly();
            }
          }
        }
    }
}
