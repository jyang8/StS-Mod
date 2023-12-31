package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class WrinklerPower extends BasePower implements CloneablePowerInterface, OnReceivePowerPower {
    public static final String POWER_ID = makeID("Wrinkler");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;

    public WrinklerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onSpecificTrigger() {
        if (this.amount <= 0) {
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            addToTop(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (abstractPower instanceof BasePower && abstractPower.type == PowerType.DEBUFF) {
            onSpecificTrigger();
            return false;
        }
        AbstractPlayer p = (AbstractPlayer) this.owner;
        if (p.hasRelic(makeID("CatTheoryTextbook")) && abstractPower.type == PowerType.DEBUFF) {
            onSpecificTrigger();
            return false;
        }
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power instanceof BasePower && power.type == PowerType.DEBUFF) {
            onSpecificTrigger();
            return 0;
        }
        AbstractPlayer p = (AbstractPlayer) this.owner;
        if (p.hasRelic(makeID("CatTheoryTextbook")) && power.type == PowerType.DEBUFF) {
            onSpecificTrigger();
            return 0;
        }
        return stackAmount;
    }

    @Override
    public AbstractPower makeCopy() { return new WrinklerPower(owner, amount); }
}
