package schedulemod.cards.navy;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.BakahamAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Bakaham extends BaseCard {
    public static final String ID = makeID(Bakaham.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            0
    );

    private static final int ATTACK_DAMAGE = 4;
    private static final int UPGRADE_ATTACK_DAMAGE = 3;
    private static final int NUM_CARDS_DRAW = 1;

    public Bakaham() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new BakahamAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), NUM_CARDS_DRAW));
    }

    @Override
    public AbstractCard makeCopy() { return new Bakaham(); }
}
