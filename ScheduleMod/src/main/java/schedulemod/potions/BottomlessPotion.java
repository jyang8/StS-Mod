package schedulemod.potions;

import static schedulemod.BasicMod.makeID;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import schedulemod.powers.MaxSatietyPower;

public class BottomlessPotion extends BasePotion {

    private static final int POTENCY = 2;

    public static final String POTION_ID = makeID("Bottomless");

    public BottomlessPotion() {
        super(POTION_ID, POTENCY, AbstractPotion.PotionRarity.UNCOMMON, AbstractPotion.PotionSize.SPHERE, Color.PURPLE, Color.VIOLET, Color.CLEAR);
    }

    public int getPotency() {
        return POTENCY;
    }

    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(p, p, new MaxSatietyPower(p, potency), potency));
        }
    }

    public String getDescription() {
        return this.potionStrings.DESCRIPTIONS[0] + getPotency() + this.potionStrings.DESCRIPTIONS[1];
    }
}