package ru.arh.athletics.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkoutTask implements Serializable {

    private Integer length;

    public Integer repetitions;

    private Double cooldownPercent;

    private boolean isWarmup;

    private boolean isCooldown;

    private Zone zone;

    private Zone cooldownZone;

    public long totalLength() {
        int basic = this.length * this.repetitions;
        long cooldowns = Math.round(this.length * this.cooldownPercent * (this.repetitions - 1));
        return basic + cooldowns;
    }
}
