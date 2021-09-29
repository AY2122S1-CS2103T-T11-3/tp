package seedu.plannermd.logic.commands;

import static seedu.plannermd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.plannermd.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.plannermd.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.plannermd.testutil.TypicalPersons.getTypicalPlannerMd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.plannermd.logic.commands.listcommand.ListPatientCommand;
import seedu.plannermd.model.Model;
import seedu.plannermd.model.ModelManager;
import seedu.plannermd.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListPatientCommand.
 */
public class ListPatientCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPlannerMd(), new UserPrefs());
        expectedModel = new ModelManager(model.getPlannerMd(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPatientCommand(), model, ListPatientCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListPatientCommand(), model, ListPatientCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
