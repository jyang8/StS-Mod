package schedulemod.relics;

import schedulemod.character.Entropy;
import schedulemod.powers.LoseMaxSatietyPower;
import schedulemod.powers.MaxSatietyPower;

import static schedulemod.BasicMod.makeID;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HungryCamera extends BaseRelic {
    private static final String NAME = "HungryCamera";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.UNCOMMON;
    private static final LandingSound SOUND = LandingSound.CLINK;
    private static final int MAX_SATIETY_INCREASE = 2;
    
    public boolean triggered = false;


    public HungryCamera() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        if (triggered) {
            triggered = false;
            AbstractPlayer p = AbstractDungeon.player;
            flash();
            addToBot(new ApplyPowerAction(p, p, new MaxSatietyPower(p, MAX_SATIETY_INCREASE)));
            addToBot(new ApplyPowerAction(p, p, new LoseMaxSatietyPower(p, MAX_SATIETY_INCREASE)));
        }
    }

    @Override
    public void atBattleStart() {
        triggered = false;
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }
}
