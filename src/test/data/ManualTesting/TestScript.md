TestCase ID: TC000 <br>
Title : Loading Sample test data <br>
Description : Tester should be able to load the sample data successfully <br>
Assumption : Tester has yet to open the app once <br>

Steps no. | Steps | Test Data |  ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | create a new empty folder named 'TaskTracker' (or any name you prefer) on any location |  |a folder called 'TaskTracker' (or as named) should be created
2 | download the v0.5 jar file and place it inside 'TaskTracker' folder | 	[Project Releases](https://github.com/CS2103AUG2016-T11-C4/main/releases) |the folder should contain the downloaded jar file
3 | Within the same folder you created, create a new folder called "data" | |A new folder named "data" should be created
4 | Transfer the `SampleData.json` in `ManualTesting` folder and put it in the `data` folder | |The forDemoUse.json file should be inside the `data` folder
5 | Rename the `SampleData.json` to `taskbook.json` | |The `SampleData.json` is renamed to `taskbook.json`
6 | open the jar file | |TaskTracker window should appear. config, perferences and log file should be generated in the same folder and some tasks should already be loaded into the system.


TestCase ID: TC001 <br>
Title : Opening the help menu <br>
Description : Tester should be able to open the help menu <br>
Precondition : TaskTraker app is opened 

Steps no. | Steps | Test Data |  ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Go to the Command box on the top of the app | | User should be able to type anything here
2 | Enter the help command in the box and press enter | help | a Help window should pop up
3 | close the help window by pressing esc | | The help window should close


TestCase ID: TC002 <br>
Title : Adding a task, deadline, event <br>
Description : Tester should be able to add a task event and deadline today <br>
Format : <br>
`add FLOATING_TASK_NAME [p-priority]` <br> 
`add DEADLINE_NAME MM/DD/YYYY HH:MM[am/pm]` <br>
`add EVENT_NAME MM/DD/YYYY HH:MM[am/pm] to MM/DD/YYYY HH:MM[am/pm]` <br>
Format expanationation: <br>
If the task name contains more than one words and spaces, please use `"name with space"`.<br>
When indicating the time, only one of `am` and `pm` should be used.
Pre-condition : TaskTraker Must be opened <br>
Assumption : Tester is testing on 11-11-2016. If not, the event and deadline added will be marked as finishd and overdue respectively.

Steps no. | Steps | Test Data |  ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Go to the Command box on the top of the app | | User should be able to type anything here
2 | Write/ Paste the Test Data into the box And press enter|  add "testing a program now" p-3 | The added task should be added and highlighted under 'Floating Task' and given a `Priority` of 3.
3 | Repeat step 2 but with a different set of test data |  add "this is a deadline" 11/11/2016 11:15pm | the added deadline should be added and highlighted under 'Deadline'
4 | Repeat step 2 one more time but adding a Event this time | add "test cs2103 product" 11/11/2016 4:15pm to 8:20pm | the added event should be added and highlighted under "Event"

TestCase ID: TC003 <br>
Title : The Flexibility of adding a task,event ,deadline <br>
Description : Tester should be able to add a task without following a rigid format <br>
Assumption : TC002 has been tested and Tester are familiar with the format of adding atask <br>

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Add a floating task using the test data | add "very flexible" | the task should be added and highlighted under 'Floating Task' given a default priority of 0.
2 | Add a deadline | add cs2103Report 10pm | the deadline should be added and highlighted under 'Deadline'
3 | Add a event | add "picnic with professor" tdy 11pm to tmr | the event should be added and highlighted under 'Event'.

TestCase ID : TC004 <br>
Title : Searching for task <br>
Description : Tester should be able to search task by their names using keywords. <br>
Format : <br>
`find KEYWORDS [mark/true]` <br> 

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Enter the find by name command | find cs2103 | the list should be updated with all task that contain CS in the name


TestCase ID : TC005 <br>
Title : Deleting (a) tasks <br>
Description : Tester should be able to delete away task <br>
Format: <br>
`del [f/d/e]index` <br>

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Default the list to show everything using find command | find type/all | the list should show everything stored in the save data
2 | Delete an event task using delete command | del e1 | the task should be gone from the list. And System should print out the index of the task that is deleted 
3 | Delete a deadline task using delete command | del d1 | the task should be gone from the list. And System should print out the index of the task that is deleted 
4 | Delete a floating task using delete command | del f1 | the task should be gone from the list. And System should print out the index of the task that is deleted 


TestCase ID : TC006 <br>
Title : Marking a task as finished and unfinished <br>
Description : Tester should be able to mark a deadline/floating task as finished and unfinished <br>
Format: <br>
`mark [f/d]index` <br> 

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Use the list command to find all unfinished tasks | list unfin | the list should show all unfinished tasks
2 | Mark the task in index d3 as done | fin d3 | the task should be strikethrough and the task is successfully marked as finished
3 | Use the list command to see all finished tasks | list fin | the list should show all finished including the one just marked as finished
4 | Mark the task in index d3 as unfinished | unfin d1 | the strikethrough of that task shoul be disappear and the task is successfully marked as unfinished


TestCase ID : TC007 <br>
Title : Clear all finished tasks <br>
Description : Tester should be able to delete all finished tasks <br>
Assumption : The list is still under the effect of 'list fin' the list show only show tasks finished <br>
Format: <br>
`clear fin` <br> 

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Use the list command to list all tasks | list | the list should show all tasks
2 | Clear finished tasks | clear fin | the finished tasks should be deleted from the lists
3 | use the list command to refresh the index | list | the list should show all tasks and the task indexes will be updated


TestCase ID : TC008 <br>
Title : Editing a task <br>
Description : Tester should be able to edit a task succesffully <br>
Precondition : TC007 is completed <br>
Format: <br>
Edit Floating Tasks: `edit INDEX [n-NEW_NAME] [p-PRIORITY]` <br>
Edit Deadline `edit INDEX [dd-DUE_DATE] [dt-DUE_TIME] [n-NEW_NAME]` <br>
Edit Event `edit INDEX [sd-START_DATE] [st-START_TIME] [ed-END_DATE] [et-END_TIME] [n-NEW_NAME]` <br>

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | edit the first floating task and change it name | edit f1 n-clean the window and floor p-4| the edited task should be highlighted reflecting the new changes
2 | edit the first event task | edit e1 st-12am et-11:59pm | the edited task should be highlighted reflecting the new changes
3 | edit the first event task | edit dt-10pm | the edited task should be highlighted reflecting the new changes 
4 | use the list command to refresh the index | list | the list should show all tasks and the task indexes will be updated

TestCase ID : TC009 <br>
Title : Undo/Redo a action <br> 
Description : Tester should be able to undo redo a actions related to add, delete, fin, unfin, clear command <br>

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | To undo last action | undo | the last edit command should be undone
2 | To redo last action | redo | the last edit command should be redone again

TestCase ID : TC010 <br>
Title : Changing/Check save data location <br>
Description : Tester should be able to change the save data location <br>
Format : <br>
`setdatadir C:\Users` will change the directory on a windows platform
`setdatadir ./path/to/new/location/on/unix/platform/` will change the location on a unix platform <br>
Description : Tester should be able change the location of the save data location. <br>
Assumption : Tester should be able to provide a valid file path, not doing so will result in a invalid file path message
Tester should provide a file path that do not require system permission (e.g saving inside a C drive C:\ )

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | change the test data to a new location | refer to the setdatadir command format explained in this test case | The system should display the location of the new sav data. the new data should be created physically on the new location as well


TestCase ID :TC011 <br>
Title : Clear the entire saved data <br>
Description : Tester should be able to clear the entire saved data completely. This action can be undo. <br>

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | clear everything in TaskTracker | clear | the list should show nothing after this command
2 | undo the clear command | undo | the list should show everything back before clearing

TestCase ID : TC012 <br>
Title : Auto hide the program and wake up the program from background. <br>
Description : Tester should hide the program into background and wake it up when needed.
Hot Key: <br>
`CTRL + SPACE`<br>
This hot key will only work on windows system.

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | hide the app in background | CTRL + SPACE | the TaskTracker will be hiden into backgound
2 | wake up the app from background | CTRL + SPACE | the TaskTracker window will be shown again

TestCase ID : TC013 <br>
Title : Auto complete. <br>
Description: Tester should use `TAB` to do auto-completing when they are entering command keyword and edit certain fileds of some tasks.
Hot Key: <br>
`TAB`<br>

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | Go to the Command box on the top of the app | | User should be able to type anything here
2 | find task whose name contains `Unicycling` keyword | find 'Unicycling' | TaskTracker will list all tasks whose name contains the keyword
3 | show the command list in command box | TAB | the command list will shown
4 | repeat step 2 to select the edit command | TAB | the edit command will be highlighted
5 | select the edit command | ENTER | the edit command will be selected and typed into the command box automatically
6 | enter the index number of the target task and then the keyword for task name field | e3 n- |
7 | use auto-complete to load the previous name | TAB | the previous task name will be auto typed in the command box
8 | revise the name a little bit | revise the number `3` to `10` in the task name and press ENTER | the edited task should be highlighted reflecting the new changes 


TestCase ID : TC014 <br>
Title : Exit the TaskTraker Program <br >
Description : Tester should be able to exit the program successfully

Steps no. | Steps | Test Data | ExpectedResult
--- | :---------------- | :---------------- | :----------------
1 | exit the program using exit command | exit | the TaskTraker should close automatically
