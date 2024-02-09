package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.DrowsyPower;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class Lunch extends EventCard {
    public static final String ID = makeID(Lunch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            1);

    private static final int ATTACK_DAMAGE = 8;
    private static final int UPGRADE_ATTACK_DAMAGE = 2;
    private static final int DROWSY = 1;
    private static final int UPGRADE_DROWSY = 1;

    public Lunch() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(DROWSY, UPGRADE_DROWSY);
        this.damageType = DamageType.THORNS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, p, new DrowsyPower(m, p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, p, new DrowsyPower(m, p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Lunch();
    }
}
