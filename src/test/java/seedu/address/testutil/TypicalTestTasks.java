package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskBook;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public static TestTask alice;

    public static TestTask benson;

    public static TestTask carl;

    public static TestTask daniel;

    public static TestTask elle;

    public static TestTask fiona;

    public static TestTask george;

    public static TestTask hoon;

    public static TestTask ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442").build();

            //Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskBook ab) {

        try {
            ab.addTask(new Task(alice));
            ab.addTask(new Task(benson));
            ab.addTask(new Task(carl));
            ab.addTask(new Task(daniel));
            ab.addTask(new Task(elle));
            ab.addTask(new Task(fiona));
            ab.addTask(new Task(george));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            assert false : "not possible";
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBook getTypicalAddressBook() {
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
