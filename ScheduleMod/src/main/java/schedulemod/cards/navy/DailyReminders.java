package schedulemod.cards.navy;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.patches.EventsPlayedPatch.EventsPlayedThisTurnField;
import schedulemod.util.CardStats;

public class DailyReminders extends BaseCard {
    public static final String ID = makeID(DailyReminders.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int ATTACK_DAMAGE = 7;
    private static final int UPGRADE_ATTACK_DAMAGE = 3;
    private static final int SCHEDULE_SLOT = 7;

    public DailyReminders() {
        super(ID, info);
        setDamage(ATTACK_DAMAGE, UPGRADE_ATTACK_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        ArrayList<EventCard> eventsPlayed = EventsPlayedThisTurnField.eventsPlayedThisTurn.get(AbstractDungeon.actionManager);
        ArrayList<EventCard> eventsToSchedule = new ArrayList<EventCard>();
        if (eventsPlayed.size() > 0) {
            for (EventCard event : eventsPlayed) {
                if (event.triggeringCard == this) {
                    eventsToSchedule.add(event);
                    event.triggeringCard = null;
                }
            }
            
            int offset = eventsToSchedule.size() - 1;
            for (EventCard event : eventsToSchedule) {
                addToBot(new ScheduleEventCard(event.makeStatEquivalentCopy(), SCHEDULE_SLOT - offset));    
                offset--;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() { return new DailyReminders(); }
}
