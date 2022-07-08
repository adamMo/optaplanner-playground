package pl.play.numbers;

import lombok.Data;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Data
@PlanningEntity
class Weekday {

    @PlanningId
    private Long id;

    @PlanningVariable(valueRangeProviderRefs = "daysAvailable", nullable = true)
    private DayWrapper day;
}
