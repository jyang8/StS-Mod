package schedulemod.potions;

import static schedulemod.BasicMod.makeID;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import schedulemod.actions.SleepAction;

public class MelatoninPotion extends BasePotion {

    private static final int POTENCY = 1;

    public static final String POTION_ID = makeID("Melatonin");

    public MelatoninPotion() {
        super(POTION_ID, POTENCY, AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.MOON, Color.GOLD, Color.YELLOW, null);
    }

    public int getPotency() {
        return POTENCY;
    }

    public void use(AbstractCreature target) {
        AbstractPlayer p = AbstractDungeon.player;
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && target instanceof AbstractMonster) {
            addToBot(new SleepAction((AbstractMonster)target, (AbstractCreature)p));
        }
    }

    public String getDescription() {
        return this.potionStrings.DESCRIPTIONS[0];
    }
}