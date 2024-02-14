package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.AmpModifier;
import schedulemod.powers.DreadfulStrikesInvisPower;
import schedulemod.util.CardStats;

public class DreadfulStrikes extends EventCard {
    public static final String ID = makeID(DreadfulStrikes.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1);

    private static final int AMP = 4;

    public DreadfulStrikes() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        tags.add(Entropy.Enums.AMP_EVENT);
        setExhaust(true);
        setMagic(AMP);
    }
    
    @Override
    public void onSchedule() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new DreadfulStrikesInvisPower(p)));
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
        return new DreadfulStrikes();
    }

}
