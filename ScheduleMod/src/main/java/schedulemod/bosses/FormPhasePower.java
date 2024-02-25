package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.powers.BasePower;

public class FormPhasePower extends BasePower {
    public static final String POWER_ID = makeID(FormPhasePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public FormPhasePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 99;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > this.amount)
            damageAmount = this.amount;
        this.amount -= damageAmount;
        if (this.amount <= 0) {
            this.amount = 0;
            addToBot((AbstractGameAction) new RollMoveAction((AbstractMonster) this.owner));
        }
        updateDescription();
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        if (this.amount <= 0) {
            this.description = DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }
}
