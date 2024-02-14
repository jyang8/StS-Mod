package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.JerseyPower;
import schedulemod.util.CardStats;

public class Jersey extends EventCard {
    public static final String ID = makeID(Jersey.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1);

    private static final int STACK_COUNT = 2;
    private static final int UPGRADE_STACK_COUNT = 1;

    public Jersey() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(STACK_COUNT, UPGRADE_STACK_COUNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new JerseyPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new JerseyPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Jersey();
    }

}
