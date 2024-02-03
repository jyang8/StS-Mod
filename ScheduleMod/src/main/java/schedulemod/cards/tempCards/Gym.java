package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class Gym extends EventCard {
    public static final String ID = makeID(Gym.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private final static int DEXTERITY = 2;
    private final static int UPGRADE_DEXTERITY = 3;

    public Gym() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(DEXTERITY, UPGRADE_DEXTERITY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(SatietyPower.POWER_ID)) {
            int tmp = p.getPower(SatietyPower.POWER_ID).amount * this.magicNumber;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, tmp), tmp));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, tmp), tmp));
        }
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(SatietyPower.POWER_ID)) {
            int tmp = p.getPower(SatietyPower.POWER_ID).amount * this.magicNumber;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, tmp), tmp));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, tmp), tmp));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Gym(); }
}
