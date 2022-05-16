package ru.arh.athletics.domain.workouttypes;

import java.util.List;
import ru.arh.athletics.domain.WorkoutInstance;
import ru.arh.athletics.domain.WorkoutRequest;
import ru.arh.athletics.domain.WorkoutTask;
import ru.arh.athletics.domain.WorkoutType;
import ru.arh.athletics.domain.Zone;
import ru.arh.athletics.domain.ZoneResolver;

public class B2 implements WorkoutType {
    @Override
    public WorkoutInstance generate(WorkoutRequest request) {
        ZoneResolver zoneResolver = new ZoneResolver(request.getRfthrTempo(), request.getRfthrPulse(),
                request.getAge());
        Zone secondZone = zoneResolver.getForIntensityLevel("2");

        WorkoutTask warmup = new WorkoutTask(7 * 60, 1, 0.0, true, false, secondZone, secondZone);

        int mainIntervalLength = (30 + (request.getLevel() - 1) * 5) * 60;
        WorkoutTask main = new WorkoutTask(mainIntervalLength, 1, 0.0, false, false, secondZone, secondZone);

        int level = Math.max((int)(request.getMaxTime() - main.totalLength() - warmup.totalLength()), 0);
        WorkoutTask cooldown = new WorkoutTask(level,
                1, 0.0, false, true, secondZone, secondZone);

        return new WorkoutInstance("B2", List.of(warmup, main, cooldown));
    }
}
