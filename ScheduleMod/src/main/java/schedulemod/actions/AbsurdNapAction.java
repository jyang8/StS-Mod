package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.powers.FatiguePower;

public class AbsurdNapAction extends AbstractGameAction {
    private int multiplier = 1;
    private float startingDuration = 0.2F;

    public AbsurdNapAction(int multiplier) {
        this.multiplier = multiplier;
        this.duration = startingDuration;
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        if (duration == 0.2F) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m.hasPower(FatiguePower.POWER_ID)) {
                    int tmp = m.getPower(FatiguePower.POWER_ID).amount * (multiplier - 1);
                    addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new FatiguePower(m, AbstractDungeon.player, tmp), tmp));
                }
            }
            tickDuration();
            this.isDone = true;
        }
    }
}
