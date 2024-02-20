package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class SuppressiveFire extends BaseCard {
    public static final String ID = makeID(SuppressiveFire.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int ATTACK_DAMAGE = 2;
    private static final int TIMES_ATTACK = 6;
    private static final boolean ETHEREAL = true;
    private static final boolean UPGRADE_ETHEREAL = false;

    public SuppressiveFire() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE);
        setMagic(TIMES_ATTACK);
        setEthereal(ETHEREAL, UPGRADE_ETHEREAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < TIMES_ATTACK; i++)
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public AbstractCard makeCopy() { return new SuppressiveFire(); }
}
