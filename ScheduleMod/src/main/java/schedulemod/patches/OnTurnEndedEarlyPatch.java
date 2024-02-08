package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.interfaces.OnTurnEndedEarlyPower;

public class OnTurnEndedEarlyPatch {

    @SpirePatch(clz = GameActionManager.class, method = "callEndTurnEarlySequence")
    public static class OnTurnEndedEarlyGameActionManagerPatch {
        @SpirePrefixPatch
        public static void Prefix(GameActionManager __instance) {
          for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow instanceof OnTurnEndedEarlyPower) {
              ((OnTurnEndedEarlyPower)pow).onTurnEndedEarly();
            }
          }
        }
    }
}
