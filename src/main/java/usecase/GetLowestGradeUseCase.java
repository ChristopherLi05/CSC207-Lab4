package usecase;

import api.GradeDataBase;
import entity.Grade;
import entity.Team;

import java.util.Map;

/**
 * GetLowestGradeUseCase class
 */
public final class GetLowestGradeUseCase {
    private static class Pair<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        public K setKey(K key) {
            this.key = key;
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }

    private final GradeDataBase gradeDataBase;

    public GetLowestGradeUseCase(GradeDataBase gradeDataBase) {
        this.gradeDataBase = gradeDataBase;
    }

    /**
     * Get the highest grade for a course across your team.
     * @param course The course.
     * @return The name of the person with lowest grade and their grade
     */
    public Map.Entry<String, Float> getLowestGrade(String course) {
        // Call the API to get the usernames of all your team members
        String person = null;
        float min = 0;

        final Team team = gradeDataBase.getMyTeam();
        // Call the API to get all the grades for the course for all your team members
        for (String username : team.getMembers()) {
            final Grade[] grades = gradeDataBase.getGrades(username);
            for (Grade grade : grades) {
                if (grade.getCourse().equals(course)) {
                    if (grade.getGrade() < min || person == null) {
                        min = grade.getGrade();
                        person = username;
                    }
                }
            }
        }
        return new Pair<>(person, min);
    }
}
