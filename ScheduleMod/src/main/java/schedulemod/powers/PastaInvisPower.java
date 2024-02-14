package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.PastaAction;
import schedulemod.cards.tempCards.Pasta;
import schedulemod.orbs.ScheduleOrb;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class PastaInvisPower extends BasePower
        implements CloneablePowerInterface, BetterOnApplyPowerPower, InvisiblePower {
    public static final String POWER_ID = makeID("PastaInvis");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PastaInvisPower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, -1);
        updateDescription();
    }

     @Override
    public void atEndOfTurn(boolean isPlayer) {
        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
            AbstractOrb o = AbstractDungeon.player.orbs.get(i);
            if (o instanceof ScheduleOrb && ((ScheduleOrb) o).eventCard instanceof Pasta) {
                return;
            }
        }
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, POWER_ID));
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof SleepPower && !target.hasPower(SleepPower.POWER_ID)) {
            addToBot(new PastaAction());
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source,
            int stackAmount) {
        return stackAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new PastaInvisPower(owner, source);
    }
}
