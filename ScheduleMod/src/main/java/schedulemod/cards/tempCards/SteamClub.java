package schedulemod.cards.tempCards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import schedulemod.actions.SteamClubAction;
import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.patches.EventsPlayedPatch.EventsPlayedThisCombatField;
import schedulemod.util.CardStats;

public class SteamClub extends EventCard {
    public static final String ID = makeID(SteamClub.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1);

    private static final int BLOCK_MULTIPLIER = 3;
    private static final int UPGRADE_BLOCK_MULTIPLIER = 1;
    private static final int BASE_BLOCK = 0;

    public SteamClub() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        setExhaust(true);
        setBlock(BASE_BLOCK);
        setMagic(BLOCK_MULTIPLIER, UPGRADE_BLOCK_MULTIPLIER);
    } 

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SteamClubAction(this.block, this.magicNumber));
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SteamClubAction(this.block, this.magicNumber));
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
        ArrayList<EventCard> eventsPlayed = EventsPlayedThisCombatField.eventsPlayedThisCombat
                    .get(AbstractDungeon.actionManager);
            int tmp = 0;
            if (eventsPlayed.size() < 2) {
                return;
            }

            for (int i = eventsPlayed.size() - 2; i >= 0; i--) {
                if (eventsPlayed.get(i) instanceof SteamClub) {
                    break;
                } else {
                    tmp += magicNumber;
                }
            }
            if (tmp + block > 0) {
                this.rawDescription = cardStrings.DESCRIPTION + " (" + (tmp + block) + ")";
            }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SteamClub();
    }

}
