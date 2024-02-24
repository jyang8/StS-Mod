package schedulemod.bosses;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CreateIntentAction extends AbstractGameAction {

    public AbstractMonster owner;

    public CreateIntentAction(AbstractMonster owner) {
        this.owner = owner;
    }

    @Override
    public void update() {
        this.owner.createIntent();
        this.isDone = true;
    }

}
