package schedulemod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import schedulemod.character.Entropy;

public class PlayerOnUseCardPatch {
    @SpirePatch(clz = UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = { AbstractCard.class,
            AbstractCreature.class })
    public static class PlayerOnUseCardPatchConstructor {
        @SpirePostfixPatch
        public static void Postfix(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
            if (!card.dontTriggerOnUseCard) {
                if (AbstractDungeon.player instanceof Entropy) {
                    Entropy entropy = (Entropy) AbstractDungeon.player;
                    entropy.onUseCard(card, __instance);
                }
            }
        }
    }
}
