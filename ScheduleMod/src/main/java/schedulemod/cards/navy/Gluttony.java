package schedulemod.cards.navy;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.GluttonyAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Gluttony extends BaseCard {
    public static final String ID = makeID(Gluttony.class.getSimpleName());

    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 14;
    private static final int UPGRADE_ATTACK_DAMAGE = 2;
    private static final int SATIETY_INCREASE = 2;
    private static final int UPGRADE_SATIETY_INCREASE = 1;
    private static final boolean EXHAUST = true;

    public Gluttony() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(SATIETY_INCREASE, UPGRADE_SATIETY_INCREASE);
        setExhaust(EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GluttonyAction((Entropy)p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new Gluttony(); }
}
