package pl.play.tasks;

import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@Data
@PlanningSolution
public class PiSolution {

    @PlanningEntityCollectionProperty
    private List<Workday> daysPlanned;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "tasksAvailable")
    private List<Task> tasksAvailable;

    @PlanningScore
    private HardSoftScore score;
}
