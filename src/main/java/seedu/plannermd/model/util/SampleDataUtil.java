package seedu.plannermd.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.plannermd.model.PlannerMd;
import seedu.plannermd.model.ReadOnlyPlannerMd;
import seedu.plannermd.model.appointment.Appointment;
import seedu.plannermd.model.appointment.AppointmentDate;
import seedu.plannermd.model.appointment.Duration;
import seedu.plannermd.model.appointment.Session;
import seedu.plannermd.model.doctor.Doctor;
import seedu.plannermd.model.patient.Patient;
import seedu.plannermd.model.patient.Risk;
import seedu.plannermd.model.person.Address;
import seedu.plannermd.model.person.BirthDate;
import seedu.plannermd.model.person.Email;
import seedu.plannermd.model.person.Name;
import seedu.plannermd.model.person.Phone;
import seedu.plannermd.model.person.Remark;
import seedu.plannermd.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PlannerMd} with sample data.
 */
public class SampleDataUtil {

    private static final Remark EMPTY_REMARK = new Remark("");

    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new BirthDate("27/10/1967"),
                    new Remark("Prefer Dr. Lau"), getTagSet("friends"), new Risk("LOW")),
            new Patient(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new BirthDate("22/08/1967"),
                    new Remark("Monthly insulin prescription"), getTagSet("colleagues", "friends"),
                    new Risk("MEDIUM")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new BirthDate("20/03/1976"),
                    new Remark("Only understands Mandarin"), getTagSet("neighbours"), new Risk("HIGH")),
            new Patient(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new BirthDate("20/07/1964"),
                    EMPTY_REMARK, getTagSet("family"), new Risk("LOW")),
            new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new BirthDate("25/12/2000"), EMPTY_REMARK,
                    getTagSet("classmates"), new Risk("LOW")),
            new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new BirthDate("09/08/1965"), EMPTY_REMARK,
                    getTagSet("colleagues"), new Risk("MEDIUM")) };
    }

    public static Doctor[] getSampleDoctors() {
        return new Doctor[] {
            new Doctor(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new BirthDate("27/10/1967"),
                    new Remark("Prefer Dr. Lau"), getTagSet("friends")),
            new Doctor(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new BirthDate("22/08/1967"),
                    new Remark("Monthly insulin prescription"), getTagSet("colleagues", "friends")),
            new Doctor(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new BirthDate("20/03/1976"),
                    new Remark("Only understands Mandarin"), getTagSet("neighbours")),
            new Doctor(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new BirthDate("20/07/1964"),
                    EMPTY_REMARK, getTagSet("family")),
            new Doctor(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new BirthDate("25/12/2000"), EMPTY_REMARK,
                    getTagSet("classmates")),
            new Doctor(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new BirthDate("09/08/1965"), EMPTY_REMARK,
                    getTagSet("colleagues")) };
    }

    public static Appointment[] getSampleAppointments() {
        return new Appointment[] {
                new Appointment(new Patient(new Name("Alex Yeoh"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new BirthDate("27/10/1967"), new Remark("Prefer Dr. Lau"), getTagSet("friends"),
                        new Risk("LOW")), new Doctor(new Name("Irfan Ibrahim"), new Phone("92492021"),
                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                        new BirthDate("25/12/2000"), EMPTY_REMARK,
                        getTagSet("classmates")), new AppointmentDate("25/12/2021"),
                        new Session("11:30", new Duration(30)), EMPTY_REMARK),
                new Appointment(new Patient(new Name("David Li"), new Phone("91031282"),
                        new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new BirthDate("20/07/1964"), EMPTY_REMARK, getTagSet("family"), new Risk("LOW")),
                        new Doctor(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new BirthDate("25/12/2000"), EMPTY_REMARK,
                        getTagSet("classmates")), new AppointmentDate("25/12/2021"),
                        new Session("12:00", new Duration(30)), EMPTY_REMARK)
        };
    }

    public static ReadOnlyPlannerMd getSamplePlannerMd() {
        PlannerMd samplePm = new PlannerMd();
        for (Patient samplePatient : getSamplePatients()) {
            samplePm.addPatient(samplePatient);
        }
        for (Doctor sampleDoctor : getSampleDoctors()) {
            samplePm.addDoctor(sampleDoctor);
        }
        for (Appointment sampleAppointment: getSampleAppointments()) {
            //TODO: add sample appointments
        }
        return samplePm;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}
