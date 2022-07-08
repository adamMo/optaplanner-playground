package pl.play.numbers;

import lombok.Data;
import org.optaplanner.core.api.domain.lookup.PlanningId;

import java.time.DayOfWeek;

@Data
class DayWrapper {

    @PlanningId
    private Long id;

    private DayOfWeek day;
}
