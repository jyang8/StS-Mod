package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import schedulemod.orbs.ScheduleOrb;

public class RemoveFromScheduleAction extends AbstractGameAction {

    private int slot = -1;

    public RemoveFromScheduleAction(int slot) {
        this.slot = slot;
        this.actionType = ActionType.EXHAUST;
    }

    public void update() {
        if (!AbstractDungeon.player.hasOrb()) {
            this.isDone = true;
            return;
        }
        AbstractOrb o = AbstractDungeon.player.orbs.get(slot);
        if (o == null || !(o instanceof ScheduleOrb)) {
            this.isDone = true;
            return;
        }
        EmptyOrbSlot emptyOrb = new EmptyOrbSlot(o.cX, o.cY);
        AbstractDungeon.player.orbs.set(slot, emptyOrb);
        emptyOrb.setSlot(slot, AbstractDungeon.player.orbs.size());
        this.isDone = true;
    }
}
