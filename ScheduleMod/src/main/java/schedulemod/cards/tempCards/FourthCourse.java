package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class FourthCourse extends EventCard {
    public static final String ID = makeID(FourthCourse.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0);

    private static final int FATIGUE = 6;
    private static final int WAVES = 2;
    private static final int UPGRADE_WAVES = 1;

    public FourthCourse() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        tags.add(Entropy.Enums.FATIGUE_EVENT);
        setExhaust(true);
        setMagic(FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        useEvent(p, m);
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < (upgraded ? WAVES : WAVES + UPGRADE_WAVES); i++) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(mo, p, new FatiguePower(mo, p, this.magicNumber)));
                }
            }
        }
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FourthCourse();
    }
}
