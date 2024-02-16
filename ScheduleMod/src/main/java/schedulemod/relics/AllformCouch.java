package schedulemod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;

import static schedulemod.BasicMod.makeID;

public class AllformCouch extends BaseRelic implements OnApplyPowerRelic {
    private static final String NAME = "AllformCouch";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.COMMON;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    private static final int ADDITIONAL_FATIGUE = 1;

    public AllformCouch() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], ADDITIONAL_FATIGUE);
    }

    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature abstractCreature, AbstractCreature abstractCreature1) {
        if (power instanceof FatiguePower) {
            power.amount++;
        }
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if (power instanceof FatiguePower && stackAmount > 0)
            return stackAmount + 1;
        return stackAmount;
    }
}
