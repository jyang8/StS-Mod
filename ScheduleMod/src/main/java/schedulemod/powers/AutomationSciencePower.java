package schedulemod.powers;

import basemod.interfaces.CloneablePowerInterface;
import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.tempCards.Optimize;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static schedulemod.BasicMod.makeID;

public class AutomationSciencePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID("AutomationScience");
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static final int REQUIRED_DAMAGE = 20;
    private static final int SCHEDULE_SLOT = 1;
    private static final int SCHEDULE_SLOT_2 = 2;
    private Color greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);

    public AutomationSciencePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount >= REQUIRED_DAMAGE) {
            this.amount = REQUIRED_DAMAGE - 1;
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount >= REQUIRED_DAMAGE - this.amount) {
            addToBot(new ScheduleEventCard(new Optimize(), SCHEDULE_SLOT));
            addToBot(new ScheduleEventCard(new Optimize(), SCHEDULE_SLOT_2));
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        this.greenColor.a = c.a;
        c = this.greenColor;

        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(REQUIRED_DAMAGE - this.amount), x, y,
                this.fontScale, c);

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (REQUIRED_DAMAGE - this.amount) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AutomationSciencePower(this.owner, this.amount);
    }
}
