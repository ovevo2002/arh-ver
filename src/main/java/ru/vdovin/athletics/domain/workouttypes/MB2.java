package ru.arh.athletics.domain.workouttypes;

import java.util.List;
import ru.arh.athletics.domain.WorkoutInstance;
import ru.arh.athletics.domain.WorkoutRequest;
import ru.arh.athletics.domain.WorkoutTask;
import ru.arh.athletics.domain.WorkoutType;
import ru.arh.athletics.domain.Zone;
import ru.arh.athletics.domain.ZoneResolver;

public class MB2 implements WorkoutType {

    private static final int[] levelToIntervalDurations = {6, 7, 6, 8, 9, 7, 6, 10, 8, 11, 7, 6, 9, 12, 10, 8, 7, 11, 9,
            8, 12, 10, 9, 11, 10, 12, 11, 12};

    private static final int[] levelToIntervalsCounts = {3, 3, 4, 3, 3, 4, 5, 3, 4, 3, 5, 6, 4, 3, 4, 5, 6, 4, 5, 6, 4,
            5, 6, 5, 6, 5, 6, 6};

    @Override
    public WorkoutInstance generate(WorkoutRequest request) {
        ZoneResolver zoneResolver = new ZoneResolver(request.getRfthrTempo(), request.getRfthrPulse(),
                request.getAge());
        Zone secondZone = zoneResolver.getForIntensityLevel("2");
        Zone fourthZone = zoneResolver.getForIntensityLevel("4");

        WorkoutTask warmup = new WorkoutTask(7 * 60, 1, 0.0, true, false, secondZone, secondZone);

        int level = Math.min(request.getLevel(), 28);

        WorkoutTask main = new WorkoutTask(levelToIntervalDuration(level - 1) * 60, levelToIntervalsCount(level - 1),
                0.25, false, false, fourthZone, secondZone);

        level = Math.max((int)(request.getMaxTime() - main.totalLength() - warmup.totalLength()), 0);
        WorkoutTask cooldown = new WorkoutTask(level,
                1, 0.0, false, true, secondZone, secondZone);

        return new WorkoutInstance("MB2", List.of(warmup, main, cooldown));
    }

    private int levelToIntervalDuration(int level) {
        if (level < 0 || level >= levelToIntervalDurations.length) {
            return 1;
        }
        return levelToIntervalDurations[level];
    }

    private int levelToIntervalsCount(int level) {
        if (level < 0 || level >= levelToIntervalsCounts.length) {
            return 1;
        }
        return levelToIntervalsCounts[level];
    }
}
