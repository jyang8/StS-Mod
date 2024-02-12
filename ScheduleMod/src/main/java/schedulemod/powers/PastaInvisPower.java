package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.PastaAction;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class PastaInvisPower extends BasePower
        implements CloneablePowerInterface, BetterOnApplyPowerPower, InvisiblePower {
    public static final String POWER_ID = makeID("PastaInvis");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PastaInvisPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, -1);
        updateDescription();
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof SleepPower && !target.hasPower(SleepPower.POWER_ID)) {
            addToBot(new PastaAction());
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source,
            int stackAmount) {
        return stackAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PastaInvisPower(owner, source);
    }
}
