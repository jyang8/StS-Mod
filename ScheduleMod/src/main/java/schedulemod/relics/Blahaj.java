package schedulemod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import schedulemod.character.Entropy;
import schedulemod.powers.SleepPower;

import static schedulemod.BasicMod.makeID;

public class Blahaj extends BaseRelic implements OnApplyPowerRelic {
    private static final String NAME = "Blahaj";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.MAGICAL;


    public Blahaj() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }


    @Override
    public boolean onApplyPower(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        if (abstractPower instanceof SleepPower && target instanceof AbstractMonster) {
            flash();
            addToBot(new GainEnergyAction(1));
        }
        return true;
    }

    @Override
    public int onApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        return stackAmount;
    }
}
