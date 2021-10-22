package seedu.plannermd.logic.commands.apptcommand;

import seedu.plannermd.commons.core.Messages;
import seedu.plannermd.commons.core.index.Index;
import seedu.plannermd.logic.commands.CommandResult;
import seedu.plannermd.logic.commands.exceptions.CommandException;
import seedu.plannermd.model.Model;
import seedu.plannermd.model.appointment.Appointment;
import seedu.plannermd.model.appointment.AppointmentDate;
import seedu.plannermd.model.appointment.Session;
import seedu.plannermd.model.doctor.Doctor;
import seedu.plannermd.model.patient.Patient;
import seedu.plannermd.model.person.Remark;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.plannermd.logic.parser.CliSyntax.FLAG_ADD;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_DOCTOR;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_PATIENT;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_START;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.plannermd.logic.parser.CliSyntax.PREFIX_DURATION;

public class AddAppointmentCommand extends AppointmentCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + FLAG_ADD + ": Adds an appointment to PlannerMD "
            + "Parameters: " + PREFIX_PATIENT + "INDEX_OF_PATIENT " + PREFIX_DOCTOR + "INDEX_OF_DOCTOR "
            + PREFIX_START + "DATE_AND_TIME" + "[" + PREFIX_DURATION + "DURATION" + "] "+ "["
            + PREFIX_REMARK + " REMARK" + "]\n" + "Example: " + COMMAND_WORD + " " + FLAG_ADD + " " + PREFIX_PATIENT + "1 "
            + PREFIX_DOCTOR + "2 " + PREFIX_START + "2/2/2022 12:00 "
            + PREFIX_DURATION + "45 " + PREFIX_REMARK + "Patient wants a blood test";


    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_CONFLICTING_APPOINTMENT
            = "This appointment cannot be added due to a clash in timings";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT
            = "This appointment already exists in PlannerMD";
    public static final String MESSAGE_WRONG_DATE_TIME = "Sessions should be of  the format DD/MM/YYYY HH:MM "
            + "and adhere to the following constraints:\n"
            + "1. Must be a valid date (after now)\n"
            + "2. Day must be between 1-31 (0 in front of single digit is optional)\n"
            + "3. Month must be between 1-12 (0 in front of single digit is optional)\n"
            + "4. Year must be 4 characters."
            + "5. Must be a valid time (after now if the given date is the current date)\n"
            + "6. Hour must be between 0-23 (0 in front of single digit is optional)\n"
            + "7. Minute must be between 0-59 (0 in front of single digit is optional).";

    Index patientIndex;
    Index doctorIndex;
    AddAppointmentDescriptor addAppointmentDescriptor;
    Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}.
     */
    public AddAppointmentCommand(Index patientIndex, Index doctorIndex, AddAppointmentDescriptor addAppointmentDescriptor) {
        requireNonNull(patientIndex);
        requireNonNull(doctorIndex);
        requireNonNull(addAppointmentDescriptor);

        this.patientIndex = patientIndex;
        this.doctorIndex = doctorIndex;
        this.addAppointmentDescriptor = new AddAppointmentDescriptor(addAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Patient> patientList = model.getFilteredPatientList();
        List<Doctor> doctorList = model.getFilteredDoctorList();
        if (patientIndex.getZeroBased() >= patientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }

        if (doctorIndex.getZeroBased() >= doctorList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }

        Patient patientToAdd = patientList.get(patientIndex.getZeroBased());
        Doctor doctorToAdd = doctorList.get(doctorIndex.getZeroBased());

        Appointment toAdd = new Appointment(patientToAdd, doctorToAdd, addAppointmentDescriptor.getAppointmentDate(),
                addAppointmentDescriptor.getSession(), addAppointmentDescriptor.getRemark());

        this.toAdd = toAdd;

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        if (model.isClashAppointment(toAdd)) {
            throw new CommandException(MESSAGE_CONFLICTING_APPOINTMENT);
        }

        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd));
    }

    /**
     * Stores the details of Appointment less patient and doctor.
     */
    public static class AddAppointmentDescriptor {
        private AppointmentDate appointmentDate;
        private Session session;
        private Remark remark;

        public AddAppointmentDescriptor() {
        }

        /**
         * Copy constructor.
         */
        public AddAppointmentDescriptor(AddAppointmentCommand.AddAppointmentDescriptor toCopy) {
            setAppointmentDate(toCopy.appointmentDate);
            setSession(toCopy.session);
            setRemark(toCopy.remark);
        }

        public void setAppointmentDate(AppointmentDate appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public AppointmentDate getAppointmentDate() {
            return appointmentDate;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public Session getSession() {
            return session;
        }
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Remark getRemark() {
            return remark;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddAppointmentCommand.AddAppointmentDescriptor)) {
                return false;
            }

            // state check
            AddAppointmentCommand.AddAppointmentDescriptor e = (AddAppointmentCommand.AddAppointmentDescriptor) other;

            return getSession().equals(e.getSession())
                    && getAppointmentDate().equals(e.getAppointmentDate())
                    && getRemark().equals(e.getRemark());
        }
    }
}