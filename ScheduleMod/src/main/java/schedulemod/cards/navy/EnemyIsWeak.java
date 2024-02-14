package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class EnemyIsWeak extends BaseCard {
    public static final String ID = makeID(EnemyIsWeak.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int WEAK = 4;
    private static final int UPGRADE_WEAK = 95;
    private static final boolean EXHAUST = true;


    public EnemyIsWeak() {
        super(ID, info);
        setMagic(WEAK, UPGRADE_WEAK);
        setExhaust(EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false)));
    }

    @Override
    public AbstractCard makeCopy() { return new EnemyIsWeak(); }
}
