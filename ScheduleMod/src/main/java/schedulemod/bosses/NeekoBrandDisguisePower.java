package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NeekoBrandDisguisePower extends AbstractNeekoDisguisePower {
    public static final String POWER_ID = makeID(NeekoBrandDisguisePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public NeekoBrandDisguisePower(AbstractMonster owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }
}
