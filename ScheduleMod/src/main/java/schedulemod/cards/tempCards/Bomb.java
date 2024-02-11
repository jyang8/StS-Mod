package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Bomb extends EventCard {
    public static final String ID = makeID(Bomb.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            2);

    private static final int ATTACK_DAMAGE = 20;
    private static final int CARDS_TO_DRAW = 1;

    public Bomb() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setDamage(ATTACK_DAMAGE);
        setMagic(CARDS_TO_DRAW);
        this.damageType = DamageType.THORNS;
        this.damageTypeForTurn = DamageType.THORNS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.LIGHTNING));
        if (upgraded) {
            addToBot(new DrawCardAction(this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bomb();
    }
}
