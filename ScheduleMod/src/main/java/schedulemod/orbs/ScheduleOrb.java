package schedulemod.orbs;

import static schedulemod.BasicMod.logger;
import static schedulemod.BasicMod.makeID;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import schedulemod.BasicMod;
import schedulemod.cards.navy.BaseCard;
import schedulemod.cards.navy.InScheduleCard;
import schedulemod.character.Entropy;
import schedulemod.vfx.AddCardToScheduleEffect;

public class ScheduleOrb extends AbstractOrb {

    public static final String[] DESC;

    private static final OrbStrings orbString;
    public static final String ORB_ID = makeID(ScheduleOrb.class.getSimpleName());
    public AbstractCard eventCard;
    private AbstractGameEffect scheduleStartEffect;
    public int slot;
    public AbstractCard triggeringCard;
    public AbstractCreature triggeringCardTarget;

    public ScheduleOrb(AbstractCard card, int slot) {
        this(card, slot, (CardGroup) null);
    }

    public ScheduleOrb(AbstractCard card, int slot, CardGroup source) {
        this(card, slot, source, false);
    }

    public ScheduleOrb(AbstractCard card, int slot, CardGroup source, boolean selfSchedule) {
        this.eventCard = card.makeCopy();
        this.slot = slot;
        if (this.eventCard.hasTag(Entropy.Enums.EVENT))
            ((BaseCard) this.eventCard).belongedOrb = this;
        BasicMod.logger.info("New Schedule Orb made:" + card);
        this.eventCard.beginGlowing();
        this.name = orbString.NAME + this.eventCard.name;
        this.channelAnimTimer = 0.5F;

        // TODO
        this.basePassiveAmount = this.passiveAmount = this.slot;
        this.baseEvokeAmount = this.basePassiveAmount;
        this.evokeAmount = this.passiveAmount;

        card.targetAngle = 0.0F;
        this.eventCard.tags.add(Entropy.Enums.SCHEDULE_GLOW);
        updateDescription();
        initialize(source, selfSchedule);
        if (this.eventCard instanceof InScheduleCard)
            ((InScheduleCard) this.eventCard).whenEnteredSchedule(this);
    }

    public void applyFocus() {
    }

    @Override
    public void updateDescription() {
        applyFocus();
        if (this.slot > 1) {
            this.description = this.eventCard.name + DESC[1] + (this.slot + 1) + DESC[2];
        } else {
            this.description = this.eventCard.name + DESC[0];
        }
    }

    public void playCopy(AbstractCard triggeringCard, AbstractCreature triggeringCardTarget) {
        if (!this.eventCard.purgeOnUse) {
            logger.info("Evoking card:" + triggeringCard + "," + triggeringCardTarget);
            AbstractMonster m = null;
            if (triggeringCardTarget != null)
                m = (AbstractMonster) triggeringCardTarget;
            AbstractCard tmp = this.eventCard.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            if (m != null)
                tmp.calculateCardDamage(m);
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(
                    new CardQueueItem(tmp, m, this.eventCard.energyOnUse, true, true),
                    true);
        }
    }

    public void onEvoke() {
        if (!(AbstractDungeon.player instanceof Entropy)) {
            return;
        }
        Entropy entropy = (Entropy) AbstractDungeon.player;
        if (this.slot > 1) {
            this.slot--;
            this.evokeAmount--;
            this.passiveAmount--;
            updateAnimation();
            updateDescription();
        } else {
            this.triggeringCard = entropy.getCurrentlyEvokingCard();
            this.triggeringCardTarget = entropy.getCurrentlyEvokingAction().target;
            playCopy(triggeringCard, triggeringCardTarget);
            this.eventCard.superFlash(Color.GOLDENROD);
        }
    }

    public void triggerEvokeAnimation() {
    }

    public void updateAnimation() {
        super.updateAnimation();
    }

    private void initialize(CardGroup source, boolean selfSchedule) {
        if (source != null) {
            source.removeCard(this.eventCard);
            switch (source.type) {
                case HAND:
                    this.scheduleStartEffect = new AddCardToScheduleEffect(this.eventCard, this,
                            this.eventCard.current_x, this.eventCard.current_y, !selfSchedule);
                    break;
                default:
                    this.scheduleStartEffect = new AddCardToScheduleEffect(this.eventCard, this, Settings.WIDTH / 2.0F,
                            Settings.HEIGHT / 2.0F, !selfSchedule);
                    break;
            }
        } else {
            this.scheduleStartEffect = new AddCardToScheduleEffect(this.eventCard, this, Settings.WIDTH / 2.0F,
                    Settings.HEIGHT / 2.0F, !selfSchedule);
        }
        AbstractDungeon.effectsQueue.add(this.scheduleStartEffect);
        this.eventCard.retain = false;
    }

    public void update() {
        super.update();
        if (this.scheduleStartEffect == null || this.scheduleStartEffect.isDone) {
            this.eventCard.target_x = this.tX;
            this.eventCard.target_y = this.tY;
            this.eventCard.applyPowers();
            if (this.hb.hovered) {
                this.eventCard.targetDrawScale = 1.0F;
            } else {
                this.eventCard.targetDrawScale = BasicMod.scheduleCardRenderScale.floatValue();
            }
        }
        this.eventCard.update();
    }

    public void render(SpriteBatch sb) {
        if (!this.hb.hovered && (this.scheduleStartEffect == null || this.scheduleStartEffect.isDone))
            renderActual(sb);
    }

    public void renderActual(SpriteBatch sb) {
        this.eventCard.render(sb);
        if (!this.hb.hovered)
            renderText(sb);
        this.hb.render(sb);
    }

    public void renderPreview(SpriteBatch sb) {
        if (this.hb.hovered && (this.scheduleStartEffect == null || this.scheduleStartEffect.isDone))
            renderActual(sb);
    }

    public void playChannelSFX() {
    }

    public AbstractOrb makeCopy() {
        return new ScheduleOrb(this.eventCard, this.slot);
    }

    static {
        orbString = CardCrawlGame.languagePack.getOrbString(ORB_ID);
        DESC = orbString.DESCRIPTION;
    }
}
