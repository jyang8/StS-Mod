package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import schedulemod.cards.tempCards.Drink;
import schedulemod.character.Entropy;
import schedulemod.powers.StackCupPower;
import schedulemod.util.CardStats;

public class StackCup extends BaseCard {
    public static final String ID = makeID(StackCup.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 7;
    private static final int WEAK = 1;

    public StackCup() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE);
        setMagic(WEAK);
        this.cardsToPreview = new Drink();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.POISON));
        if (upgraded) {
            addToBot(new ApplyPowerAction(m, p, new WeakPower(p, this.magicNumber, false)));
        }
        addToBot(new ApplyPowerAction(p, p, new StackCupPower(p, p)));
    }

    @Override
    public AbstractCard makeCopy() { return new StackCup(); }
}
