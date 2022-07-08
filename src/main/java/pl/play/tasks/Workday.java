package pl.play.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.time.LocalDate;

@Getter
@PlanningEntity
@NoArgsConstructor
@AllArgsConstructor
public class Workday {

    @PlanningId
    private long id;

    private Employee employee;

    private LocalDate localDate;

    @Setter
    @PlanningVariable(valueRangeProviderRefs = "tasksAvailable", nullable = true)
    private Task task;
}
