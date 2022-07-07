package pl.play.numbers;

import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class Controller {

    private final SolverManager<Solution, Long> solverManager;

    @GetMapping
    public void start() throws ExecutionException, InterruptedException {
        Solution solution = new Solution();

        DayWrapper dayWrapper = new DayWrapper();
        dayWrapper.setId(1L);
        dayWrapper.setDay(DayOfWeek.FRIDAY);
        DayWrapper dayWrapper2 = new DayWrapper();
        dayWrapper2.setId(2L);
        dayWrapper2.setDay(DayOfWeek.TUESDAY);
        DayWrapper dayWrapper3 = new DayWrapper();
        dayWrapper3.setId(5L);
        dayWrapper3.setDay(DayOfWeek.SUNDAY);

        solution.setDaysAvailable(List.of(dayWrapper, dayWrapper2, dayWrapper3));
        List<Weekday> weekdays = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Weekday weekday = new Weekday();
            weekday.setId(100L + i);
            weekdays.add(weekday);
        }

        solution.setDaysPlanned(weekdays);

        SolverJob<Solution, Long> solve = solverManager.solve(1L, solution);

        Solution finalBestSolution = solve.getFinalBestSolution();

        System.out.println(finalBestSolution);
    }
}
