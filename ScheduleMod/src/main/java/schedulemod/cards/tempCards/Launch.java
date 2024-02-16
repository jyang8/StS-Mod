package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Launch extends EventCard {
    public static final String ID = makeID(Launch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1);

    private static final int ATTACK_DAMAGE = 30;
    private static final int UPGRADE_ATTACK_DAMAGE = 40;

    public Launch() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        this.damageType = DamageType.THORNS;
        this.damageTypeForTurn = damageType;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        useEvent(p, m);
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
                addToBot(new WaitAction(0.1F));
            }
        }
        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Launch();
    }
}
