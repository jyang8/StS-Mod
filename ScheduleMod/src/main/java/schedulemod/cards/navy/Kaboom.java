package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.actions.RemoveFromScheduleAction;
import schedulemod.character.Entropy;
import schedulemod.orbs.ScheduleOrb;
import schedulemod.util.CardStats;

public class Kaboom extends BaseCard {
    public static final String ID = makeID(Kaboom.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            6);

    private static final int ATTACK_DAMAGE = 4;
    private static final int UPGRADE_ATTACK_DAMAGE = 1;
    private static final float DELAY = 0.2F;
    private static final int HITS = 8;
    private static final int UPGRADE_HITS = 1;

    private int currentCostReduction = 0;

    public Kaboom() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
        setMagic(HITS, UPGRADE_HITS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new WaitAction(DELAY));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new WaitAction(DELAY));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new WaitAction(DELAY));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.FIRE));
        addToBot(new WaitAction(DELAY));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new WaitAction(DELAY));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new WaitAction(DELAY));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new WaitAction(DELAY));
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (upgraded) {
            addToBot(new WaitAction(DELAY));
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SMASH));
        }

        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
            AbstractOrb o = AbstractDungeon.player.orbs.get(i);
            if (o instanceof ScheduleOrb) {
                addToBot(new RemoveFromScheduleAction(i));
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int eventCount = 0;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof ScheduleOrb) {
                eventCount++;
            }
        }
        setCostForTurn(costForTurn - (eventCount - currentCostReduction));
        currentCostReduction = eventCount;
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.currentCostReduction = 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Kaboom();
    }
}
