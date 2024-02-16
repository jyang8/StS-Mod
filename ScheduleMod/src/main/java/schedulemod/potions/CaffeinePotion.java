package schedulemod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class CaffeinePotion extends BasePotion {

    public CaffeinePotion(String id, int potency, PotionRarity rarity, PotionSize shape, Color liquidColor, Color hybridColor, Color spotsColor) {
        super(id, potency, rarity, shape, liquidColor, hybridColor, spotsColor);
    }

    public int getPotency(int ascension) {
        return 0;
    }

    public void use(AbstractCreature target) {
        
    }

    public String getDescription() {
        return "";
    }
}