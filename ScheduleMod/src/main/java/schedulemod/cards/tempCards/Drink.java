package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import basemod.helpers.CardModifierManager;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.AmpModifier;
import schedulemod.orbs.ScheduleOrb;
import schedulemod.util.CardStats;

public class Drink extends EventCard {
    public static final String ID = makeID(Drink.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            2);

    private static final int AMP = 8;
    private static final int UPGRADE_AMP = 4;

    public Drink() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        tags.add(Entropy.Enums.AMP_EVENT);
        setExhaust(true);
        setMagic(AMP, UPGRADE_AMP);
    }

    @Override
    public void onSchedule(AbstractOrb replaced) {
        if (replaced != null && replaced instanceof ScheduleOrb && ((ScheduleOrb)replaced).eventCard instanceof Drink) {
            this.baseMagicNumber += ((ScheduleOrb)replaced).eventCard.baseMagicNumber;
        }
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
    }

    @Override
    public AbstractCard makeCopy() {
        return new Drink();
    }

}
