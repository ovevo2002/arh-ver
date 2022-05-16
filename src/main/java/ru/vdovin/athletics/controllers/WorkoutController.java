package ru.arh.athletics.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.arh.athletics.domain.Workout;
import ru.arh.athletics.domain.WorkoutRequest;
import ru.arh.athletics.domain.WorkoutResult;
import ru.arh.athletics.services.WorkoutService;

@Controller
@RequestMapping("workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public String workouts(ModelMap modelMap, Pageable pageable) {
        Page<Workout> page = workoutService.findWorkouts(pageable);
        modelMap.addAttribute("page", page);

        return "workout/workouts";
    }

    @GetMapping("{id}")
    public String workout(@PathVariable Long id, ModelMap modelMap) {
        Workout workout = workoutService.findWorkout(id);
        modelMap.addAttribute("workout", workout);
        modelMap.addAttribute("workoutResult", workout.getResult() == null ? new WorkoutResult() : workout.getResult());
        modelMap.addAttribute("hasTempo", workout.getTasks()
                .stream()
                .mapToDouble(task -> task.getZone().getExpectedTempoFrom())
                .average()
                .isPresent());
        modelMap.addAttribute("hasSpeed", workout.getTasks()
                .stream()
                .mapToDouble(task -> task.getZone().getExpectedSpeedFrom())
                .average()
                .isPresent());

        return "workout/workout";
    }

    @GetMapping("create")
    public String createWorkout(ModelMap modelMap) {
        modelMap.addAttribute("workoutRequest", new WorkoutRequest());
        return "workout/create-workout";
    }

    @PostMapping("create")
    public String submitCreateWorkout(@ModelAttribute @Validated WorkoutRequest workoutRequest, BindingResult errors) {
        if (!errors.hasErrors()) {
            Workout workout = workoutService.createWorkout(workoutRequest);
            return "redirect:/workouts/" + workout.getId();
        }

        return "workout/create-workout";
    }

    @PostMapping("{id}/update-result")
    public String updateWorkoutResult(@PathVariable("id") Long id,
            @ModelAttribute @Validated WorkoutResult workoutResult, BindingResult errors) {
        if (!errors.hasErrors()) {
            workoutService.updateWorkoutResult(id, workoutResult);
        }

        return "redirect:/workout/" + id;
    }

    @PostMapping("{id}/delete")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);

        return "redirect:/workouts";
    }
}
