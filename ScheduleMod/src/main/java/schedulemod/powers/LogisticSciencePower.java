package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.LogisticScienceAction;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static schedulemod.BasicMod.makeID;

public class LogisticSciencePower extends BasePower implements CloneablePowerInterface {
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
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target instanceof AbstractPlayer && power instanceof PunctualPower && power.amount > 0) {
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
