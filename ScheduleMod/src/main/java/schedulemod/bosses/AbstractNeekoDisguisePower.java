package schedulemod.bosses;

import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.powers.BasePower;

public abstract class AbstractNeekoDisguisePower extends BasePower {

    public AbstractNeekoDisguisePower(String id, PowerType powerType, boolean isTurnBased, AbstractMonster owner,
            int amount) {
        super(id, powerType, isTurnBased, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public abstract String getDialog();

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, NeekoViegoDisguisePower.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, NeekoLilliaDisguisePower.POWER_ID));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, NeekoBrandDisguisePower.POWER_ID));

        addToBot(new ApplyPowerAction(this.owner, this.owner, new NeekoPower(this.owner)));
        addToBot(new RollMoveAction((AbstractMonster) this.owner));
        addToBot(new CreateIntentAction((AbstractMonster) this.owner));
        addToBot(new TalkAction(this.owner, this.getDialog(), 0.5F, 3.0F));
        addToBot(new WaitAction(1F));
    }
}
