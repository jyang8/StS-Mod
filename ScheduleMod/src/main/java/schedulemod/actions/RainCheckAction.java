package schedulemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import schedulemod.character.Entropy;
import schedulemod.orbs.ScheduleOrb;

public class RainCheckAction extends AbstractGameAction {

    private static final float STARTING_DURATION = 0.2F;

    public RainCheckAction() {
        duration = STARTING_DURATION;
    }

    @Override
    public void update() {
        if (duration == STARTING_DURATION) {
            AbstractPlayer p = AbstractDungeon.player;
            if (!(p instanceof Entropy)) {
                tickDuration();
                isDone = true;
                return;
            }
            ArrayList<ScheduleOrb> schedOrbs = new ArrayList<ScheduleOrb>();
            for (AbstractOrb orb : p.orbs) {
                if (orb instanceof ScheduleOrb) {
                    schedOrbs.add((ScheduleOrb) orb);
                }
            }
            if (schedOrbs.size() == 0) {
                tickDuration();
                isDone = true;
                return;
            }
            int slot = p.orbs.size() - 1;
            for (int i = schedOrbs.size() - 1; i >= 0; i--) {
                p.orbs.set(slot, schedOrbs.get(i));
                schedOrbs.get(i).setSlot(slot, p.orbs.size());
                slot--;
            }
            while (slot >= 0) {
                AbstractOrb empty = new EmptyOrbSlot();
                p.orbs.set(slot, empty);
                empty.setSlot(slot, p.orbs.size());
                slot--;
            }
            tickDuration();
            isDone = true;
        }
    }
}
