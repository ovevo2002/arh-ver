package ru.arh.athletics.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Workout extends AbstractDomain {

    @Id
    @SequenceGenerator(name = "workoutSeq", sequenceName = "workout_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workoutSeq")
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private User client;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<WorkoutTask> tasks = new ArrayList<>();

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private WorkoutRequest request;

    private String workoutType;

    @Enumerated(EnumType.STRING)
    private WorkoutStatus status;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private WorkoutResult result;

    public long totalLength() {
        return this.asWorkoutInstance().totalLength();
    }

    public WorkoutInstance asWorkoutInstance() {
        List<WorkoutTask> workoutTasks = this.tasks.stream().map(task -> {
            Zone z1 = task.getZone();
            Zone z2 = task.getCooldownZone();
            return new WorkoutTask(task.getLength(), task.getRepetitions(), task.getCooldownPercent(), task.isWarmup(),
                    task.isCooldown(),
                    new Zone(z1.getName(), z1.getExpectedPulse(), z1.getExpectedTempoFrom(), z1.getExpectedTempoTo(),
                            z1.getExpectedSpeedFrom(), z1.getExpectedSpeedTo()),
                    new Zone(z2.getName(), z2.getExpectedPulse(), z2.getExpectedTempoFrom(), z2.getExpectedTempoTo(),
                            z2.getExpectedSpeedFrom(), z2.getExpectedSpeedTo()));
        }).collect(Collectors.toList());

        return new WorkoutInstance(this.getWorkoutType(), workoutTasks);
    }

    public Double runningEfficiency() {
        if (this.getResult() == null) {
            return null;
        }
        if (this.getResult().getResultingTime() == null) {
            return null;
        }
        if (this.getResult().getResultingAvgPulse() == null) {
            return null;
        }
        if (this.getResult().getResultingDistance() == null) {
            return null;
        }

        return Double.valueOf(this.getResult().getResultingDistance()) / this.getResult().getResultingTime() /
                this.getResult().getResultingAvgPulse();
    }
}
