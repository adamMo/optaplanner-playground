package pl.play.numbers;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class Constraints implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                roomConflict(constraintFactory),
                oneDayAtTheTime(constraintFactory),
                nonEmpty(constraintFactory)
        };
    }

    Constraint roomConflict(ConstraintFactory constraintFactory) {
        // prefer the days with higher ordinal number than others
        return constraintFactory
                .forEachUniquePair(Weekday.class,
                        Joiners.lessThan(Weekday::getId),
                        Joiners.greaterThan(dayWrapper -> dayWrapper.getDay().getDay().ordinal())
                )
                .reward("higher ordinal property", HardSoftScore.ONE_SOFT);
    }

    Constraint nonEmpty(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(Weekday.class)
                .ifExists(Weekday.class, Joiners.lessThan(Weekday::getId),
                        Joiners.filtering((Weekday o, Weekday o2) -> o.getDay() == null)
                )
                .penalize("not null days", HardSoftScore.ONE_SOFT);
    }

    Constraint oneDayAtTheTime(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(Weekday.class,
                        //do not use the same day twice!
                        Joiners.equal(weekday -> weekday.getDay().getId())
                )
                .penalize("the same day conflict", HardSoftScore.ONE_HARD);
    }
}
