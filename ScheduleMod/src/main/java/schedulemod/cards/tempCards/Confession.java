package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.CopyModifier;
import schedulemod.util.CardStats;

public class Confession extends EventCard {
    public static final String ID = makeID(Confession.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1);


    public Confession() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        CardModifierManager.addModifier(triggeringCard, new CopyModifier());
        if (upgraded)
            CardModifierManager.addModifier(triggeringCard, new CopyModifier());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Confession();
    }

}
