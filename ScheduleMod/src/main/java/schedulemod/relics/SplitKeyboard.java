package schedulemod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import schedulemod.character.Entropy;
import schedulemod.powers.PunctualPower;
import static schedulemod.BasicMod.makeID;

public class SplitKeyboard extends BaseRelic {
    private static final String NAME = "SplitKeyboard";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int PUNCTUAL = 1;
    private static final int THRESHOLD = 7;

    public SplitKeyboard() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], THRESHOLD, PUNCTUAL);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (this.counter != -1) {
            this.counter++;
            if (this.counter >= THRESHOLD) {
                this.counter = -1;
                flash();
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                        new PunctualPower(AbstractDungeon.player, AbstractDungeon.player, PUNCTUAL), PUNCTUAL));
            }
        }
    }
}
