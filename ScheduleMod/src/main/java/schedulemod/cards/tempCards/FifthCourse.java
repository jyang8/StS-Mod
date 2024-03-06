package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import basemod.helpers.CardModifierManager;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.modifiers.AmpModifier;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class FifthCourse extends EventCard {
    public static final String ID = makeID(FifthCourse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0);

    private static final int AMP = 7;
    private static final int UPGRADE_AMP = 3;

    public FifthCourse() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        tags.add(Entropy.Enums.AMP_EVENT);
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
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FifthCourse();
    }
}
