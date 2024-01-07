package schedulemod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import schedulemod.character.Entropy;

@SpirePatch(clz = CardGlowBorder.class, method = "<ctor>", paramtypez = {AbstractCard.class})
public class ScheduleGlowPatch {
    @SpirePostfixPatch
    public static void Postfix(CardGlowBorder obj, AbstractCard card) {
        if (card.hasTag(Entropy.Enums.SCHEDULE_GLOW))
            ReflectionHacks.setPrivate(obj, AbstractGameEffect.class, "color", new Color(Color.GOLDENROD));
    }
}
