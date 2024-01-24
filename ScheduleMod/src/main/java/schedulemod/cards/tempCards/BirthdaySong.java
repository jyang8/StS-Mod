package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.BirthdayAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class BirthdaySong extends EventCard {
    public static final String ID = makeID(BirthdaySong.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 15;
    private static final int DAMAGE_INCREASE = 5;
    private static final int UPGRADE_DAMAGE_INCREASE = 2;

    public BirthdaySong() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setDamage(DAMAGE);
        setMagic(DAMAGE_INCREASE, UPGRADE_DAMAGE_INCREASE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new BirthdayAction(this, this.magicNumber));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m, AbstractCard triggeringCard) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new BirthdayAction(this, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { return new BirthdaySong(); }
}
