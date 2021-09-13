package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

import static java.util.Objects.requireNonNull;

public class RemarkCommandParser implements Parser<RemarkCommand> {

    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap multimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(multimap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), e);
        }

        Remark remark = new Remark(multimap.getValue(CliSyntax.PREFIX_REMARK).orElse(""));

        return new RemarkCommand(index, remark);
    }
}
