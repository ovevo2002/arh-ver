package ru.arh.athletics.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.arh.athletics.domain.User;
import ru.arh.athletics.domain.UserRepository;
import ru.arh.athletics.domain.Workout;
import ru.arh.athletics.domain.WorkoutInstance;
import ru.arh.athletics.domain.WorkoutRepository;
import ru.arh.athletics.domain.WorkoutRequest;
import ru.arh.athletics.domain.WorkoutResult;
import ru.arh.athletics.domain.WorkoutStatus;

@Service
public class WorkoutService {

    private final WorkoutInstanceFactory workoutInstanceFactory;

    private final WorkoutRepository workoutRepository;

    private final UserRepository userRepository;

    public WorkoutService(WorkoutInstanceFactory workoutInstanceFactory, WorkoutRepository workoutRepository,
            UserRepository userRepository) {
        this.workoutInstanceFactory = workoutInstanceFactory;
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Workout createWorkout(WorkoutRequest workoutRequest) {
        WorkoutInstance workoutInstance = workoutInstanceFactory.create(workoutRequest);
        User client = getCurrentUser();
        Workout workout = new Workout();
        workout.setClient(client);
        workout.setRequest(workoutRequest);
        workout.setTasks(workoutInstance.getTasks());
        workout.setWorkoutType(workoutInstance.getName());
        workout.setStatus(WorkoutStatus.created);

        return workoutRepository.save(workout);
    }

    public Page<Workout> findWorkouts(Pageable pageable) {
        return workoutRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Workout findWorkout(Long id) {
        return workoutRepository.findByIdAndClient(id, getCurrentUser())
                .orElseThrow(() -> new RuntimeException("Workout with id " + id + " not found for current user"));
    }

    @Transactional
    public void updateWorkoutResult(Long id, WorkoutResult workoutResult) {
        Workout workout = findWorkout(id);
        workout.setResult(workoutResult);

        workoutRepository.save(workout);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        Workout workout = findWorkout(id);
        workoutRepository.delete(workout);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User with username " + username + " not found"));
    }
}
