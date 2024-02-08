package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.KaboomAction;
import schedulemod.cards.tempCards.Cleaning;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Kaboom extends BaseCard {
    public static final String ID = makeID(Kaboom.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );

    private static final int ATTACK_DAMAGE = 20;
    private static final int UPGRADE_ATTACK_DAMAGE = 4;
    private static final int SCHEDULE_SLOT = 1;

    public Kaboom() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(SCHEDULE_SLOT);
        this.cardsToPreview = new Cleaning();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new KaboomAction((Cleaning)this.cardsToPreview));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            this.cardsToPreview.upgrade();
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeCopy() { return new Kaboom(); }
}
