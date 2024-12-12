package delft;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CombinedTest {

    // ---------------- Course Tests ----------------

    @Test
    void testAddGrade() {
        Course course = new Course("Math");
        course.addGrade(90.0, 1);
        assertEquals(1, course.getNumberOfGrades());
        assertEquals(Arrays.asList(90.0), course.getGrades());
    }

    @Test
    void testAddMultipleGrades() {
        Course course = new Course("Math");
        course.addGrade(90.0, 1);
        course.addGrade(85.0, 2);
        course.addGrade(92.0, 3);
        assertEquals(3, course.getNumberOfGrades());
        assertEquals(Arrays.asList(90.0, 85.0, 92.0), course.getGrades());
    }


    @Test
    void testAddGradeToExistingCourse() {
        GradeTracker tracker = new GradeTracker();
        tracker.addCourse("Math");
        // Now add a grade to the existing "Math" course
        tracker.addGrade("Math", 95.0, 1);
        Course course = tracker.getCourse("Math");
        // Verify that the grade was indeed added
        assertEquals(1, course.getNumberOfGrades());
        assertEquals(95.0, course.getGrades().get(0), 0.01);
    }


    @Test
    void testCalculateCurrentGradeSimple() {
        Course course = new Course("Math");
        course.addGrade(90.0, 1);
        course.addGrade(80.0, 2);
        assertEquals(83.33, course.calculateCurrentGrade(), 0.01);
    }

    @Test
    void testCalculateCurrentGradeNoGrades() {
        Course course = new Course("Math");
        assertEquals(0, course.calculateCurrentGrade());
    }

    @Test
    void testCalculateCurrentGradeSingleGrade() {
        // To ensure full coverage of loop scenarios (just one iteration)
        Course course = new Course("Math");
        course.addGrade(75.0, 1);
        assertEquals(75.0, course.calculateCurrentGrade(), 0.01);
    }

    @Test
    void testCalculateCurrentGradeWithZeroWeight() {
        Course course = new Course("Math");
        course.addGrade(90.0, 0);
        assertEquals(0, course.calculateCurrentGrade());
    }

    @Test
    void testCalculateCurrentGradeMultipleZeroWeights() {
        Course course = new Course("Math");
        course.addGrade(100.0, 0);
        course.addGrade(90.0, 0);
        assertEquals(0, course.calculateCurrentGrade());
    }

    @Test
    void testCalculateCurrentGradeMixedPositiveAndNegativeWeights() {
        Course course = new Course("Math");
        course.addGrade(100.0, 2);
        course.addGrade(50.0, 1);
        assertEquals(83.33, course.calculateCurrentGrade(), 0.01);
    }

    @Test
    void testCalculateCurrentGradeNegativeWeights() {
        Course course = new Course("Math");
        course.addGrade(80.0, -1);
        course.addGrade(100.0, 2);
        assertEquals(120.0, course.calculateCurrentGrade(), 0.01);
    }

    @Test
    void testHasAtLeastFiveGradesExactlyFive() {
        Course course = new Course("Math");
        for (int i = 0; i < 5; i++) {
            course.addGrade(80.0 + i, 1);
        }
        assertTrue(course.hasAtLeastFiveGrades());
    }

    @Test
    void testHasAtLeastFiveGradesLessThanFive() {
        Course course = new Course("Math");
        course.addGrade(90.0, 1);
        course.addGrade(80.0, 1);
        course.addGrade(70.0, 1);
        assertFalse(course.hasAtLeastFiveGrades());
    }

    @Test
    void testHasAtLeastFiveGradesMoreThanFive() {
        Course course = new Course("Math");
        for (int i = 0; i < 6; i++) {
            course.addGrade(70.0 + i, 1);
        }
        assertTrue(course.hasAtLeastFiveGrades());
        assertEquals(6, course.getNumberOfGrades());
    }

    @Test
    void testCalculateRequiredFinalScoreForAStandardCase() {
        Course course = new Course("Math");
        course.addGrade(90.0, 1);
        course.addGrade(80.0, 1);
        course.addGrade(70.0, 1);
        course.addGrade(60.0, 1);
        course.addGrade(50.0, 1);
        assertEquals(110.0, course.calculateRequiredFinalScoreForA(), 0.01);
    }

    @Test
    void testCalculateRequiredFinalScoreForAWithException() {
        Course course = new Course("Math");
        course.addGrade(90.0, 1);
        course.addGrade(80.0, 1);
        course.addGrade(70.0, 1);
        assertThrows(IllegalStateException.class, course::calculateRequiredFinalScoreForA);
    }

    @Test
    void testCalculateRequiredFinalScoreForAAlreadyAboveA() {
        Course course = new Course("Math");
        for (int i = 0; i < 5; i++)
            course.addGrade(95.0, 1);
        assertEquals(85.0, course.calculateRequiredFinalScoreForA(), 0.01);
    }

    @Test
    void testCalculateRequiredFinalScoreForAExactly90Average() {
        Course course = new Course("Physics");
        for (int i = 0; i < 5; i++)
            course.addGrade(90.0, 1);
        assertEquals(90.0, course.calculateRequiredFinalScoreForA(), 0.01);
    }

    @Test
    void testCalculateRequiredFinalScoreForAHighAverage92() {
        Course course = new Course("Literature");
        for (int i = 0; i < 5; i++)
            course.addGrade(92.0, 1);
        assertEquals(88.0, course.calculateRequiredFinalScoreForA(), 0.01);
    }

    @Test
    void testCalculateRequiredFinalScoreForAAverage85() {
        Course course = new Course("Biology");
        for (int i = 0; i < 5; i++)
            course.addGrade(85.0, 1);
        assertEquals(95.0, course.calculateRequiredFinalScoreForA(), 0.01);
    }

    @Test
    void testCalculateRequiredFinalScoreForAImpossible() {
        Course course = new Course("History");
        for (int i = 0; i < 5; i++)
            course.addGrade(50.0, 1);
        assertTrue(course.calculateRequiredFinalScoreForA() > 100);
    }

    @Test
    void testCalculateRequiredFinalScoreForANegativeNeeded() {
        Course course = new Course("Philosophy");
        for (int i = 0; i < 5; i++)
            course.addGrade(200.0, 1);
        assertTrue(course.calculateRequiredFinalScoreForA() < 0);
    }

    @Test
    void testCalculateRequiredFinalScoreForAExactlyNoEffortNeeded() {
        Course course = new Course("Sociology");
        for (int i = 0; i < 5; i++)
            course.addGrade(180.0, 1);
        assertEquals(0.0, course.calculateRequiredFinalScoreForA(), 0.01);
    }

    @Test
    void testCalculateRequiredFinalScoreForAMoreThanFiveGrades() {
        Course course = new Course("ExtraCourse");
        for (int i = 0; i < 6; i++)
            course.addGrade(85.0, 1);
        double req = course.calculateRequiredFinalScoreForA();
        assertTrue(req > 0 && req < 100);
    }

    @Test
    void testCalculateRequiredFinalScoreForAMoreThanFiveNegativeNeeded() {
        Course course = new Course("LangArts");
        for (int i = 0; i < 6; i++)
            course.addGrade(200.0, 1);
        assertTrue(course.calculateRequiredFinalScoreForA() < 0);
    }

    @Test
    void testCalculateGPA() {
        Course course = new Course("Math");
        // avg 80 => B- = 2.7
        course.addGrade(90.0, 1);
        course.addGrade(80.0, 1);
        course.addGrade(70.0, 1);
        assertEquals(2.7, course.calculateGPA(), 0.01);
    }

    @Test
    void testCalculateGPAWithHighGrade() {
        Course course = new Course("Math");
        course.addGrade(97.0, 1);
        assertEquals(4.25, course.calculateGPA(), 0.01);
    }

    @Test
    void testCalculateGPAWithPerfectScore() {
        Course course = new Course("Math");
        course.addGrade(100.0, 1);
        assertEquals(4.25, course.calculateGPA(), 0.01);
    }

    @Test
    void testCalculateGPAWithLowGrade() {
        Course course = new Course("Math");
        course.addGrade(60.0, 1);
        assertEquals(0.7, course.calculateGPA(), 0.01);
    }

    @Test
    void testCalculateGPAWithNoGrades() {
        Course course = new Course("Math");
        assertEquals(0.0, course.calculateGPA(), 0.01);
    }

    @Test
    void testGPAInsideAllRanges() {
        assertEquals(4.25, gpaForSingleGrade(99.0), 0.01); // A+
        assertEquals(4.0, gpaForSingleGrade(95.0), 0.01);  // A
        assertEquals(3.7, gpaForSingleGrade(91.0), 0.01);  // A-
        assertEquals(3.3, gpaForSingleGrade(88.0), 0.01);  // B+
        assertEquals(3.0, gpaForSingleGrade(85.0), 0.01);  // B
        assertEquals(2.7, gpaForSingleGrade(81.0), 0.01);  // B-
        assertEquals(2.3, gpaForSingleGrade(78.0), 0.01);  // C+
        assertEquals(2.0, gpaForSingleGrade(75.0), 0.01);  // C
        assertEquals(1.7, gpaForSingleGrade(71.0), 0.01);  // C-
        assertEquals(1.3, gpaForSingleGrade(68.0), 0.01);  // D+
        assertEquals(1.0, gpaForSingleGrade(64.0), 0.01);  // D
        assertEquals(0.7, gpaForSingleGrade(60.0), 0.01);  // D-
        assertEquals(0.0, gpaForSingleGrade(50.0), 0.01);  // F
    }

    @Test
    void testGPAAtExactBoundaries() {
        assertEquals(4.0, gpaForSingleGrade(93.0), 0.01);
        assertEquals(3.7, gpaForSingleGrade(90.0), 0.01);
        assertEquals(3.3, gpaForSingleGrade(87.0), 0.01);
        assertEquals(3.0, gpaForSingleGrade(83.0), 0.01);
        assertEquals(2.7, gpaForSingleGrade(80.0), 0.01);
        assertEquals(2.3, gpaForSingleGrade(77.0), 0.01);
        assertEquals(2.0, gpaForSingleGrade(73.0), 0.01);
        assertEquals(1.7, gpaForSingleGrade(70.0), 0.01);
        assertEquals(1.3, gpaForSingleGrade(67.0), 0.01);
        assertEquals(1.0, gpaForSingleGrade(63.0), 0.01);
        assertEquals(0.7, gpaForSingleGrade(60.0), 0.01);
        assertEquals(0.0, gpaForSingleGrade(59.9), 0.01);
    }

    @Test
    void testGetCourseName() {
        Course course = new Course("Math");
        assertEquals("Math", course.getCourseName());
    }

    @Test
    void testNegativeGradesAndWeights() {
        Course course = new Course("Math");
        course.addGrade(-10.0, 1);
        course.addGrade(100.0, -1);
        assertEquals(0.0, course.calculateCurrentGrade(), 0.01);
    }

    // ---------------- GradeTracker Tests ----------------

    @Test
    void testAddCourseToTracker() {
        GradeTracker tracker = new GradeTracker();
        tracker.addCourse("Math");
        assertTrue(tracker.courseExists("Math"));
    }

    @Test
    void testCourseDoesNotExist() {
        GradeTracker tracker = new GradeTracker();
        tracker.addCourse("Math");
        assertFalse(tracker.courseExists("Science"));
    }

    @Test
    void testAddCourseWithGradesAndWeightsToTracker() {
        GradeTracker tracker = new GradeTracker();
        List<Double> grades = Arrays.asList(90.0, 80.0, 70.0);
        List<Integer> weights = Arrays.asList(1, 1, 1);
        tracker.addCourse("Math", grades, weights);
        Course course = tracker.getCourse("Math");
        assertEquals(3, course.getNumberOfGrades());
    }

    @Test
    void testAddCourseWithMismatchedGradesAndWeights() {
        GradeTracker tracker = new GradeTracker();
        List<Double> grades = Arrays.asList(90.0, 80.0);
        List<Integer> weights = Arrays.asList(1);
        assertThrows(IllegalArgumentException.class, () -> tracker.addCourse("Math", grades, weights));
    }

    @Test
    void testAddGradeToNonExistentCourse() {
        GradeTracker tracker = new GradeTracker();
        assertThrows(IllegalArgumentException.class, () -> tracker.addGrade("Math", 90.0, 1));
    }

    @Test
    void testCalculateGPAWithCourses() {
        GradeTracker tracker = new GradeTracker();
        List<Double> grades = Arrays.asList(90.0, 80.0, 70.0);
        List<Integer> weights = Arrays.asList(1, 1, 1);
        tracker.addCourse("Math", grades, weights);
        assertEquals(2.7, tracker.calculateGPA(), 0.01);
    }

    @Test
    void testCalculateGPAWithMultipleCourses() {
        GradeTracker tracker = new GradeTracker();

        // Math: (90+80)/2=85% => B=3.0
        List<Double> grades1 = Arrays.asList(90.0, 80.0);
        List<Integer> weights1 = Arrays.asList(1, 1);
        tracker.addCourse("Math", grades1, weights1);

        // English: (100+100)/2=100% => A+=4.25
        List<Double> grades2 = Arrays.asList(100.0, 100.0);
        List<Integer> weights2 = Arrays.asList(1, 1);
        tracker.addCourse("English", grades2, weights2);

        double expected = (3.0 + 4.25) / 2;
        assertEquals(expected, tracker.calculateGPA(), 0.001);
    }

    @Test
    void testCalculateGPAWithNoCourses() {
        GradeTracker tracker = new GradeTracker();
        assertEquals(0.0, tracker.calculateGPA(), 0.01);
    }

    @Test
    void testGetCoursesWithFewerThanFiveGrades() {
        GradeTracker tracker = new GradeTracker();
        List<Double> grades = Arrays.asList(90.0, 80.0);
        List<Integer> weights = Arrays.asList(1, 1);
        tracker.addCourse("Math", grades, weights);
        List<String> result = tracker.getCoursesWithFewerThanFiveGrades();
        assertTrue(result.contains("Math"));
    }

    @Test
    void testGetCoursesWithFewerThanFiveGradesNone() {
        GradeTracker tracker = new GradeTracker();
        assertTrue(tracker.getCoursesWithFewerThanFiveGrades().isEmpty());
    }

    @Test
    void testGetCoursesWithFewerThanFiveGradesAllHaveFive() {
        GradeTracker tracker = new GradeTracker();
        List<Double> grades = Arrays.asList(90.0, 91.0, 92.0, 93.0, 94.0);
        List<Integer> weights = Arrays.asList(1, 1, 1, 1, 1);
        tracker.addCourse("Math", grades, weights);
        List<String> result = tracker.getCoursesWithFewerThanFiveGrades();
        assertTrue(result.isEmpty());
    }

    @Test
    void testCalculateRequiredFinalForAInTracker() {
        GradeTracker tracker = new GradeTracker();
        List<Double> grades = Arrays.asList(90.0, 80.0, 70.0, 60.0, 50.0);
        List<Integer> weights = Arrays.asList(1, 1, 1, 1, 1);
        tracker.addCourse("Math", grades, weights);
        assertEquals(110.0, tracker.calculateRequiredFinalForA("Math"), 0.01);
    }

    @Test
    void testCalculateRequiredFinalForANonExistentCourse() {
        GradeTracker tracker = new GradeTracker();
        assertThrows(IllegalArgumentException.class, () -> tracker.calculateRequiredFinalForA("History"));
    }

    /**
     * Helper method for GPA tests on a single grade
     */
    private double gpaForSingleGrade(double grade) {
        Course c = new Course("Test");
        c.addGrade(grade, 1);
        return c.calculateGPA();
    }
}