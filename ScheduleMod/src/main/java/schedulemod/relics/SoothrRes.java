package schedulemod.relics;

import schedulemod.character.Entropy;

import static schedulemod.BasicMod.makeID;

public class SoothrRes extends BaseRelic {
    private static final String NAME = "SoothrRes";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.STARTER;
    private static final LandingSound SOUND = LandingSound.CLINK;

    private static final int STRENGTH = 10;

    public SoothrRes() {
        super(ID, NAME, Entropy.Enums.CARD_COLOR, RARITY, SOUND);
    }

}
