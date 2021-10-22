package seedu.plannermd.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.plannermd.commons.core.index.Index;
import seedu.plannermd.commons.util.StringUtil;
import seedu.plannermd.logic.parser.exceptions.ParseException;
import seedu.plannermd.model.appointment.AppointmentDate;
import seedu.plannermd.model.appointment.Duration;
import seedu.plannermd.model.appointment.Session;
import seedu.plannermd.model.patient.Risk;
import seedu.plannermd.model.person.Address;
import seedu.plannermd.model.person.BirthDate;
import seedu.plannermd.model.person.Email;
import seedu.plannermd.model.person.Name;
import seedu.plannermd.model.person.Phone;
import seedu.plannermd.model.person.Remark;
import seedu.plannermd.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String birthDate} into an {@code BirthDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code birthDate} is invalid.
     */
    public static BirthDate parseBirthDate(String birthDate) throws ParseException {
        requireNonNull(birthDate);
        String trimmedBirthDate = birthDate.trim();
        if (!BirthDate.isValidBirthDate(trimmedBirthDate)) {
            throw new ParseException(BirthDate.MESSAGE_CONSTRAINTS);
        }
        return new BirthDate(trimmedBirthDate);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String risk} into an {@code Risk}.
     * Leading and trailing whitespaces will be trimmed.
     * Optional Risk field accepts an empty string as UNCLASSIFIED.
     *
     * @throws ParseException if the given {@code risk} is invalid.
     */
    public static Risk parseRisk(String risk) throws ParseException {
        requireNonNull(risk);
        String trimmedAndUpperCaseRisk = risk.trim().toUpperCase();
        if (trimmedAndUpperCaseRisk.isEmpty()) {
            return Risk.getUnclassifiedRisk();
        }
        if (!Risk.isValidRisk(trimmedAndUpperCaseRisk)) {
            throw new ParseException(Risk.MESSAGE_CONSTRAINTS);
        }
        return new Risk(trimmedAndUpperCaseRisk);
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     * Optional Risk field accepts an empty string as UNCLASSIFIED.
     *
     * @throws ParseException if the given {@code risk} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (trimmedRemark.isEmpty()) {
            return Remark.getEmptyRemark();
        }
        return new Remark(trimmedRemark);
    }

//    public static AppointmentDate parseStartDate(String startDate) throws ParseException {
//        requireNonNull(startDate);
//        String trimmedStartDate = startDate.trim();
//        if (!AppointmentDate.isValidAppointmentDate(trimmedStartDate)) {
//            throw new ParseException(AppointmentDate.MESSAGE_CONSTRAINTS);
//        }
//        return new AppointmentDate(trimmedStartDate);
//    }

    public static Duration parseDuration(String duration) throws ParseException {
        requireNonNull(duration);
        String trimmedDuration = duration.trim();
        if (trimmedDuration.isEmpty()) {
            return Duration.getDefaultDuration();
        }
        try {
            if (!Duration.isValidDuration(trimmedDuration)) {
                throw new ParseException(Duration.MESSAGE_CONSTRAINTS);
            }
            return new Duration(trimmedDuration);
        } catch (NumberFormatException e) {
            throw new ParseException(Duration.MESSAGE_CONSTRAINTS);
        }
    }
}
