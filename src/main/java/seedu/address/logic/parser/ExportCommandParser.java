package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link ExportCommand} object.
 * <p>
 * The parser expects a single argument specifying the file path where
 * the delivery assignments will be exported. If no file path is provided,
 * a {@link ParseException} is thrown.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmed = args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException("File path required for export.");
        }
        return new ExportCommand(trimmed);
    }
}
