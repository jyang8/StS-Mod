package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.MultiplyDamageModifier;
import schedulemod.patches.FatigueDamageTypePatch;
import schedulemod.util.CardStats;

public class Drunkus extends EventCard {
    public static final String ID = makeID(Drunkus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1);

    private static final int MULTIPLY = 2;
    private static final int UPGRADE_MULTIPLY = 1;

    public Drunkus() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(MULTIPLY, UPGRADE_MULTIPLY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        if (triggeringCard.type != CardType.ATTACK) {
            return;
        }
        triggeringCard.damageTypeForTurn = FatigueDamageTypePatch.FATIGUE;
        CardModifierManager.addModifier(triggeringCard, new MultiplyDamageModifier(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Drunkus();
    }

}
