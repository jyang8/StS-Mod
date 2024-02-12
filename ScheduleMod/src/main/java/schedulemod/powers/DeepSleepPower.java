package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.interfaces.OnTurnEndedEarlyPower;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class DeepSleepPower extends BasePower implements CloneablePowerInterface, OnTurnEndedEarlyPower {
    public static final String POWER_ID = makeID("DeepSleep");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public DeepSleepPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onTurnEndedEarly() {
        addToBot(new ApplyPowerAction(this.owner, this.owner, new WrinklerPower(this.owner, this.amount)));
    }

    @Override
    public AbstractPower makeCopy() {
        return new DeepSleepPower(owner, source, amount);
    }
}
