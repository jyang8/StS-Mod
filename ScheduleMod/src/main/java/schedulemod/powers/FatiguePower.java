package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.actions.CheckFatigueAction;
import schedulemod.interfaces.AfterMonsterCalculateDamagePower;

import static schedulemod.BasicMod.makeID;

public class FatiguePower extends BasePower implements CloneablePowerInterface, AfterMonsterCalculateDamagePower {
    public static final String POWER_ID = makeID("Fatigue");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public FatiguePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;

        if (!(this.owner instanceof AbstractMonster)) {
            return;
        }
        if (!((AbstractMonster) this.owner).hasPower(SleepPower.POWER_ID)) {
            addToBot(new CheckFatigueAction((AbstractMonster) this.owner, this.source));
        }
    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if (this.amount < 0) {
            this.type = PowerType.BUFF;
        }
    }

    @Override
    public void onInitialApplication() {
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));

        if (!(this.owner instanceof AbstractMonster)) {
            return;
        }
        if (!((AbstractMonster) this.owner).hasPower(SleepPower.POWER_ID)) {
            addToBot(new CheckFatigueAction((AbstractMonster) this.owner, this.source));
        }
    }

    @Override
    public void afterMonsterCalculateDamage() {
        if (!(this.owner instanceof AbstractMonster)) {
            return;
        }
        if (!((AbstractMonster) this.owner).hasPower(SleepPower.POWER_ID)) {
            addToBot(new CheckFatigueAction((AbstractMonster) this.owner, this.source));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FatiguePower(owner, source, amount);
    }
}
