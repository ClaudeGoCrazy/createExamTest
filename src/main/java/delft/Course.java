package delft;

import java.util.ArrayList;
import java.util.List;

// Represents a course and its grades
class Course {
    private String courseName;
    private List<Double> grades;
    private List<Integer> weights;

    public Course(String courseName) {
        this.courseName = courseName;
        this.grades = new ArrayList<>();
        this.weights = new ArrayList<>();
    }

    public void addGrade(double grade, int weight) {
        grades.add(grade);
        weights.add(weight);
    }

    public double calculateCurrentGrade() {
        double totalWeightedScore = 0;
        double totalWeights = 0;

        for (int i = 0; i < grades.size(); i++) {
            totalWeightedScore += grades.get(i) * weights.get(i);
            totalWeights += weights.get(i);
        }

        return totalWeights == 0 ? 0 : totalWeightedScore / totalWeights;
    }

    public boolean hasAtLeastFiveGrades() {
        return grades.size() >= 5;
    }

    public int getNumberOfGrades() {
        return grades.size();
    }

    public String getCourseName() {
        return courseName;
    }

    public double calculateRequiredFinalScoreForA() {
        if (grades.size() < 5) {
            throw new IllegalStateException("Need at least 5 grades to calculate required final score.");
        }

        double currentGrade = calculateCurrentGrade();
        double currentWeightedTotal = currentGrade * (grades.size() * 1.0); // Normalized weight so far
        double requiredTotal = 90 * (grades.size() + 5); // Target for an A, with final worth 5X

        return (requiredTotal - currentWeightedTotal) / 5; // Weight of final is 5X
    }

    public double calculateGPA() {
        double percentageGrade = calculateCurrentGrade();
        return convertPercentageToGPA(percentageGrade);
    }

    private double convertPercentageToGPA(double percentage) {
        if (percentage >= 97) return 4.25; // A+
        if (percentage >= 93) return 4.0; // A
        if (percentage >= 90) return 3.7; // A-
        if (percentage >= 87) return 3.3; // B+
        if (percentage >= 83) return 3.0; // B
        if (percentage >= 80) return 2.7; // B-
        if (percentage >= 77) return 2.3; // C+
        if (percentage >= 73) return 2.0; // C
        if (percentage >= 70) return 1.7; // C-
        if (percentage >= 67) return 1.3; // D+
        if (percentage >= 63) return 1.0; // D
        if (percentage >= 60) return 0.7; // D-
        return 0.0; // F
    }

    public List<Double> getGrades() {
        return grades;
    }
}