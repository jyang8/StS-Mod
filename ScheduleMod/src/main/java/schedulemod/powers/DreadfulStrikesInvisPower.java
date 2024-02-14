package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.DreadfulStrikesAction;
import schedulemod.cards.tempCards.DreadfulStrikes;
import schedulemod.orbs.ScheduleOrb;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class DreadfulStrikesInvisPower extends BasePower
        implements CloneablePowerInterface, InvisiblePower {
    public static final String POWER_ID = makeID("DreadfulStrikesInvis");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public DreadfulStrikesInvisPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, -1);
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new DreadfulStrikesAction());

        for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
            AbstractOrb o = AbstractDungeon.player.orbs.get(i);
            if (o instanceof ScheduleOrb && ((ScheduleOrb) o).eventCard instanceof DreadfulStrikes) {
                return;
            }
        }
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, POWER_ID));
    }

    @Override
    public AbstractPower makeCopy() {
        return new DreadfulStrikesInvisPower(owner);
    }
}
