package ru.arh.athletics.services;

import org.springframework.stereotype.Component;
import ru.arh.athletics.domain.WorkoutInstance;
import ru.arh.athletics.domain.WorkoutRequest;
import ru.arh.athletics.domain.WorkoutType;
import ru.arh.athletics.domain.workouttypes.A2;
import ru.arh.athletics.domain.workouttypes.B2;
import ru.arh.athletics.domain.workouttypes.MB1;
import ru.arh.athletics.domain.workouttypes.MB2;
import ru.arh.athletics.domain.workouttypes.MB4;

@Component
public class WorkoutInstanceFactory {

    private final A2 a2 = new A2();

    private final B2 b2 = new B2();

    private final MB1 mb1 = new MB1();

    private final MB2 mb2 = new MB2();

    private final MB4 mb4 = new MB4();

    public WorkoutInstance create(WorkoutRequest request) {
        WorkoutType type = this.chooseWorkoutType(request);
        return type.generate(request);
    }

    private WorkoutType chooseWorkoutType(WorkoutRequest request) {
        switch (request.getDesiredIntensity()) {
        case 0:
            return b2;
        case 1:
            if (Math.random() < 0.5) {
                return mb1;
            } else {
                return mb2;
            }
        case 2:
            return mb4;
        case 3:
            return a2;
        }

        throw new IllegalStateException("Invalid desiredIntensity, must be 0, 1, 2 or 3");
    }
}
