package schedulemod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import schedulemod.character.Entropy;

import static schedulemod.BasicMod.makeID;


public class KaraokeKables extends BaseRelic {
    private static final String NAME = "KaraokeKables";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.CLINK;

    public KaraokeKables() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    private static final int ADDITIONAL_DAMAGE = 2;

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], ADDITIONAL_DAMAGE);
    }

    @Override
    public void atTurnStart() {
        flash();
        addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, ADDITIONAL_DAMAGE), ADDITIONAL_DAMAGE));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
}
