package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.commons.phone.Phone;
import seedu.address.model.person.Address;
import seedu.address.model.person.Box;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ImportUtil;

/**
 * Imports subscribers from a CSV file and adds them to the model using AddCommand.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Imported %d/%d subscribers successfully.";
    public static final String MESSAGE_FAILURE = "Import failed: %s";

    private final String filePath;

    /**
     * Creates an ImportCommand to import subscribers from the specified CSV file path.
     *
     * @param filePath The path to the CSV file to be imported. Must not be null.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    /**
     * Executes the import command by reading subscriber data from a CSV file and adding valid entries
     * to the model.
     * <p>
     * Each row in the CSV file is parsed into a {@code Person} object. Invalid or duplicate rows
     * are skipped silently. The total number of successfully added subscribers is returned in the result.
     *
     * @param model The model which the imported subscribers will be added to. Must not be null.
     * @return A {@code CommandResult} containing a success message with the number of imported subscribers.
     * @throws CommandException If the CSV file cannot be read or parsed.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<String[]> rows;
        try {
            rows = ImportUtil.parseCsv(filePath);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
        }

        int addedCount = 0;
        List<String> failedImports = new ArrayList<>();

        for (String[] row : rows) {
            try {
                Name name = new Name(row[1].trim());
                Phone phone = new Phone(row[2].trim());
                Email email = new Email(row[3].trim());
                Address address = new Address(row[4].trim());
                ExpiryDate expiryDate = new ExpiryDate(row[6].trim());
                Box box = new Box(row[5].trim(), expiryDate);
                Set<Box> boxes = new TreeSet<>();
                boxes.add(box);
                String remarkStr = row[7].trim();
                Remark remark = remarkStr.isEmpty() ? new Remark(Remark.DEFAULT_REMARK) : new Remark(remarkStr);
                Set<Tag> tags = parseTags(row[8]);

                Person person = new Person(name, phone, email, address, boxes, remark, expiryDate, tags);

                new AddCommand(person).execute(model);
                addedCount++;

            } catch (Exception e) {
                String rowSummary = String.format("Name: %s | Phone: %s | Email: %s | Address: %s | "
                                + "Boxes: %s | Expiry: %s | Remark: %s | Tags: %s",
                        row.length > 1 ? row[1].trim() : "?",
                        row.length > 2 ? row[2].trim() : "?",
                        row.length > 3 ? row[3].trim() : "?",
                        row.length > 4 ? row[4].trim() : "?",
                        row.length > 5 ? row[5].trim() : "?",
                        row.length > 6 ? row[6].trim() : "?",
                        row.length > 7 ? row[7].trim() : "?",
                        row.length > 8 ? row[8].trim() : "?");
                failedImports.add(String.format("  - %s\n     Reason: %s\n", rowSummary, e.getMessage()));
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format(MESSAGE_SUCCESS, addedCount, rows.size()));

        if (!failedImports.isEmpty()) {
            result.append(String.format("\n\n%d row(s) failed to import:\n", failedImports.size()));
            failedImports.forEach(msg -> result.append(msg).append("\n"));
        }

        return new CommandResult(result.toString().trim());
    }

    private Set<Tag> parseTags(String tagsStr) {
        Set<Tag> tags = new TreeSet<>();
        if (tagsStr != null && !tagsStr.isEmpty()) {
            String[] arr = tagsStr.split(",\\s*");
            for (String t : arr) {
                tags.add(new Tag(t.trim()));
            }
        }
        return tags;
    }
}
