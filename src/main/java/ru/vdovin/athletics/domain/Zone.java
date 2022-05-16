package ru.arh.athletics.domain;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Zone implements Serializable {

    @NotNull
    private String name;

    private Integer expectedPulse;

    private Double expectedTempoFrom;

    private Double expectedTempoTo;

    private Double expectedSpeedFrom;

    private Double expectedSpeedTo;
}
