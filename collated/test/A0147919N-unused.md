# A0147919N

Unused code
###### src/test/java/seedu/address/logic/commands/AddDeadlineCommandTest.java

``` java
/*
Test for AddDeadlineCommand
*/

public class AddDeadlineCommandTest {
	private DeadlineTask task1;
	private DeadlineTask task2;
	private DeadlineTask task3;

	private final Model model = new ModelManager();

	public void DeadlineTasks(DeadlineTask task1, DeadlineTask task2, DeadlineTask task3){
		try{
			task1 = new DeadlineTask("Finish project", UNIX_EPOCH.plusHours(1));
			task2 = new DeadlineTask("Pay rent", UNIX_EPOCH.plusDays(25));
			task3 = new DeadlineTask("Book tickets for movie", UNIX_EPOCH.plusYears(10));
		}
		catch (IllegalValueException e){
			throw new AssertionError("this should not happen", e);
		}
	}

	AddDeadlineCommand command1 = new AddDeadlineCommand(task1);
	AddDeadlineCommand command2 = new AddDeadlineCommand(task2);
	AddDeadlineCommand command3 = new AddDeadlineCommand(task3);

	@Test
	public void test() throws Exception{
		assertEquals(1, model.addDeadlineTask(task1));
		assertEquals(2, model.addDeadlineTask(task2));
		assertEquals(3, model.addDeadlineTask(task3));
		final CommandResult result = new AddDeadlineCommand(task1).execute(model);
		final CommandResult result2 = new AddDeadlineCommand(task2).execute(model);
		final CommandResult result3 = new AddDeadlineCommand(task3).execute(model);
		assertEquals(task1, model.getDeadlineTask(1));
		assertEquals(task2, model.getDeadlineTask(2));
		assertEquals(task3, model.getDeadlineTask(3));
	}

	}
onfigPath;
    }

```
