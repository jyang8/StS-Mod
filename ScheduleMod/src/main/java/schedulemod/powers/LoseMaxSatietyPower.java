package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class LoseMaxSatietyPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("LoseMaxSatiety");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public LoseMaxSatietyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    // TODO: (Entropy)owner.decreaseMaxSatiety(amount, false)

    @Override
    public AbstractPower makeCopy() { return new LoseMaxSatietyPower(owner, amount); }
}
