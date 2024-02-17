package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class TidyUpPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("TidyUp");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public TidyUpPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot((AbstractGameAction) new ApplyPowerAction(this.owner, this.owner,
                new PunctualPower(this.owner, this.owner, this.amount), this.amount));
        addToBot((AbstractGameAction) new ApplyPowerAction(this.owner, this.owner,
        new LosePunctualPower(this.owner, this.amount), this.amount));
        addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new TidyUpPower(owner, amount);
    }
}
