package pl.play.tasks;

import lombok.Data;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@Data
public final class Task {
    @PlanningId
    private final long id;
    private final String issueId;
    private final int priority;
    private final EmployeeType employeeType;

}