package seedu.plannermd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.plannermd.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.plannermd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.plannermd.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.plannermd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.plannermd.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.plannermd.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.plannermd.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.plannermd.testutil.TypicalPersons.getTypicalPlannerMd;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.plannermd.commons.core.Messages;
import seedu.plannermd.commons.core.index.Index;
import seedu.plannermd.model.Model;
import seedu.plannermd.model.ModelManager;
import seedu.plannermd.model.PlannerMd;
import seedu.plannermd.model.UserPrefs;
import seedu.plannermd.model.person.Person;
import seedu.plannermd.model.tag.Tag;
import seedu.plannermd.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TagCommand.
 */
class TagCommandTest {

    private final Model model = new ModelManager(getTypicalPlannerMd(), new UserPrefs());

    private final Tag tag = new Tag(VALID_TAG_FRIEND);

    @Test
    void execute_validTagUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> newTags = new HashSet<>(firstPerson.getTags());
        newTags.add(tag);
        Person editedPerson = new PersonBuilder(firstPerson).withTags(
                        newTags.stream().map(t -> t.tagName).toArray(String[]::new))
                .build();

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PlannerMd(model.getPlannerMd()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validTagFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Tag> newTags = new HashSet<>(personInFilteredList.getTags());
        newTags.add(tag);
        Person editedPerson = new PersonBuilder(personInFilteredList).withTags(
                        newTags.stream().map(t -> t.tagName).toArray(String[]::new))
                .build();

        TagCommand tagCommand = new TagCommand(INDEX_FIRST_PERSON, tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_ADD_TAG_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new PlannerMd(model.getPlannerMd()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), editedPerson);

        assertCommandSuccess(tagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand tagCommand = new TagCommand(outOfBoundIndex, tag);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of plannermd list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPlannerMd().getPersonList().size());

        TagCommand tagCommand = new TagCommand(outOfBoundIndex, tag);

        assertCommandFailure(tagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        final TagCommand standardCommand = new TagCommand(INDEX_FIRST_PERSON, tag);

        // same values -> returns true
        Tag copyTag = new Tag(VALID_TAG_FRIEND);
        TagCommand commandWithSameValues = new TagCommand(INDEX_FIRST_PERSON, copyTag);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new TagCommand(INDEX_SECOND_PERSON, tag));

        // different tag -> returns false
        assertNotEquals(standardCommand, new TagCommand(INDEX_FIRST_PERSON, new Tag(VALID_TAG_HUSBAND)));
    }
}