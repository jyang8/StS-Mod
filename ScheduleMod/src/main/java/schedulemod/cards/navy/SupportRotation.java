package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.SupportRotationAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class SupportRotation extends BaseCard {
    public static final String ID = makeID(SupportRotation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 1;
    private static final int UPGRADE_ATTACK_DAMAGE = 1;
    private static final int TIMES_ATTACKING = 3;
    private static final int FATIGUE = 3;

    public SupportRotation() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 1; i < TIMES_ATTACKING; i++)
            addToBot(new SupportRotationAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public AbstractCard makeCopy() { return new SupportRotation(); }
}
