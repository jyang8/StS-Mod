package schedulemod.cards.navy;

import schedulemod.orbs.ScheduleOrb;

public interface InScheduleCard {
    default void onStartOfTurn(ScheduleOrb orb) {}

    default void onEvoke(ScheduleOrb orb) {}

    default void whenEnteredSchedule(ScheduleOrb orb) {}

    default void whenPlayedFromSchedule() {}
}
