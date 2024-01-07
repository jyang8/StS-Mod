package schedulemod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import schedulemod.character.Entropy;
import schedulemod.orbs.ScheduleOrb;

public class AddCardToScheduleEffect extends AbstractGameEffect {
    private static final float EFFECT_DUR = 1.5F;

    private static final float PADDING = 30.0F * Settings.scale;

    private AbstractCard card;

    private ScheduleOrb o;

    private float glowPoint;

    private boolean glowStartHit;

    public AddCardToScheduleEffect(AbstractCard srcCard, ScheduleOrb o, float startX, float startY, boolean instant) {
        this.card = srcCard;
        this.duration = this.startingDuration = instant ? 0.1F : 1.0F;
        this.glowPoint = this.startingDuration * 0.25F;
        this.card.target_x = startX;
        this.card.target_y = startY;
        this.card.targetDrawScale = 0.75F;
        this.o = o;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        this.card.update();
        if (this.duration < this.glowPoint) {
            if (!this.glowStartHit) {
                this.card.beginGlowing();
                this.card.tags.add(Entropy.Enums.SCHEDULE_GLOW);
                this.card.superFlash(Color.GOLDENROD);
                this.glowStartHit = true;
            }
            if (this.duration < 0.0F)
                this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (!this.isDone)
            this.card.render(sb);
    }

    public void dispose() {}
}
