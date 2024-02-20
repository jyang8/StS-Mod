package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.SupportRotationAction;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class SupportRotation extends BaseCard {
    public static final String ID = makeID(SupportRotation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int ATTACK_DAMAGE = 1;
    private static final int UPGRADE_ATTACK_DAMAGE = 1;
    private static final int TIMES_ATTACKING = 3;
    private static final int FATIGUE = 3;
    private static final int UPGRADE_FATIGUE = 1;

    public SupportRotation() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < TIMES_ATTACKING; i++)
            addToBot(new SupportRotationAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new SupportRotation(); }
}
