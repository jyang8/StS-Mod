package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import schedulemod.powers.BasePower;

public class AblazePower extends BasePower {
    public static final String POWER_ID = makeID(AblazePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public AblazePower(AbstractCreature owner, AbstractCreature source) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
