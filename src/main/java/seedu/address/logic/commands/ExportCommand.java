package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.delivery.DeliveryAssignmentHashMap;
import seedu.address.model.delivery.ExportUtil;

import java.io.File;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Exported delivery assignments to file.";
    public static final String MESSAGE_FAILURE = "Failed to export delivery assignments.";

    private static final String DEFAULT_DIR = "data";
    private static final String DEFAULT_FILENAME = "delivery_assignments.txt";

    private final String filePath;

    /**
     * Creates an ExportCommand with a specified file path.
     * If null or empty, uses the default path.
     *
     * @param filePath the file path to export to
     */
    public ExportCommand(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            // Ensure the data folder exists
            File dir = new File(DEFAULT_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            this.filePath = DEFAULT_DIR + File.separator + DEFAULT_FILENAME;
        } else {
            this.filePath = filePath;
        }
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            ExportUtil.exportAssignmentsFormatted(
                    DeliveryAssignmentHashMap.getAssignments(),
                    filePath
            );
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE + " " + e.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}