package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class PremiumToasterPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("PremiumToaster");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PremiumToasterPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateExistingSlicesOfBread();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateExistingSlicesOfBread();
    }

    private void updateExistingSlicesOfBread() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof schedulemod.cards.tempCards.SliceOfBread) {
                if (!c.upgraded) {
                    c.baseBlock = 8 + this.amount;
                    continue;
                }
                c.baseBlock = 10 + this.amount;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof schedulemod.cards.tempCards.SliceOfBread) {
                if (!c.upgraded) {
                    c.baseBlock = 8 + this.amount;
                    continue;
                }
                c.baseBlock = 10 + this.amount;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof schedulemod.cards.tempCards.SliceOfBread) {
                if (!c.upgraded) {
                    c.baseBlock = 8 + this.amount;
                    continue;
                }
                c.baseBlock = 10 + this.amount;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof schedulemod.cards.tempCards.SliceOfBread) {
                if (!c.upgraded) {
                    c.baseBlock = 8 + this.amount;
                    continue;
                }
                c.baseBlock = 10 + this.amount;
            }
        }
    }

    public void onDrawOrDiscard() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof schedulemod.cards.tempCards.SliceOfBread) {
                if (!c.upgraded) {
                    c.baseBlock = 8 + this.amount;
                    continue;
                }
                c.baseBlock = 10 + this.amount;
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new PremiumToasterPower(owner, amount);
    }
}
