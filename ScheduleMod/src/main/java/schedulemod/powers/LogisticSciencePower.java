package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.LogisticScienceAction;
import schedulemod.cards.EventCard;
import schedulemod.interfaces.OnEventScheduledPower;
import schedulemod.orbs.ScheduleOrb;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static schedulemod.BasicMod.makeID;

public class LogisticSciencePower extends BasePower implements CloneablePowerInterface, OnEventScheduledPower {
    public static final String POWER_ID = makeID("LogisticScience");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public LogisticSciencePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    @Override
    public void onEventScheduled(EventCard card, int slot, AbstractOrb replaced) {
        if (replaced instanceof ScheduleOrb) {
            flashWithoutSound();
            addToBot(new LogisticScienceAction(this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LogisticSciencePower(this.owner, this.amount);
    }
}
