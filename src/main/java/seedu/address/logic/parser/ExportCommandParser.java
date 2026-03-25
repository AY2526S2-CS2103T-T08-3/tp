package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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