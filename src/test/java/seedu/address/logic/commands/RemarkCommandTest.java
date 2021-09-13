package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalIndexes;
import seedu.address.testutil.TypicalPersons;

import static org.junit.jupiter.api.Assertions.*;

class RemarkCommandTest {

    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());
    @Test
    void execute_addRemarkUnfiltered_success() {
        Person firstPerson = model.getFilteredPersonList().get(TypicalIndexes.INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark("Test").build();

        RemarkCommand remarkCommand = new RemarkCommand(TypicalIndexes.INDEX_FIRST_PERSON,
                new Remark(editedPerson.getRemark().getValue()));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        CommandTestUtil.assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }
}