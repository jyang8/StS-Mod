package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class SnoozeAlarmPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SnoozeAlarm");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private boolean upgraded;

    public SnoozeAlarmPower(AbstractCreature owner, AbstractCreature source, int amount, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.upgraded = upgraded;
        updateDescription();
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
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if (this.upgraded)
            this.description += DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (this.upgraded && power instanceof SleepPower && target instanceof AbstractMonster) {
            addToBot(new ApplyPowerAction(target, source, new DrowsyPower(target, source, this.amount)));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new SnoozeAlarmPower(owner, source, amount, upgraded);
    }
}
