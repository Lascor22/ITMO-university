package ru.ifmo.rain.karimov.student;

import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentGroupQuery;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentGroupQuery {
    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getGroups(students, studentNameComparator);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getGroups(students, Student::compareTo);
    }

    @Override
    public String getLargestGroup(Collection<Student> students) {
        return getLargestGroupBy(students, Comparator.comparingInt(List::size));
    }

    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return getLargestGroupBy(students, Comparator.comparingInt(list -> getDistinctFirstNames(list).size()));
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getFromStudentListBy(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getFromStudentListBy(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return getFromStudentListBy(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getFromStudentListBy(students, student -> student.getFirstName() + " " + student.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new TreeSet<>(getFirstNames(students));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students.stream().min(Student::compareTo).map(Student::getFirstName).orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStudentsBy(students, Student::compareTo);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStudentsBy(students, studentNameComparator);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, final String firstName) {
        return findBy(students, firstName, Student::getFirstName);
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, final String lastName) {
        return findBy(students, lastName, Student::getLastName);
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, final String group) {
        return findBy(students, group, Student::getGroup);
    }

    private static final Comparator<? super Student> studentNameComparator = Comparator
            .comparing(Student::getLastName, String::compareTo)
            .thenComparing(Student::getFirstName, String::compareTo)
            .thenComparing(Student::compareTo);

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, final String group) {
        return findBy(students, group, Student::getGroup).stream()
                .collect(Collectors.groupingBy(Student::getLastName, Collectors.toList())).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                        .map(Student::getFirstName).min(String.CASE_INSENSITIVE_ORDER).orElse("")));
    }

    private String getLargestGroupBy(Collection<Student> students, Comparator<List<Student>> comparator) {
        return getGroupEntryStreamFromStudents(students).
                max(Map.Entry.<String, List<Student>>comparingByValue(comparator)
                        .thenComparing(Map.Entry.comparingByKey(Collections.reverseOrder(String::compareTo))))
                .map(Map.Entry::getKey)
                .orElse("");
    }

    private List<Group> getGroups(Collection<Student> students, Comparator<? super Student> comparator) {
        return getGroupEntryStreamFromStudents(sortStudentsBy(students, comparator))
                .map(entry -> new Group(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(Group::getName))
                .collect(Collectors.toList());
    }

    private Stream<Map.Entry<String, List<Student>>> getGroupEntryStreamFromStudents(Collection<Student> students) {
        return students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, LinkedHashMap::new, Collectors.toList()))
                .entrySet().stream();
    }

    private List<String> getFromStudentListBy(List<Student> students, Function<? super Student, String> getField) {
        return students.stream().map(getField).collect(Collectors.toList());
    }

    private List<Student> sortStudentsBy(Collection<Student> students, Comparator<? super Student> comparator) {
        return students.stream().sorted(comparator).collect(Collectors.toList());
    }

    private List<Student> findBy(Collection<Student> students, final String name, final Function<Student, String> getField) {
        return students.stream().filter(student -> getField.apply(student).equals(name))
                .sorted(studentNameComparator).collect(Collectors.toList());
    }
}
