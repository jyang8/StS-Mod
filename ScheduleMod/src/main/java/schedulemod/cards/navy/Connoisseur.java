package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import schedulemod.character.Entropy;
import schedulemod.powers.MaxSatietyPower;
import schedulemod.util.CardStats;

public class Connoisseur extends BaseCard {
    public static final String ID = makeID(Connoisseur.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int STAT_GAIN = 2;
    private static final int UPGRADE_STAT_GAIN = 1;
    private static final int MAX_SATIETY_LOSS = 2;

    public Connoisseur() {
        super(ID, info);
        setMagic(STAT_GAIN, UPGRADE_STAT_GAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new MaxSatietyPower(p, -MAX_SATIETY_LOSS)));
    }

    @Override
    public AbstractCard makeCopy() { return new Connoisseur(); }
}
