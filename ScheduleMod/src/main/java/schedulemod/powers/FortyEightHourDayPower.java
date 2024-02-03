package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.cards.EventCard;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class FortyEightHourDayPower extends BasePower implements CloneablePowerInterface, EventPowerInterface {
    public static final String POWER_ID = makeID("FortyEightHourDay");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public FortyEightHourDayPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() { this.description = DESCRIPTIONS[0]; }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            addToBot(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    @Override
    public void modifyEvent(EventCard e, AbstractCard c) {
        flash();
    }

    @Override
    public AbstractPower makeCopy() {
        return new FortyEightHourDayPower(owner, amount);
    }
}
