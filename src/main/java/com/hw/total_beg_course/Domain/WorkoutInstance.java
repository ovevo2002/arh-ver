package com.hw.total_beg_course.Domain;

public class WorkoutInstance {
    public int[] tasks;
    public String name;

    public WorkoutInstance(int[] tasks, String name) {
        this.tasks = tasks;
        this.name = name;
    }

//    public totalLength() {
//        return collect($this -> tasks)->sum(function($task) {
//            return $task -> totalLength();
//        });
}
