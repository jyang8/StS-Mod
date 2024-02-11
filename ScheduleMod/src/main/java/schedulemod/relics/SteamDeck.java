package schedulemod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.SteamClub;
import schedulemod.character.Entropy;
import static schedulemod.BasicMod.makeID;

public class SteamDeck extends BaseRelic {
    private static final String NAME = "SteamDeck";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.FLAT;
    private static final int SCHEDULE_SLOT = 7;


    public SteamDeck() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onShuffle() {
        flash();
        addToBot((AbstractGameAction)new ScheduleEventCard(new SteamClub(), SCHEDULE_SLOT));
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }


}
