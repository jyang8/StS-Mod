package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class Cheesecake extends BaseCard {
    public static final String ID = makeID(Cheesecake.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int SATIETY = 1;
    private static final int STRENGTH_GAIN = 2;
    private static final int UPGRADE_STRENGTH_GAIN = 1;

    public Cheesecake() {
        super(ID, info);
        setMagic(STRENGTH_GAIN, UPGRADE_STRENGTH_GAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower(makeID("HokkaidoUniBuffet"))
                && (p.getPower(makeID("Satiety")).amount + SATIETY >= ((Entropy)p).MAX_SATIETY))
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, SATIETY)));
    }

    @Override
    public AbstractCard makeCopy() { return new Cheesecake(); }
}
