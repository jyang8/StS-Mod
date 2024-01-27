package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.AmpModifier;
import schedulemod.util.CardStats;

public class Amp extends EventCard {
    public static final String ID = makeID(Amp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);

    private static final int AMP = 4;
    private static final int UPGRADE_AMP = 2;

    public Amp() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(AMP, UPGRADE_AMP);
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
        return new Amp();
    }

}
