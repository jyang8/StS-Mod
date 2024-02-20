package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.UnderstandPower;
import schedulemod.util.CardStats;

public class Understand extends BaseCard {
    public static final String ID = makeID(Understand.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 11;
    private static final int UPGRADE_ATTACK_DAMAGE = 4;
    private static final int PROC_DAMAGE = 1;
    private static final int UPGRADE_PROC_DAMAGE = 1;

    public Understand() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(PROC_DAMAGE, UPGRADE_PROC_DAMAGE);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new ApplyPowerAction(m, p, new UnderstandPower(m, p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new Understand(); }
}
