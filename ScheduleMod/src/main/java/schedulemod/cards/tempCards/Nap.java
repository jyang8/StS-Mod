package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.navy.BaseCard;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class Nap extends BaseCard {
    public static final String ID = makeID(Nap.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    private static final int FATIGUE = 5;
    private static final int UPGRADE_FATIGUE = 2;

    public Nap() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        this.freeToPlayOnce = true;
        //TODO: fix later
        setExhaust(true);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Nap(); }

}
