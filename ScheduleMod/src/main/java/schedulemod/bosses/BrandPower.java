package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.powers.BasePower;
import schedulemod.powers.SatietyPower;

public class BrandPower extends BasePower implements OnReceivePowerPower {
    public static final String POWER_ID = makeID(BrandPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public boolean pillarOfFlame;

    public BrandPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        // TODO fix this
        if (target == AbstractDungeon.player && power instanceof SatietyPower) {
            addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, 1)));
        }
        return true;
    }

    @Override
    public float atDamageGive(float damage, DamageType type) {
        if (owner instanceof BossBen) {
            BossBen bossBen = (BossBen) owner;
            if (AbstractDungeon.player.hasPower(AblazePower.POWER_ID) && bossBen.lastMove(BossBen.PILLAR_OF_FLAME)) {
                damage = damage * 1.5F;
            }
        }
        return damage;
    }

}
