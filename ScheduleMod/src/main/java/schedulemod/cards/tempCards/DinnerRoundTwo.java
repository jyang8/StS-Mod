package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.BounceModifier;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class DinnerRoundTwo extends EventCard {
    public static final String ID = makeID(DinnerRoundTwo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1);

    private static final int SATIETY = 1;

    public DinnerRoundTwo() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        if (triggeringCard.type == CardType.POWER) {
            return;
        }
        CardModifierManager.addModifier(triggeringCard, new BounceModifier(this.upgraded));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, SATIETY), SATIETY));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DinnerRoundTwo();
    }

}
