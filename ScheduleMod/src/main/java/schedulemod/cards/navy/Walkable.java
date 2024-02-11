package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.WalkHome;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Walkable extends BaseCard {
    public static final String ID = makeID(Walkable.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 7;
    private static final int UPGRADE_ATTACK_DAMAGE = 2;
    private static final int SCHEDULE_SLOT = 4;
    private static final int UPGRADE_SCHEDULE_SLOT = 5;

    public Walkable() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(SCHEDULE_SLOT);
        this.cardsToPreview = new WalkHome();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
        if (upgraded) {
            addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), UPGRADE_SCHEDULE_SLOT));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new Walkable(); }
}
