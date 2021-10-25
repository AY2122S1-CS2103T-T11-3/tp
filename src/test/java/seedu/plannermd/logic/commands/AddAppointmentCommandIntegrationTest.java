package seedu.plannermd.logic.commands;

import static seedu.plannermd.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.plannermd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.plannermd.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.plannermd.testutil.TypicalPlannerMd.getTypicalPlannerMd;
import static seedu.plannermd.testutil.doctor.TypicalDoctors.DR_CARL;
import static seedu.plannermd.testutil.patient.TypicalPatients.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.plannermd.commons.core.index.Index;
import seedu.plannermd.logic.commands.apptcommand.AddAppointmentCommand;
import seedu.plannermd.model.Model;
import seedu.plannermd.model.ModelManager;
import seedu.plannermd.model.UserPrefs;
import seedu.plannermd.model.appointment.Appointment;
import seedu.plannermd.model.doctor.Doctor;
import seedu.plannermd.model.patient.Patient;
import seedu.plannermd.testutil.appointment.AddAppointmentDescriptorBuilder;
import seedu.plannermd.testutil.appointment.AppointmentBuilder;

public class AddAppointmentCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPlannerMd(), new UserPrefs());
    }

    @Test
    public void execute_newAppointment_success() {
        Appointment validAppointment = new AppointmentBuilder().withPatient(ALICE).withDoctor(DR_CARL).build();
        Patient patient = validAppointment.getPatient();
        Doctor doctor = validAppointment.getDoctor();
        Index doctorIndex = Index.fromZeroBased(1);
        Index patientIndex = Index.fromOneBased(1);
        for (Patient pt : model.getPlannerMd().getPatientList()) {
            if (pt.equals(patient)) {
                patientIndex = Index.fromZeroBased(model.getPlannerMd().getPatientList().indexOf(pt));
                break;
            }
        }
        for (Doctor dr : model.getPlannerMd().getDoctorList()) {
            if (dr.equals(doctor)) {
                doctorIndex = Index.fromZeroBased(model.getPlannerMd().getDoctorList().indexOf(dr));
                break;
            }
        }
        AddAppointmentCommand.AddAppointmentDescriptor descriptor =
                new AddAppointmentDescriptorBuilder(validAppointment).build();

        Model expectedModel = new ModelManager(model.getPlannerMd(), new UserPrefs());
        expectedModel.addAppointment(validAppointment);
        assertCommandSuccess(new AddAppointmentCommand(patientIndex, doctorIndex, descriptor), model,
                String.format(AddAppointmentCommand.MESSAGE_SUCCESS, validAppointment), expectedModel);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Appointment appointmentInList = model.getPlannerMd().getAppointmentList().get(0);
        Patient patient = appointmentInList.getPatient();
        Doctor doctor = appointmentInList.getDoctor();
        Index doctorIndex = INDEX_FIRST_PERSON;
        Index patientIndex = INDEX_FIRST_PERSON;
        for (Patient pt : model.getPlannerMd().getPatientList()) {
            if (pt.equals(patient)) {
                patientIndex = Index.fromZeroBased(model.getPlannerMd().getPatientList().indexOf(pt));
                break;
            }
        }
        for (Doctor dr : model.getPlannerMd().getDoctorList()) {
            if (dr.equals(doctor)) {
                doctorIndex = Index.fromZeroBased(model.getPlannerMd().getDoctorList().indexOf(dr));
                break;
            }

        }
        AddAppointmentCommand.AddAppointmentDescriptor descriptor =
                new AddAppointmentDescriptorBuilder(appointmentInList).build();
        assertCommandFailure(new AddAppointmentCommand(patientIndex, doctorIndex, descriptor), model,
                AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
    }


    @Test
    public void execute_conflictingAppointment_throwsCommandException() {
        Appointment appointmentInList = model.getPlannerMd().getAppointmentList().get(0);
        Patient patient = appointmentInList.getPatient();

        Index doctorIndex = INDEX_FIRST_PERSON;
        Index patientIndex = INDEX_FIRST_PERSON;
        for (Patient pt : model.getPlannerMd().getPatientList()) {
            if (pt.equals(patient)) {
                patientIndex = Index.fromZeroBased(model.getPlannerMd().getPatientList().indexOf(pt));
                break;
            }
        }

        AddAppointmentCommand.AddAppointmentDescriptor descriptor =
                new AddAppointmentDescriptorBuilder(appointmentInList).build();

        assertCommandFailure(new AddAppointmentCommand(patientIndex, doctorIndex, descriptor), model,
                AddAppointmentCommand.MESSAGE_CONFLICTING_APPOINTMENT);
    }
}
