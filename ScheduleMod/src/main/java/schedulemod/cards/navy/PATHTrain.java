package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Jersey;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class PATHTrain extends BaseCard {
    public static final String ID = makeID(PATHTrain.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int ATTACK_DAMAGE = 14;
    private static final int UPGRADE_ATTACK_DAMAGE = 4;
    private static final int SCHEDULE_SLOT = 5;

    public PATHTrain() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        this.cardsToPreview = new Jersey();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { return new PATHTrain(); }
}
