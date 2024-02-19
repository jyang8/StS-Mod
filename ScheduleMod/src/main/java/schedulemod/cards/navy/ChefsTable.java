package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.LoseGoldAction;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.TastingMenu;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class ChefsTable extends BaseCard {
    public static final String ID = makeID(ChefsTable.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    private static final int DAMAGE = 6;
    private static final int GOLD_LOSS = 30;
    private static final int UPGRADE_GOLD_LOSS = -10;
    private static final int SCHEDULE_SLOT = 6;

    public ChefsTable() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(GOLD_LOSS, UPGRADE_GOLD_LOSS);
        this.cardsToPreview = new TastingMenu();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), SCHEDULE_SLOT));
        addToBot(new LoseGoldAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.cardsToPreview.upgrade();
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { return new ChefsTable(); }
}
