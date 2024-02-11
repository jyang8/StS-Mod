package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import schedulemod.character.Entropy;
import schedulemod.relics.HungryCamera;

import static schedulemod.BasicMod.makeID;

public class SatietyPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("Satiety");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public SatietyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        ((Entropy) owner).gainSatiety(amount);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void updateDescription() {
        AbstractPlayer p = AbstractDungeon.player;
        int bellySize = p.hasPower(MaxSatietyPower.POWER_ID) ? p.getPower(MaxSatietyPower.POWER_ID).amount : 0;
        this.description = DESCRIPTIONS[0] + ((p instanceof Entropy ? ((Entropy) p).maxSatiety : 1) + bellySize) + DESCRIPTIONS[1];
    }

    public void checkSatiety() {
        AbstractPlayer p = AbstractDungeon.player;
        int bellySize = p.hasPower(MaxSatietyPower.POWER_ID) ? p.getPower(MaxSatietyPower.POWER_ID).amount : 0;
        if (!(owner.hasPower(makeID("HokkaidoUniBuffet")))) {
            if (this.amount >= (p instanceof Entropy ? ((Entropy) p).maxSatiety : 1) + bellySize) {
                this.amount = 0;
                flashWithoutSound();
                playApplyPowerSfx();
                addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
                if (p.hasRelic(HungryCamera.ID)) {
                    ((HungryCamera)p.getRelic(HungryCamera.ID)).triggered = true;
                }
                AbstractDungeon.actionManager.callEndTurnEarlySequence();
            }
        }
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        checkSatiety();
    }

    @Override
    public AbstractPower makeCopy() {
        return new SatietyPower(owner, amount);
    }
}
