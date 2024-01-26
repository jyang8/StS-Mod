package schedulemod.cards.navy;

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

    private static final int DAMAGE = 6;
    private static final int SCHEDULE_SLOTS = 3;
    private static final int UPGRADE_SCHEDULE_SLOTS = 1;

    public DND() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(SCHEDULE_SLOTS, UPGRADE_SCHEDULE_SLOTS);
        this.cardsToPreview = new DreadfulStrikes();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        for (int i = 1; i <= this.magicNumber; i++) {
            addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), i));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DND();
    }
}
