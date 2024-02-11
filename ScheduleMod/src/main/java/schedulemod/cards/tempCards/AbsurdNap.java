package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.AbsurdNapAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class AbsurdNap extends EventCard {
    public static final String ID = makeID(AbsurdNap.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1);

    private static final int FATIGUE_MULTIPLIER = 2;
    private static final int UPGRADE_FATIGUE_MULTIPLIER = 1;

    public AbsurdNap() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(FATIGUE_MULTIPLIER, UPGRADE_FATIGUE_MULTIPLIER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbsurdNapAction(this.magicNumber));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbsurdNapAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AbsurdNap();
    }

}
