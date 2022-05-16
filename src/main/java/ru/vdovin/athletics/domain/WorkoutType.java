package ru.arh.athletics.domain;

public interface WorkoutType {

    WorkoutInstance generate(WorkoutRequest request);
}
