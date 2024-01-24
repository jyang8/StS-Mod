package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.actions.DraftAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class Draft extends EventCard {
    public static final String ID = makeID(Draft.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            0
    );

    public Draft() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DraftAction());
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m, AbstractCard triggeringCard) {
        addToBot(new DraftAction());
    }

    @Override
    public AbstractCard makeCopy() { return new Draft(); }
}
