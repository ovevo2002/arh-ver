package com.hw.total_beg_course.Domain;

import static java.lang.Math.round;

public class WorkoutTask {
    public int length;
    public int repetitions;
    public float cooldown_percent;

    public boolean is_warmup;
    public boolean is_cooldown;

    public Zone zone;
    public Zone cooldown_zone;


    public WorkoutTask(int length, int repetitions, float cooldown_percent, boolean is_warmup, boolean is_cooldown, Zone zone, Zone cooldown_zone) {
        this.length = length;
        this.repetitions = repetitions;
        this.cooldown_percent = cooldown_percent;
        this.is_warmup = is_warmup;
        this.is_cooldown = is_cooldown;

        this.zone = zone;
        this.cooldown_zone = cooldown_zone;
    }

    public int totalLength() {
        int basic = this.length * this.repetitions;
        int cooldowns = round(this.length * this.cooldown_percent * (this.repetitions - 1));
        return basic + cooldowns;
    }
}

