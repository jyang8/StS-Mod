package schedulemod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import schedulemod.orbs.ScheduleOrb;

@SpirePatch(clz = AbstractPlayer.class, method = "render")
public class RenderSchedulePreviewInFrontPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer obj, SpriteBatch sb) {
        for (AbstractOrb o : obj.orbs) {
            if (o instanceof ScheduleOrb)
                ((ScheduleOrb)o).renderPreview(sb);
        }
    }
}
