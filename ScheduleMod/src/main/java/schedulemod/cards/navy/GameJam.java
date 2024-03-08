package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.BurnOut;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class GameJam extends BaseCard {
    public static final String ID = makeID(GameJam.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int ATTACK_DAMAGE = 7;
    private static final int UPGRADE_ATTACK_DAMAGE = 3;
    private static final int SCHEDULE_SLOT = 2;
    private static final int UPGRADE_SCHEDULE_SLOT = 1;
    private static final int ENERGY_GAIN = 1;

    public GameJam() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(SCHEDULE_SLOT, UPGRADE_SCHEDULE_SLOT);
        this.cardsToPreview = new BurnOut();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new GainEnergyAction(ENERGY_GAIN));
        addToBot(new ScheduleEventCard(this.cardsToPreview.makeStatEquivalentCopy(), this.magicNumber));
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { return new GameJam(); }
}
