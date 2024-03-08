package schedulemod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.SteamClub;
import schedulemod.character.Entropy;
import schedulemod.interfaces.OnEventScheduledRelic;
import schedulemod.orbs.ScheduleOrb;

import static schedulemod.BasicMod.makeID;
 
public class SteamDeck extends BaseRelic implements OnEventScheduledRelic {
    private static final String NAME = "SteamDeck";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.FLAT;
    private static final int AMOUNT = 1;


    public SteamDeck() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onEventScheduled(EventCard c, int slot, AbstractOrb replaced) {
        if (replaced instanceof ScheduleOrb) {
            flash();
            addToBot((AbstractGameAction)new MakeTempCardInHandAction(new SteamClub(), AMOUNT));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }


}
