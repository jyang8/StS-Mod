package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.character.Entropy;
import schedulemod.powers.BasePower;

public class ViegoPower extends BasePower {

    public static final String POWER_ID = makeID(ViegoPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ViegoPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
    }

    @Override
    public void atStartOfTurn() {
        AbstractPlayer player = AbstractDungeon.player;
        int amount;
        if (player instanceof Entropy) {
            amount = 0;
            Entropy entropy = (Entropy) player;
            for (AbstractOrb orb : entropy.orbs) {
                if (!(orb instanceof EmptyOrbSlot)) {
                    amount++;
                }
            }
        } else {
            amount = 1;
        }
        addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, amount)));

    }

}
