package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Optimize;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class AutomationSciencePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("AutomationScience");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static final int REQUIRED_DAMAGE = 18;
    private static final int SCHEDULE_SLOT = 2;

    public AutomationSciencePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount >= REQUIRED_DAMAGE) {
            addToBot(new ScheduleEventCard(new Optimize(this.amount), SCHEDULE_SLOT));
        }
    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + REQUIRED_DAMAGE + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AutomationSciencePower(this.owner, this.amount);
    }
}
