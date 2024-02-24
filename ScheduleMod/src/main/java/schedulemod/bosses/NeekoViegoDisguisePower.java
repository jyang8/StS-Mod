package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NeekoViegoDisguisePower extends AbstractNeekoDisguisePower {
    public static final String POWER_ID = makeID(NeekoViegoDisguisePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public NeekoViegoDisguisePower(AbstractMonster owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

}
