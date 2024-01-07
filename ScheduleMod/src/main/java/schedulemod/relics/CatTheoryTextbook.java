package schedulemod.relics;

import schedulemod.character.Entropy;

import static schedulemod.BasicMod.makeID;

public class CatTheoryTextbook extends BaseRelic {
    private static final String NAME = "CatTheoryTextbook";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.RARE;
    private static final LandingSound SOUND = LandingSound.HEAVY;


    public CatTheoryTextbook() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0]);
    }
}
