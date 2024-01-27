package schedulemod.orbs;

import static schedulemod.BasicMod.makeID;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import schedulemod.BasicMod;
import schedulemod.cards.EventCard;
import schedulemod.cards.navy.BaseCard;
import schedulemod.cards.navy.InScheduleCard;
import schedulemod.character.Entropy;
import schedulemod.patches.EventsPlayedPatch.EventsPlayedThisCombatField;
import schedulemod.patches.EventsPlayedPatch.EventsPlayedThisTurnField;
import schedulemod.powers.FortyEightHourDayPower;
import schedulemod.vfx.AddCardToScheduleEffect;

public class ScheduleOrb extends AbstractOrb {

    public static final String[] DESC;

    private static final OrbStrings orbString;
    public static final String ORB_ID = makeID(ScheduleOrb.class.getSimpleName());
    public EventCard eventCard;
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
        assert card instanceof EventCard : "Can only schedule Event cards.";
        this.eventCard = (EventCard) card.makeStatEquivalentCopy();
        this.slot = slot;
        if (this.eventCard.hasTag(Entropy.Enums.EVENT))
            ((BaseCard) this.eventCard).belongedOrb = this;
        BasicMod.logger.info("New Schedule Orb made:" + card);
        this.eventCard.beginGlowing();
        this.name = orbString.NAME + this.eventCard.name;
        this.channelAnimTimer = 0.5F;

        // TODO
        updateAmount();

        card.targetAngle = 0.0F;
        this.eventCard.tags.add(Entropy.Enums.SCHEDULE_GLOW);
        updateDescription();
        initialize(source, selfSchedule);
        if (this.eventCard instanceof InScheduleCard)
            ((InScheduleCard) this.eventCard).whenEnteredSchedule(this);
    }

    public void updateAmount() {
        this.basePassiveAmount = this.passiveAmount = this.slot + 1;
        this.baseEvokeAmount = this.basePassiveAmount;
        this.evokeAmount = this.passiveAmount;
    }

    public void applyFocus() {
    }

    @Override
    public void updateDescription() {
        applyFocus();
        if (this.slot > 0) {
            this.description = this.eventCard.name + DESC[1] + (this.slot + 1) + DESC[2];
        } else {
            this.description = this.eventCard.name + DESC[0];
        }
    }

    @Override
    public void setSlot(int slotNum, int maxOrbs) {
        this.slot = slotNum;
        updateAmount();
        super.setSlot(slotNum, maxOrbs);
        updateDescription();
    }

    // TODO:
    // Add interface OrbModifier
    // Add patch to AbstractCard.applyPowers to look at current orb and see if there
    // are any modifiers on the current orb
    // Consider card playing animation

    public void onEvoke() {
        if (!(AbstractDungeon.player instanceof Entropy)) {
            return;
        }
        Entropy p = (Entropy) AbstractDungeon.player;
        this.triggeringCard = p.getCurrentlyEvokingCard();
        this.triggeringCardTarget = p.getCurrentlyEvokingMonster();

        AbstractMonster m = null;
        if (triggeringCard.target == CardTarget.ENEMY) {
            // Try using the trigger target
            if (triggeringCardTarget != null
                    && triggeringCardTarget instanceof AbstractMonster
                    && !triggeringCardTarget.isDeadOrEscaped()) {
                m = (AbstractMonster) triggeringCardTarget;
            }
        } else {
            // If that doesn't work then use a random alive monster
            m = (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true,
                    AbstractDungeon.cardRandomRng);
        }
        EventsPlayedThisTurnField.eventsPlayedThisTurn.get(AbstractDungeon.actionManager).add(this.eventCard);
        EventsPlayedThisCombatField.eventsPlayedThisCombat.get(AbstractDungeon.actionManager).add(this.eventCard);
        this.eventCard.useEvent(p, m, triggeringCard);

        if (p.hasPower(FortyEightHourDayPower.POWER_ID)) {
            EventsPlayedThisTurnField.eventsPlayedThisTurn.get(AbstractDungeon.actionManager).add(this.eventCard);
            EventsPlayedThisCombatField.eventsPlayedThisCombat.get(AbstractDungeon.actionManager).add(this.eventCard);
            if (m.isDeadOrEscaped()) {
                m = (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true,
                AbstractDungeon.cardRandomRng);
            }
            this.eventCard.useEvent(p, m, triggeringCard);
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
