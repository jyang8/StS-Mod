package schedulemod.bosses;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class LoadFormImageAction extends AbstractGameAction {

    private BossBen bossBen;

    public LoadFormImageAction(BossBen bossBen) {
        this.bossBen = bossBen;
    }

    @Override
    public void update() {
        bossBen.loadFormImage();
        this.isDone = true;
    }

}
