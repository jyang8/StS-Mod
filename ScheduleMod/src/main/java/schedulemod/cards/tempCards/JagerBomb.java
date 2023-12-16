package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.navy.BaseCard;
import schedulemod.util.CardStats;

public class JagerBomb extends BaseCard {
    public static final String ID = makeID(JagerBomb.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );
    private static final int ATTACK_DMG = 16;
    private static final boolean EXHAUST = true;
    private static final boolean RETAIN = true;

    public JagerBomb() {
        super(ID, info);
        setDamage(ATTACK_DMG);
        setExhaust(EXHAUST);
        setSelfRetain(RETAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public AbstractCard makeCopy() { return new JagerBomb(); }
}
