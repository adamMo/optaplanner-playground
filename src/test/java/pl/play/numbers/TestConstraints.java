package pl.play.numbers;

import org.junit.jupiter.api.Test;
import org.optaplanner.test.api.score.stream.ConstraintVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class TestConstraints {

    @Autowired
    ConstraintVerifier<Constraints, Solution> constraintVerifier;
    // = ConstraintVerifier.build(new Constraints(), Solution.class, Weekday.class);


    @Test
    void nonEmptyDayPenalty() {

        DayWrapper dayWrapper = new DayWrapper();
        dayWrapper.setId(1L);
        dayWrapper.setDay(DayOfWeek.FRIDAY);
        DayWrapper dayWrapper2 = new DayWrapper();
        dayWrapper2.setId(2L);
        dayWrapper2.setDay(DayOfWeek.TUESDAY);
        DayWrapper dayWrapper3 = new DayWrapper();
        dayWrapper3.setId(5L);
        dayWrapper3.setDay(DayOfWeek.SUNDAY);


        Weekday weekday1 = new Weekday();
        Weekday weekday2 = new Weekday();
        Weekday weekday3 = new Weekday();
        weekday1.setId(100L+1);
        weekday2.setId(100L+2);
        weekday2.setDay(dayWrapper2);
        weekday3.setId(100L+3);
        weekday3.setDay(dayWrapper3);



        constraintVerifier.verifyThat(Constraints::nonEmpty)
                .given(dayWrapper, dayWrapper2, dayWrapper3, weekday1, weekday2, weekday3)
                .penalizes();

    }

}
