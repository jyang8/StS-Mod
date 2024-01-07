package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import schedulemod.orbs.ScheduleOrb;

public class ScheduleEvokeAction extends AbstractGameAction {
    private ScheduleOrb orb;

    public ScheduleEvokeAction(ScheduleOrb orb) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orb = orb;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        AbstractDungeon.player.orbs.remove(this.orb);
        AbstractDungeon.player.orbs.add(0, this.orb);
        AbstractDungeon.player.evokeOrb();
        this.isDone = true;
    }
}
