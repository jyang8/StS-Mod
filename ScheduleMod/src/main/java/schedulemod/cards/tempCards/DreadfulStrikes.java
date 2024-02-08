package schedulemod.cards.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import schedulemod.actions.DreadfulStrikesAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.AmpModifier;
import schedulemod.patches.EventsPlayedPatch;
import schedulemod.util.CardStats;

public class DreadfulStrikes extends EventCard {
    public static final String ID = makeID(DreadfulStrikes.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);

    private static final int AMP = 2;
    private static final int UPGRADE_AMP = 2;
    private static final int AMP_INCREASE = 2;

    public DreadfulStrikes() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        ArrayList<EventCard> events = EventsPlayedPatch.EventsPlayedThisCombatField.eventsPlayedThisCombat.get(AbstractDungeon.actionManager);
        int count = 0;
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i) instanceof DreadfulStrikes) {
                count++;
            }
        }
        setMagic(AMP + (count * AMP_INCREASE), UPGRADE_AMP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        if (triggeringCard.type != CardType.ATTACK) {
            return;
        }
        CardModifierManager.addModifier(triggeringCard, new AmpModifier(this.magicNumber));
        addToBot((AbstractGameAction) new DreadfulStrikesAction(AMP_INCREASE));

    }

    @Override
    public AbstractCard makeCopy() {
        return new DreadfulStrikes();
    }

}
