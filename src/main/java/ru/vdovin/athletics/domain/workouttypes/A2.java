package ru.arh.athletics.domain.workouttypes;

import java.util.List;
import ru.arh.athletics.domain.WorkoutInstance;
import ru.arh.athletics.domain.WorkoutRequest;
import ru.arh.athletics.domain.WorkoutTask;
import ru.arh.athletics.domain.WorkoutType;
import ru.arh.athletics.domain.Zone;
import ru.arh.athletics.domain.ZoneResolver;

public class A2 implements WorkoutType {
    private static final double[] levelToIntervalDurations = {0.5, 1, 2.5, 5, 1, 1.5, 2, 3, 1, 3.5, 1.5, 2.5, 1, 2, 4,
            1, 1.5, 3, 4.5, 1, 2, 2.5, 5, 1.5, 3.5, 1.5, 2, 3, 4, 2.5, 1.5, 4.5, 2, 3.5, 1.5, 2.5, 3, 5};

    private static final int[] levelToIntervalsCounts = {10, 5, 2, 1, 6, 4, 3, 2, 7, 2, 5, 3, 8, 4, 2, 9, 6, 3, 2, 10,
            5, 4, 2, 7, 3, 8, 6, 4, 3, 5, 9, 3, 7, 4, 10, 6, 5, 3};

    @Override
    public WorkoutInstance generate(WorkoutRequest request) {
        ZoneResolver zoneResolver = new ZoneResolver(request.getRfthrTempo(), request.getRfthrPulse(),
                request.getAge());
        Zone secondZone = zoneResolver.getForIntensityLevel("2");
        Zone fourthZone = zoneResolver.getForIntensityLevel("4");

        WorkoutTask warmup = new WorkoutTask(7 * 60, 1, 0.0, true, false, secondZone, secondZone);

        int level = Math.min(request.getLevel(), 38);

        WorkoutTask main = new WorkoutTask((int)(levelToIntervalDuration(level - 1) * 60),
                levelToIntervalsCount(level - 1), 0.25, false, false, fourthZone, secondZone);

        level = Math.max((int)(request.getMaxTime() - main.totalLength() - warmup.totalLength()), 0);
        WorkoutTask cooldown = new WorkoutTask(level,
                1, 0.0, false, true, secondZone, secondZone);

        return new WorkoutInstance("A2", List.of(warmup, main, cooldown));
    }

    private Double levelToIntervalDuration(int level) {
        if (level < 0 || level >= levelToIntervalDurations.length) {
            return 1.0;
        }
        return levelToIntervalDurations[level];
    }

    private Integer levelToIntervalsCount(int level) {
        if (level < 0 || level >= levelToIntervalsCounts.length) {
            return null;
        }
        return levelToIntervalsCounts[level];
    }
}
