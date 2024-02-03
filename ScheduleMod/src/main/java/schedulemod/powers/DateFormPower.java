package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import schedulemod.actions.PunctualPerEventAction;

import static schedulemod.BasicMod.makeID;

public class DateFormPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("DateForm");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DateFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        flashWithoutSound();
        addToBot(new PunctualPerEventAction(amount));
    }

    @Override
    public AbstractPower makeCopy() { return new DateFormPower(owner, amount); }
}
