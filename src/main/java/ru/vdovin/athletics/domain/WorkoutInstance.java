package ru.arh.athletics.domain;

import java.util.List;
import lombok.Data;

@Data
public class WorkoutInstance {

    private String name;

    private List<WorkoutTask> tasks;

    public WorkoutInstance(String name, List<WorkoutTask> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public long totalLength() {
        return tasks.stream().mapToLong(WorkoutTask::totalLength).sum();
    }
}
