package schedulemod.relics;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.Lunch;
import schedulemod.character.Entropy;
import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SoothrRes extends BaseRelic {
    private static final String NAME = "SoothrRes";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int SCHEDULE_SLOT = 3;

    private EventCard eventCard;

    public SoothrRes() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
        eventCard = new Lunch();
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        addToBot(new RelicAboveCreatureAction(p, this));
        addToBot(new ScheduleEventCard(eventCard, SCHEDULE_SLOT));
    }
}
