package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.PunctualPower;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class SixthCourse extends EventCard {
    public static final String ID = makeID(SixthCourse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);

    private static final int PUNCTUAL = 4;
    private static final int UPGRADE_PUNCTUAL = 2;

    public SixthCourse() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(PUNCTUAL, UPGRADE_PUNCTUAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PunctualPower(p, p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1), 1));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PunctualPower(p, p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SixthCourse();
    }
}
