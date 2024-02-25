package schedulemod.modifiers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.AbstractCardModifier;

public class MultiplyDamageModifier extends AbstractCardModifier {

    public int amount = 1;
    public int priority = 3;

    public MultiplyDamageModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage * this.amount;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new MultiplyDamageModifier(amount);
    }

}
