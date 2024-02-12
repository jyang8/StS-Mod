package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class Pasta extends EventCard {
    public static final String ID = makeID(Pasta.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );
    private static final int BLOCK = 6;
    private static final int UPGRADE_BLOCK = 2;
    private static final int BLOCK_INCREASE = 6;
    private static final int UPGRADE_INCREASE = 2;

    public Pasta() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setBlock(BLOCK, UPGRADE_BLOCK);
        setMagic(BLOCK_INCREASE, UPGRADE_INCREASE);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { return new Pasta(); }
}
