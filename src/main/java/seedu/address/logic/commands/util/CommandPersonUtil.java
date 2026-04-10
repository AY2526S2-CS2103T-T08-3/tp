package seedu.address.logic.commands.util;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.commons.name.Name;
import seedu.address.model.delivery.Driver;
import seedu.address.model.person.Box;
import seedu.address.model.person.Person;

/**
 * Contains utility methods for command classes that operate on the Person class.
 */
public class CommandPersonUtil {

    /**
     * Finds a person by name if it exists.
     */
    public static Optional<Person> findPersonByName(List<Person> persons, Name name) {
        return persons.stream()
                .filter(person -> person.getName().equals(name))
                .findFirst();
    }

    /**
     * Sets {@code person}'s boxes to {@code updatedBoxes}.
     */
    public static Person withBoxes(Person person, Set<Box> updatedBoxes) {
        assert person != null;
        assert updatedBoxes != null;

        Driver driver = person.hasDriver() ? person.getAssignedDriver() : null;
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), updatedBoxes,
                person.getRemark(), person.getDeliveryStatus(), person.getTags(), driver);
    }
}
