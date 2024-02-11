package schedulemod.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import schedulemod.character.Entropy;
import schedulemod.powers.SatietyPower;

import static schedulemod.BasicMod.makeID;

public class KitchenBlowtorch extends BaseRelic implements OnReceivePowerRelic {
    private static final String NAME = "KitchenBlowtorch";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.SHOP;
    private static final LandingSound SOUND = LandingSound.MAGICAL;

    private static final int STRENGTH = 1;

    public KitchenBlowtorch() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], STRENGTH);
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature source) {
        if (power instanceof SatietyPower) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(source, STRENGTH), STRENGTH));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(source, STRENGTH), STRENGTH));
        }
        return true;
    }
}
