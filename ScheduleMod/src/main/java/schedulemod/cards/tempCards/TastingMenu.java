package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class TastingMenu extends EventCard {
    public static final String ID = makeID(TastingMenu.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 2;

    public TastingMenu() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setDamage(DAMAGE, UPGRADE_DAMAGE);
        this.damageType = DamageType.THORNS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Entropy e = (Entropy) p;
        setMagic(e.getSatietyGainedThisCombat());
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SMASH));
        }
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        Entropy e = (Entropy) p;
        setMagic(e.getSatietyGainedThisCombat());
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SMASH));
        }
    }

    @Override
    public AbstractCard makeCopy() { return new TastingMenu(); }
}
