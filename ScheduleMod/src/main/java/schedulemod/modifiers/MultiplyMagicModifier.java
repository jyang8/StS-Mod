package schedulemod.modifiers;

import java.io.Serializable;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.AbstractCardModifier;

public class MultiplyMagicModifier extends AbstractCardModifier implements Serializable {

    public int multiplier = 1;

    public MultiplyMagicModifier(int multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        return magic *= multiplier;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MultiplyMagicModifier(multiplier);
    }

}
