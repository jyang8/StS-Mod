package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import schedulemod.bosses.BossBen;
import schedulemod.character.Entropy;

public class BossPatch {

    // TODO change to Beyond
    @SpirePatch(clz = Exordium.class, method = "initializeBoss")
    public static class ExordiumInitializeBossPatch {

        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(Exordium obj) {
            if (AbstractDungeon.player instanceof Entropy &&
                    (!UnlockTracker.isBossSeen(BossBen.ID))) {
                Exordium.bossList.clear();
                Exordium.bossList.add(BossBen.ID);
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();

            }
        }
    }

}