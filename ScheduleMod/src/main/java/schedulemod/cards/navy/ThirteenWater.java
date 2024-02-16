package schedulemod.cards.navy;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import schedulemod.actions.ScheduleEventCard;
import schedulemod.cards.EventCard;
import schedulemod.cards.tempCards.FifthCourse;
import schedulemod.cards.tempCards.FirstCourse;
import schedulemod.cards.tempCards.FourthCourse;
import schedulemod.cards.tempCards.SecondCourse;
import schedulemod.cards.tempCards.SeventhCourse;
import schedulemod.cards.tempCards.SixthCourse;
import schedulemod.cards.tempCards.ThirdCourse;
import schedulemod.character.Entropy;
import schedulemod.util.CardStats;

public class ThirteenWater extends BaseCard {
    public static final String ID = makeID(ThirteenWater.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Entropy.Enums.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            -1);

    private float previewTimer = 1.5f;
    private int nextPreview = 0;
    private ArrayList<EventCard> courseCards;
    private int nextMaybeOpenSlot = 0;

    public ThirteenWater() {
        super(ID, info);
        this.courseCards = new ArrayList<EventCard>();
        this.courseCards.add(new FirstCourse());
        this.courseCards.add(new SecondCourse());
        this.courseCards.add(new ThirdCourse());
        this.courseCards.add(new FourthCourse());
        this.courseCards.add(new FifthCourse());
        this.courseCards.add(new SixthCourse());
        this.courseCards.add(new SeventhCourse());
        setNextCardPreview();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);
        for (int i = 0; i < this.energyOnUse + (upgraded ? 1 : 0); i++) {
            int slot = getNextScheduleSlot();
            if (slot != -1) {
                addToBot(new ScheduleEventCard(courseCards.get(slot), slot + 1));
            }
        }
        nextMaybeOpenSlot = 0;
    }

    private int getNextScheduleSlot() {
        int slot = -1;
        for (int i = nextMaybeOpenSlot; i < AbstractDungeon.player.orbs.size(); i++) {
            if (AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot) {
                slot = i;
                nextMaybeOpenSlot = i + 1;
                break;
            }
        }
        return slot;
    }

    private void setNextCardPreview() {
        if (nextPreview >= courseCards.size()) {
            nextPreview = 0;
        }
        this.cardsToPreview = courseCards.get(nextPreview);
        nextPreview++;
    }

    @Override
    public void update() {
        super.update();
        previewTimer -= Gdx.graphics.getDeltaTime();
        if (previewTimer < 0.0F) {
            setNextCardPreview();
            previewTimer = 1.5F;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ThirteenWater();
    }
}
