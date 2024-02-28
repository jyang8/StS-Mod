package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.DailyRemindersAction;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class DailyReminders extends BaseCard {
    public static final String ID = makeID(DailyReminders.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 7;
    private static final int UPGRADE_ATTACK_DAMAGE = 3;

    public DailyReminders() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        addToBot(new DailyRemindersAction(this));
    }

    @Override
    public AbstractCard makeCopy() { return new DailyReminders(); }
}
