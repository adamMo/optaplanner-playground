package pl.play.numbers;

import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

/**
 * Super easy exercise to order days by their ordinal property
 */
@Data
@PlanningSolution
public class Solution {

    @PlanningEntityCollectionProperty
    private List<Weekday> daysPlanned;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "daysAvailable")
    private List<DayWrapper> daysAvailable;

    @PlanningScore
    private HardSoftScore score;
}

