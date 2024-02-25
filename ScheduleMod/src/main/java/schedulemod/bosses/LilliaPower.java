package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.powers.BasePower;
import schedulemod.powers.SleepPower;

public class LilliaPower extends BasePower implements OnReceivePowerPower {
    public static final String POWER_ID = makeID(LilliaPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean skipFirst = true;

    public LilliaPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == this.owner && power instanceof SleepPower) {
            addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 3)));
        }
        return true;
    }

    @Override
    public void atEndOfRound() {
        if (!this.skipFirst) {
            flash();
            addToBot(new MakeTempCardInDiscardAction(new Dazed(), 1));
        } else {
            this.skipFirst = false;
        }
    }

}
