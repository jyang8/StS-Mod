package schedulemod.bosses;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import schedulemod.character.Entropy;
import schedulemod.powers.BasePower;

public class SpeedPower extends BasePower {
    public static final String POWER_ID = makeID(SpeedPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private boolean justApplied = true;

    public SpeedPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.updateDescription();
        this.canGoNegative = true;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_FOCUS", 0.05F);
    }

    @Override
    public void updateDescription() {
        String maybePlural = DESCRIPTIONS[3];
        if (this.amount > 0) {
            if (amount == 1) {
                maybePlural = DESCRIPTIONS[4];
            }
            this.description = DESCRIPTIONS[0] + (this.amount * 5) + DESCRIPTIONS[1] + this.amount + maybePlural;
            this.type = AbstractPower.PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            if (tmp == 1) {
                maybePlural = DESCRIPTIONS[4];
            }
            this.description = DESCRIPTIONS[0] + (tmp * 5) + DESCRIPTIONS[2] + tmp + maybePlural;
            this.type = AbstractPower.PowerType.DEBUFF;
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -20)
            this.amount = -20;
    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0)
            addToTop((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -20)
            this.amount = -20;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            float multiplier = (1 + 0.05F * amount);
            if (multiplier < 0.5F) {
                multiplier = 0.5F;
            }
            damage *= multiplier;
        }
        return damage;
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            addToBot((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else if (this.amount > 0) {
            addToBot((AbstractGameAction) new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        } else {
            addToBot((AbstractGameAction) new ApplyPowerAction(this.owner, this.owner,
                    new SpeedPower(this.owner, this.owner, 1),
                    1));
        }
    }
}
