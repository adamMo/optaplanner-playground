package pl.play.tasks;

import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController("tasks")
@RequestMapping
public class Controller {

    private final SolverManager<PiSolution, Long> solverManager;

    @GetMapping("/tasks")
    public List<Workday> start() throws ExecutionException, InterruptedException {
        PiSolution solution = new PiSolution();

        final List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Archie", EmployeeType.ARCH));
        employees.add(new Employee("Devell", EmployeeType.DEV));
        employees.add(new Employee("Qastor", EmployeeType.QA));

        List<LocalDate> dates = new ArrayList<>();
        LocalDate tempDate = LocalDate.now();
        for (int i = 0; i < 20; i++) {
            dates.add(tempDate.plusDays(i));
        }

        long idCounter = 100;

        List<Workday> workdays = new ArrayList<>();
        for (LocalDate date : dates) {
            for (Employee employee : employees) {
                 workdays.add(new Workday(idCounter++, employee, date, null));
            }
        }

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(idCounter++, "API-10", 5, EmployeeType.ARCH));
        tasks.add(new Task(idCounter++, "API-10", 5, EmployeeType.ARCH));
        tasks.add(new Task(idCounter++, "API-10", 5, EmployeeType.DEV));
        tasks.add(new Task(idCounter++, "API-10", 5, EmployeeType.DEV));
        tasks.add(new Task(idCounter++, "API-10", 5, EmployeeType.DEV));
        tasks.add(new Task(idCounter++, "API-10", 5, EmployeeType.QA));
        tasks.add(new Task(idCounter++, "API-10", 5, EmployeeType.QA));

        solution.setTasksAvailable(tasks);
        solution.setDaysPlanned(workdays);

        SolverJob<PiSolution, Long> solve = solverManager.solve(1L, solution);

        PiSolution finalBestSolution = solve.getFinalBestSolution();

        System.out.println(finalBestSolution);
        return finalBestSolution.getDaysPlanned().stream().filter(e -> e.getTask() != null).collect(Collectors.toList());
    }
}
