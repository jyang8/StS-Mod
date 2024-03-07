package schedulemod.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.SteamClub;
import schedulemod.patches.EventsPlayedPatch.EventsPlayedThisCombatField;

public class SteamClubAction extends AbstractGameAction {
    private int baseBlock = 0;
    private int multiplier = 1;
    private float startingDuration = 0.2F;

    public SteamClubAction(int baseBlock, int multiplier) {
        this.baseBlock = baseBlock;
        this.multiplier = multiplier;
        this.duration = startingDuration;
        this.actionType = ActionType.BLOCK;
    }
    @Override
    public void update() {
        /* 
        DEPRECATED
        if (duration == 0.2F) {
            ArrayList<EventCard> eventsPlayed = EventsPlayedThisCombatField.eventsPlayedThisCombat
                    .get(AbstractDungeon.actionManager);
            int tmp = 0;
            if (eventsPlayed.size() < 2) {
                tickDuration();
                this.isDone = true;
                return;
            }

            for (int i = eventsPlayed.size() - 2; i >= 0; i--) {
                if (eventsPlayed.get(i) instanceof SteamClub) {
                    break;
                } else {
                    tmp += multiplier;
                }
            }
            if (tmp + baseBlock > 0) {
                addToBot(new GainBlockAction(AbstractDungeon.player, baseBlock + tmp));
            }
            tickDuration();
            this.isDone = true;
    
        }
        */
    }
}
