package pl.play.tasks;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class Constraints implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                oneDayAtTheTime(constraintFactory),
                rewardMatchingSkills(constraintFactory),
                skillTypeOrder(constraintFactory)
        };
    }

    Constraint skillTypeOrder(ConstraintFactory factory) {
        return factory.forEachUniquePair(Workday.class,
                //join two different dates
                Joiners.lessThan(Workday::getLocalDate),
                //join two days when tasks are the same
                Joiners.equal(e -> e.getTask().getIssueId()),
                //order them with enum's ordinal
                Joiners.lessThan(e -> e.getEmployee().type().ordinal())
        ).reward("skill type order", HardSoftScore.ONE_SOFT);
    }

    Constraint rewardMatchingSkills(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Workday.class)
                .filter(day -> day.getTask() != null)
                .filter(day -> day.getEmployee().type() == day.getTask().getEmployeeType())
                .reward("matching skills", HardSoftScore.ONE_HARD);
    }

    Constraint oneDayAtTheTime(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(Workday.class,
                        //do not use the same task entry twice!
                        Joiners.equal(day -> day.getTask().getId())
                )
                .penalize("the same task conflict", HardSoftScore.ofHard(5));
    }

}
