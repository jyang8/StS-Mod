package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.cards.EventCard;
import schedulemod.interfaces.OnEventScheduledPower;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class CalendarTetrisPower extends BasePower implements CloneablePowerInterface, OnEventScheduledPower {
    public static final String POWER_ID = makeID("CalendarTetris");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public CalendarTetrisPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + (this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
    }

    @Override
    public void onEventScheduled(EventCard card, int slot) {
        addToBot(new DrawCardAction(this.amount));
    }

    @Override
    public AbstractPower makeCopy() {
        return new CalendarTetrisPower(owner, amount);
    }
}
