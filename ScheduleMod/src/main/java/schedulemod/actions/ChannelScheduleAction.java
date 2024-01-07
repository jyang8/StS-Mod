package schedulemod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import schedulemod.character.Entropy;
import schedulemod.orbs.ScheduleOrb;

public class ChannelScheduleAction extends AbstractGameAction {
    private AbstractOrb orbType;
    private boolean autoEvoke = false;

    public ChannelScheduleAction(AbstractOrb newOrbType) {
        this(newOrbType, true);
    }

    public ChannelScheduleAction(AbstractOrb newOrbType, boolean autoEvoke) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.autoEvoke) {
                AbstractDungeon.player.channelOrb(this.orbType);
            } else {
                for (AbstractOrb o : AbstractDungeon.player.orbs) {
                    if (o instanceof ScheduleOrb)
                        ((ScheduleOrb)o).slot--;
                    ((Entropy)AbstractDungeon.player).channelOrb(o);
                }
            }
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        tickDuration();
    }
}
