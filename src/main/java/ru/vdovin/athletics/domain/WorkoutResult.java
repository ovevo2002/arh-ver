package ru.arh.athletics.domain;

import java.io.Serializable;
import javax.persistence.Column;
import lombok.Data;

@Data
public class WorkoutResult implements Serializable {

    private Integer resultingTime;

    private Integer resultingDistance;

    private Double resultingAvgPulse;

    @Column(length = 1024)
    private String comment;

}
