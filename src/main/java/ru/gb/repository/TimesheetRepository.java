package ru.gb.repository;

import org.springframework.stereotype.Repository;
import ru.gb.model.Timesheet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Repository
public class TimesheetRepository {

    private static Long sequence = 1L;
    private final List<Timesheet> timesheets = new ArrayList<>();

    public Optional<Timesheet> getById(Long id) {
        return timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst();
    }

    public List<Timesheet> getAll(LocalDate createdAtBefore, LocalDate createdAtAfter) {
        Predicate<Timesheet> filter = it -> true;

        if (Objects.nonNull(createdAtBefore)) {
            filter = filter.and(it -> it.getCreatedAt().isBefore(createdAtBefore));
        }

        if (Objects.nonNull(createdAtAfter)) {
            filter = filter.and(it -> it.getCreatedAt().isAfter(createdAtAfter));
        }

        return timesheets.stream().filter(filter).toList();
    }

    public List<Timesheet> getAll() {
        return List.copyOf(timesheets);
    }

    public Timesheet create(Timesheet timesheet) {
        timesheet.setId(sequence++);
        timesheets.add(timesheet);
        return timesheet;
    }

    public void delete(Long id) {
        timesheets.stream()
                .filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .ifPresent(timesheets::remove);
    }

    public List<Timesheet> getByProjectId(Long projectId) {
        return timesheets.stream()
                .filter(it -> Objects.equals(it.getProjectId(), projectId))
                .toList();
    }
}
