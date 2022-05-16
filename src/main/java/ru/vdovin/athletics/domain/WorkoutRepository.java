package ru.arh.athletics.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WorkoutRepository extends PagingAndSortingRepository<Workout, Long> {

    Page<Workout> findByClient(User client, Pageable pageable);

    Optional<Workout> findByIdAndClient(Long id, User user);
}
