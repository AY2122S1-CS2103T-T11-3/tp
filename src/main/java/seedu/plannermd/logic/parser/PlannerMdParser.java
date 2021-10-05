package seedu.plannermd.logic.parser;

import static seedu.plannermd.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.plannermd.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.plannermd.logic.commands.ClearCommand;
import seedu.plannermd.logic.commands.Command;
import seedu.plannermd.logic.commands.ExitCommand;
import seedu.plannermd.logic.commands.HelpCommand;
import seedu.plannermd.logic.commands.addcommand.AddPatientCommand;
import seedu.plannermd.logic.commands.deletecommand.DeletePatientCommand;
import seedu.plannermd.logic.commands.editcommand.EditPatientCommand;
import seedu.plannermd.logic.commands.findcommand.FindPatientCommand;
import seedu.plannermd.logic.commands.listcommand.ListPatientCommand;
import seedu.plannermd.logic.commands.tagcommand.AddPatientTagCommand;
import seedu.plannermd.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PlannerMdParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddPatientCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditPatientCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeletePatientCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindPatientCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListPatientCommand.COMMAND_WORD:
            return new ListPatientCommand();

        case AddPatientTagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
