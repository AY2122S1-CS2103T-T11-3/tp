package seedu.plannermd.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.plannermd.commons.core.index.Index;
import seedu.plannermd.logic.commands.addcommand.AddDoctorCommand;
import seedu.plannermd.logic.commands.apptcommand.AddAppointmentCommand;
import seedu.plannermd.logic.parser.exceptions.ParseException;
import seedu.plannermd.model.appointment.AppointmentDate;
import seedu.plannermd.model.appointment.Duration;
import seedu.plannermd.model.appointment.Session;
import seedu.plannermd.model.person.Remark;

import static seedu.plannermd.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.plannermd.commons.util.AppUtil.checkArgument;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_BIRTH_DATE;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_START;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_DURATION;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

public class AddAppointmentCommandParser  {
    public static final String END_DATE_BEFORE_START_DATE_MESSAGE = "End date cannot be before start date.";
    public static final String NO_ARGUMENTS_MESSAGE = "No arguments provided.\n"
            + AddAppointmentCommand.MESSAGE_USAGE;
    private static final String UNUSED_PREAMBLE = "0";

    /**
     * Parses the given {@code String} of arguments in the context of the FilterAppointmentCommand
     * and returns a AddAppointmentCommand object for execution.
     *
     * @throws ParseException If the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PATIENT, PREFIX_DOCTOR, PREFIX_START,
                PREFIX_DURATION, PREFIX_REMARK);
        if (!arePrefixesPresent(argMultimap, PREFIX_PATIENT, PREFIX_DOCTOR, PREFIX_START)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        AddAppointmentCommand.AddAppointmentDescriptor addAppointmentDescriptor =  new AddAppointmentCommand.AddAppointmentDescriptor();
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        addAppointmentDescriptor.setRemark(remark);
        String trimmedParsedDateTime = argMultimap.getValue(PREFIX_START).get().trim();
        System.out.println(trimmedParsedDateTime);
        String time = getTimeFromDateTime(trimmedParsedDateTime);
        String date = getDateFromDateTime(trimmedParsedDateTime);
        AppointmentDate appointmentDate = new AppointmentDate(date);
        addAppointmentDescriptor.setAppointmentDate(appointmentDate);
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Session session = new Session(time,duration);
        addAppointmentDescriptor.setSession(session);
        Index patientIndex;
        Index doctorIndex;
        try {
            patientIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_PATIENT).get());
            doctorIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DOCTOR).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE),
                    pe);
        }

        return new AddAppointmentCommand(patientIndex, doctorIndex, addAppointmentDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     *
     * @return boolean
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private final DateTimeFormatter fmt = new DateTimeFormatterBuilder()
            .appendPattern("d/M/yyyy")
            .appendPattern(" HH:mm")
            .toFormatter();

    private String getTimeFromDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        try {
            LocalTime inputTime = LocalDateTime.parse(dateTime, fmt).toLocalTime();
            return inputTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            throw new ParseException(AddAppointmentCommand.MESSAGE_WRONG_DATE_TIME);
        }
    }

    private String getDateFromDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        try {
            LocalDate inputDate = LocalDateTime.parse(dateTime, fmt).toLocalDate();
            return inputDate.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
        } catch (DateTimeParseException e) {
            throw new ParseException(AddAppointmentCommand.MESSAGE_WRONG_DATE_TIME);
        }
    }
}