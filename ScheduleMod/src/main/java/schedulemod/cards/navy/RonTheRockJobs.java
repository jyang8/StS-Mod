package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.WrinklerPower;
import schedulemod.util.CardStats;

public class RonTheRockJobs extends BaseCard {
    public static final String ID = makeID(RonTheRockJobs.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 9;
    private static final int UPGRADE_ATTACK_DAMAGE = 3;
    private static final int WRINKLER = 1;
    private static final int UPGRADE_WRINKLER = 1;

    public RonTheRockJobs() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(WRINKLER, UPGRADE_WRINKLER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new WrinklerPower(p, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { return new RonTheRockJobs(); }
}
