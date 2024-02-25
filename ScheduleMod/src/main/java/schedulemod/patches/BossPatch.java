package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import schedulemod.bosses.BossBen;
import schedulemod.character.Entropy;

public class BossPatch {

    @SpirePatch(clz = TheBeyond.class, method = "initializeBoss")
    public static class TheBeyondInitializeBossPatch {

        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(TheBeyond obj) {
            // If Boss Ben has not been seen, force it to appear
            if (AbstractDungeon.player instanceof Entropy &&
                    (!UnlockTracker.isBossSeen(BossBen.ID))) {
                TheBeyond.bossList.clear();
                TheBeyond.bossList.add(BossBen.ID);
                return SpireReturn.Return();
            } else {
                return SpireReturn.Continue();

            }
        }
    }

}