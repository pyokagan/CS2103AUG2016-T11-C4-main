package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public static TestTask alice;

    public static TestTask benson;

    public static TestTask carl;

    public static TestTask daniel;

    public static TestTask elle;

    public static TestTask fiona;

    public static TestTask george;

    public static TestTask hoon;

    public static TestTask ida;

    public TypicalTestPersons() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111")
                    .withEmail("alice@gmail.com").withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563").withEmail("heinz@yahoo.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533").withEmail("cornelia@google.com").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224").withEmail("werner@gmail.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427").withEmail("lydia@gmail.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442").withEmail("anna@google.com").withAddress("4th street").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424").withEmail("stefan@mail.com").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131").withEmail("hans@google.com").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new Task(alice));
            ab.addPerson(new Task(benson));
            ab.addPerson(new Task(carl));
            ab.addPerson(new Task(daniel));
            ab.addPerson(new Task(elle));
            ab.addPerson(new Task(fiona));
            ab.addPerson(new Task(george));
        } catch (UniqueTaskList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
