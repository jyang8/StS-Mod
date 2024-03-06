package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static schedulemod.BasicMod.makeID;

public class SweetDreamsPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("SweetDreams");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public SweetDreamsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof SleepPower && target instanceof AbstractMonster) {
            addToBot(new ApplyPowerAction(owner, owner, new NextTurnBlockPower(owner, amount), amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new SweetDreamsPower(owner, amount);
    }
}
