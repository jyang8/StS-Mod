package schedulemod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import schedulemod.character.Entropy;

import static schedulemod.BasicMod.makeID;

@SpirePatch(clz = EmptyOrbSlot.class, method = "updateDescription")
public class EmptyOrbSlotPatch {
    public static Texture NORMAL_ORB = ImageMaster.ORB_SLOT_1;

    public static final OrbStrings normalOrbString = CardCrawlGame.languagePack.getOrbString("Empty");

    public static final OrbStrings scheduleOrbString = CardCrawlGame.languagePack.getOrbString(makeID("EmptyScheduleSlot"));

    public static void Postfix(EmptyOrbSlot EmptyOrbSlot_instance) {
        OrbStrings orbString;
        ImageMaster.ORB_SLOT_1 = NORMAL_ORB;
        if (AbstractDungeon.player instanceof Entropy) {
            orbString = scheduleOrbString;
        } else {
            orbString = normalOrbString;
        }
        EmptyOrbSlot_instance.name = orbString.NAME;
        EmptyOrbSlot_instance.description = orbString.DESCRIPTION[0];
    }
}
