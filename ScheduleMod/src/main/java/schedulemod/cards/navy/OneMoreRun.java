package schedulemod.cards.navy;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import schedulemod.character.Entropy;
import schedulemod.patches.EventsPlayedPatch.EventsPlayedThisTurnField;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class OneMoreRun extends BaseCard {
    public static final String ID = makeID(OneMoreRun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1);

    private static final int FATIGUE = 5;
    private static final int UPGRADE_FATIGUE = 2;

    public OneMoreRun() {
        super(ID, info);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int eventsTriggered = EventsPlayedThisTurnField.eventsPlayedThisTurn.get(AbstractDungeon.actionManager).size();
        addToBot(new ApplyPowerAction(m, p, new FatiguePower(m, p, this.magicNumber * eventsTriggered)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new OneMoreRun();
    }
}
