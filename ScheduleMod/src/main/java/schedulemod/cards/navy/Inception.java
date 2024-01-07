package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Inception extends BaseCard {
    public static final String ID = makeID(Inception.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 9;
    private static final int UPGRADE_ATTACK_DAMAGE = 4;
    private static final int DEBUFF_AMOUNT = 3;
    private static final int UPGRADE_DEBUFF_AMOUNT = 1;

    public Inception() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(DEBUFF_AMOUNT, UPGRADE_DEBUFF_AMOUNT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        if (m.hasPower(makeID("Sleep"))) {
            addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false)));
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false)));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Inception(); }
}
