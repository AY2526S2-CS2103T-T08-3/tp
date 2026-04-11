package seedu.address.logic.commands.util;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.model.person.Box;
import seedu.address.model.person.Person;

/**
 * Contains utility methods used in the execution of box commands.
 */
public class BoxUtil {

    /**
     * Returns the set of box names of {@code person}
     */
    public static Set<String> getBoxNames(Person person) {
        return person.getBoxes().stream()
                .map(Box::getBoxName)
                .collect(Collectors.toSet());
    }

    /**
     * Returns true if at least one box from {@code person} and {@code boxesToAdd} have the same name.
     */
    public static boolean hasMatchingBoxNames(Person person, Set<Box> boxesToAdd) {
        Set<String> existingBoxNames = getBoxNames(person);
        return boxesToAdd.stream()
                .map(Box::getBoxName)
                .anyMatch(existingBoxNames::contains);
    }

    /**
     * Returns the names of boxes present in {@code targetBoxNames} but not in {@code person}.
     */
    public static Set<String> getNonExistentBoxNames(Person person, Set<String> targetBoxNames) {
        Set<String> existingBoxNames = getBoxNames(person);
        return targetBoxNames.stream()
                .filter(boxName -> !existingBoxNames.contains(boxName))
                .collect(Collectors.toSet());
    }

    /**
     * Returns the updated set of boxes of {@code person} after adding boxes from {@code boxesToAdd}.
     */
    public static Set<Box> addBoxes(Person person, Set<Box> boxesToAdd) {
        Set<Box> updatedBoxes = new TreeSet<>(person.getBoxes());
        updatedBoxes.addAll(boxesToAdd);
        return updatedBoxes;
    }

    /**
     * Removes boxes from {@code person} with names that are present in {@code targetBoxNames} and returns the result.
     */
    public static Set<Box> removeBoxes(Person person, Set<String> targetBoxNames) {
        return person.getBoxes().stream()
                .filter(box -> !targetBoxNames.contains(box.boxName))
                .collect(Collectors.toSet());
    }
}
