package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.interfaces.OnRefreshHandPower;
import schedulemod.interfaces.OnTurnEndedEarlyPower;

public class OnRefreshHandPatch {

    @SpirePatch(clz = CardGroup.class, method = "refreshHandLayout")
    public static class OnRefreshHandCardGroupPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance) {
          for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow instanceof OnRefreshHandPower) {
              ((OnRefreshHandPower)pow).onRefreshHand();
            }
          }
        }
    }
}
