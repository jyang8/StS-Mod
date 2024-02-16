package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Launch;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class RocketFactoryPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("RocketFactory");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static final int WRINKLER_TRIGGERS_REQUIRED = 3;
    private static final int SCHEDULE_SLOT = 1;
    private int remainingTriggers = WRINKLER_TRIGGERS_REQUIRED;

    public RocketFactoryPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, -1);
        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        this.remainingTriggers--;
        if (this.remainingTriggers <= 0) {
            this.remainingTriggers = WRINKLER_TRIGGERS_REQUIRED;
            addToBot(new ScheduleEventCard(new Launch(), SCHEDULE_SLOT));
        }
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.remainingTriggers + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new RocketFactoryPower(owner);
    }
}
