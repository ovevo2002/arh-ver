package ru.arh.athletics.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutRequest {

    @NotNull
    @Min(1)
    private Integer age;

    @NotNull
    @Min(1)
    @Max(38)
    private Integer level;

    private Double rfthrPulse;

    private Integer rfthrTempo;

    @NotNull
    @Min(0)
    private Integer desiredIntensity;

    @NotNull
    @Min(0)
    private Integer maxTime;
}
