package schedulemod.cards.tempCards;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType;

import schedulemod.cards.EventCard;
import schedulemod.character.Entropy;
import schedulemod.powers.FatiguePower;
import schedulemod.util.CardStats;

public class Cleaning extends EventCard {
    public static final String ID = makeID(Cleaning.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0);

    private static final int FATIGUE = 10;
    private static final int UPGRADE_FATIGUE = 13;

    private int multiplier = 0;

    public void setX(int amount) {
        this.multiplier = amount;
        this.rawDescription = (this.baseMagicNumber == 1) ? cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + this.multiplier + cardStrings.EXTENDED_DESCRIPTION[2]
                : cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + this.multiplier + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    public Cleaning() {
        super(ID, info);
        tags.add(Entropy.Enums.EVENT);
        tags.add(Entropy.Enums.FATIGUE_EVENT);
        setExhaust(true);
        setMagic(FATIGUE, UPGRADE_FATIGUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < multiplier; i++) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(mo, p, new FatiguePower(mo, p, this.magicNumber)));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Cleaning();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        Cleaning card = (Cleaning) super.makeStatEquivalentCopy();
        card.setX(this.multiplier);
        return card; 
    }

    @Override
    public void useEvent(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        for (int i = 0; i < multiplier; i++) {
            for (AbstractMonster mo : monsters) {
                addToTop(new ApplyPowerAction(mo, p, new FatiguePower(mo, p, this.magicNumber)));
            }
            addToTop(new VFXAction(new ShockWaveEffect(p.hb_x, p.hb_y, Color.GRAY, ShockWaveType.CHAOTIC)));
            addToTop(new WaitAction(0.2f));
        }
    }

}
