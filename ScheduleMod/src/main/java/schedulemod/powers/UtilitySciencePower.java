package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.interfaces.OnCardCreatedPower;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class UtilitySciencePower extends BasePower implements CloneablePowerInterface, OnCardCreatedPower {
    public static final String POWER_ID = makeID("UtilityScience");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public UtilitySciencePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onCardCreated() {
        flash();
        addToBot(new ApplyPowerAction(this.owner, this.owner, new WrinklerPower(this.owner, this.amount), this.amount));
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new UtilitySciencePower(owner, amount);
    }
}
