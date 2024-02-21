package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.powers.CoffeePower;
import schedulemod.powers.SatietyPower;
import schedulemod.util.CardStats;

public class EmergencyCoffee extends BaseCard {
    public static final String ID = makeID(EmergencyCoffee.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;
    private static final int EXTRA_EVENTS = 1;
    private static final int UPGRADE_EXTRA_EVENTS = 1;

    public EmergencyCoffee() {
        super(ID, info);
        tags.add(Entropy.Enums.FOOD);
        setBlock(BLOCK, UPGRADE_BLOCK);
        setMagic(EXTRA_EVENTS, UPGRADE_EXTRA_EVENTS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new CoffeePower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new SatietyPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { return new EmergencyCoffee(); }
}
