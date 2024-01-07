package schedulemod.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightFlareParticleEffect;
import schedulemod.BasicMod;
import schedulemod.orbs.ScheduleOrb;

public class ReduceRightMostScheduleAction extends AbstractGameAction {
    public ReduceRightMostScheduleAction() {}

    @Deprecated
    public ReduceRightMostScheduleAction(boolean unnecessary) {
        this();
    }

    public void update() {
        if (this.duration == this.startDuration && AbstractDungeon.player.orbs.size() > 0) {
            BasicMod.logger.info("ReduceRightMostScheduleAction firing.");
            AbstractOrb theOrb = null;
            for (AbstractOrb o : AbstractDungeon.player.orbs) {
                if (o instanceof ScheduleOrb) {
                    o.onStartOfTurn();
                    theOrb = o;
                    break;
                }
            }
            if (theOrb != null)
                for (int i = 0; i < 20; i++)
                    AbstractDungeon.effectsQueue.add(new LightFlareParticleEffect(theOrb.tX, theOrb.tY, Color.YELLOW));
        }
        tickDuration();
    }
}
