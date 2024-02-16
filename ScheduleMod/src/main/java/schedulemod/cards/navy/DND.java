package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.DreadfulStrikes;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class DND extends BaseCard {
    public static final String ID = makeID(DND.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1);

    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int SCHEDULE_SLOT = 7;

    public DND() {
        super(ID, info);
        setDamage(DAMAGE, UPGRADE_DAMAGE);
        setMagic(SCHEDULE_SLOT);
        this.cardsToPreview = new DreadfulStrikes();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.cardsToPreview.upgrade();
        }
        super.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AttackEffect.SLASH_HORIZONTAL, true));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DND();
    }
}
