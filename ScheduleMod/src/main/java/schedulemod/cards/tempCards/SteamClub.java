package schedulemod.cards.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.SteamClubAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class SteamClub extends EventCard {
    public static final String ID = makeID(SteamClub.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1);

    private static final int BLOCK_MULTIPLIER = 2;
    private static final int UPGRADE_BLOCK_MULTIPLIER = 1;

    public SteamClub() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setMagic(BLOCK_MULTIPLIER, UPGRADE_BLOCK_MULTIPLIER);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SteamClubAction(this.magicNumber));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SteamClubAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SteamClub();
    }

}
