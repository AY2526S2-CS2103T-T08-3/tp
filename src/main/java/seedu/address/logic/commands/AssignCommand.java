package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

class Driver {

}

public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_DUPLICATE_DRIVER = "There are duplicate drivers!";
    public static final String MESSAGE_SUCCESS = "Drivers added and assigned successfully!";

    private Driver[] drivers;

    /**
     * Creates an AssignCommand to tag all {@code Person}s to a {@code Driver}
     */
    public AssignCommand(Driver... inputDrivers) throws CommandException {
        for (int i = 0; i < inputDrivers.length; i++) {
            Driver toAdd = inputDrivers[i];
            if (inputHasDuplicate(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_DRIVER);
            } else {
                drivers[i] = toAdd;
            }

        }
    }

    private boolean inputHasDuplicate(Driver toAdd) {
        for (Driver d : drivers) {
            if (d.equals(toAdd)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // TODO: UniqueDriverList in AddressBook with methods to check Drivers
        if (model.hasDriver(drivers)) {
            throw new CommandException(MESSAGE_DUPLICATE_DRIVER);
        }

        model.addDrivers(drivers);
        return new CommandResult(MESSAGE_SUCCESS);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AssignCommand)) {
            return false;
        }

        AssignCommand otherAssignCommand = (AssignCommand) other;
        return Arrays.equals(drivers, otherAssignCommand.drivers);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAssignDrivers", drivers)
                .toString();
    }
}
