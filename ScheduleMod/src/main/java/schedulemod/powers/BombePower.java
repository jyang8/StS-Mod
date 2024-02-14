package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.Bomb;
import schedulemod.orbs.ScheduleOrb;

import static schedulemod.BasicMod.makeID;

public class BombePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Bombe");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static final int SCHEDULE_SLOT = 3;
    private static final int[] SLOTS_TO_CHECK = { 1, 3 };
    private boolean upgraded = false;
    private EventCard eventToSchedule;

    public BombePower(AbstractCreature owner, boolean upgraded) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);
        eventToSchedule = new Bomb();
        if (upgraded) {
            this.upgraded = upgraded;
            eventToSchedule.upgrade();
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT &&
                !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            int tmp = 0;
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof ScheduleOrb) {
                    int slot = ((ScheduleOrb) orb).slot;
                    if (slot == SLOTS_TO_CHECK[0] || slot == SLOTS_TO_CHECK[1]) {
                        tmp++;
                    }
                }
            }
            if (tmp == 1) {
                addToBot(new ScheduleEventCard(eventToSchedule, SCHEDULE_SLOT));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new BombePower(owner, upgraded);
    }
}
