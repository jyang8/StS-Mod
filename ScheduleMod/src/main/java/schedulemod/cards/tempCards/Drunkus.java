package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.patches.FatigueDamageTypePatch;
import schedulemod.util.CardStats;

public class Drunkus extends EventCard {
    public static final String ID = makeID(Drunkus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);


    public Drunkus() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        if (triggeringCard.type != CardType.ATTACK) {
            return;
        }
        triggeringCard.damageTypeForTurn = FatigueDamageTypePatch.FATIGUE;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Drunkus();
    }

}
