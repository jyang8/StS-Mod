package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class Arcade extends EventCard {
    public static final String ID = makeID(Arcade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    private final static int DEXTERITY = 2;
    private final static int UPGRADE_DEXTERITY = 1;

    public Arcade() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(DEXTERITY, UPGRADE_DEXTERITY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(SatietyPower.POWER_ID)) {
            int tmp = p.getPower(SatietyPower.POWER_ID).amount * this.magicNumber;
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, tmp), tmp));
            addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, tmp), tmp));
        }
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(SatietyPower.POWER_ID)) {
            int tmp = p.getPower(SatietyPower.POWER_ID).amount * this.magicNumber;
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, tmp), tmp));
            addToBot(new ApplyPowerAction(p, p, new LoseDexterityPower(p, tmp), tmp));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Arcade(); }
}
