package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.powers.BasePower;
import schedulemod.powers.SatietyPower;

public class BrandPower extends BasePower {
    public static final String POWER_ID = makeID(BrandPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private boolean skipFirst = true;
    public boolean pillarOfFlame;

    public BrandPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
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

    @Override
    public void atEndOfRound() {
        if (!this.skipFirst) {
            AbstractPlayer player = AbstractDungeon.player;
            if (player.hasPower(SatietyPower.POWER_ID)) {
                int amount = player.getPower(SatietyPower.POWER_ID).amount;
                flash();
                addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, amount),
                        amount));
            }
        } else {
            this.skipFirst = false;
        }
    }

}
