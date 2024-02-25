package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NeekoLilliaDisguisePower extends AbstractNeekoDisguisePower {
    public static final String POWER_ID = makeID(NeekoLilliaDisguisePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public NeekoLilliaDisguisePower(AbstractMonster owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public String getDialog() {
        return BossBen.DIALOG[2];
    }

}