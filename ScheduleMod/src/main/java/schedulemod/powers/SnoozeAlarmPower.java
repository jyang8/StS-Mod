package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class SnoozeAlarmPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SnoozeAlarm");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public SnoozeAlarmPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, -1);
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SnoozeAlarmPower(owner);
    }
}
