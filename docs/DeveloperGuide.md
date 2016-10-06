# Developer Guide

* [Appendix A: User Stories](#appendix-a-user-stories)
* [Appendix B: Use Cases](#appendix-b-use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c-non-functional-requirements)

## Appendix A: User Stories

Priorities:

* `* * *` -- High (must have)
* `* *` -- Medium (nice to have)
* `*` -- Low (unlikely to have)

User categories:

* User -- Any user
* New user -- User that has used Task Tracker for less than 100 hours
* Busy user -- User that has a high density of deadlines and/or events over the
  next 1 month
* Power user -- A user that likes to do things with as few interactions
  (keystrokes, mouseclicks, confirmations) as possible.
* Group user -- User that has other people working on the same task

Priority | As a ... | I want to ... | So that I can...
-------- | :------- | :------------ | :---------------
`* * *`  | New user | See the manual | refer to manual when I forget how to use the App
`* * *`  | User | Add an event to the task manager | keep track of it and be notified when it is approaching.
`* * *`  | User | Add a deadline to the task manager | keep track of it and be notified when it is approaching.
`* * *`  | User | Add a floating task to the task manager | Keep track of it and remember to do it when I'm free
`* * *`  | User | View my floating tasks | keep track of them.
`* * *`  | User | Search specific tasks by keywords |
`* * *`  | Power user | Have shortcut keys to launch the app | Launch the app quickly
`* * *`  | Power user | Have shortcut keys to minimise the app | Hide the app with only the keyboard
`* *`    | User | Mark a deadline as finished before the due time | Remove it from the notification list and archive it.
`* *`    | User | Mark a floating task as finished | Remove it from my floating task list.
`* *`    | User who has events taking place at multiple locations | Add the location of an event | Be reminded of where to go.
`* *`    | User | Have the app notify me of the error in my command, and suggest the right command when I make a typo/forget the format of the command | Enter in the correct command immediately without having to open up the manual.
`* *`    | Busy user | View what events or deadlines are scheduled over a range of time | Ensure that the event does not clash with other events or deadlines.
`* *`    | User | Revise the due datetime for a certain deadline. | Keep track of it and avoid creating a new deadline when the time has been revised.
`* *`    | User | Revise the timeslot for a certain event | Keep track of it and avoid creating a new event when the time has been revised.
`* *`    | Busy user | Generate a list of all empty time slots in a given period | Choose a free time slot to create new events or tasks.
`* *`    | User | Undo an action | Restore tasks deleted by accident.
`*`      | User who is unable to remember details of each task | Add a short description under the name of each task in my schedule | Know how to do the task and where, even if I forget these details by any chance.
`*`      | Busy user | Tags such as coloured dots, or icons, for each task which indicates the priority of the task (on a scale of 1-3) | Able to decide which task needs to be completed urgently
`*`      | Group user | Option to categorize my task as a "Group activity" and automatically send notifications (through mail or other social networking platforms) to all other users who are in my team, whenever I make any changes to our work schedule for the group activity; and send them reminders about upcoming deadlines for the tasks. Every time I add a new task, I should have also an option to either include it to an existing group activity or add it to a new group activity (different commands for each of these operations). | Improve my work efficiency, and make sure everyone in my team are aware of the work schedule of our project.
`* `     | User who needs to be reminded of the task before the deadline date. | Set reminders at customized times before the deadline. | Have enough time to complete the task before deadline, even if I forgot to do it.

## Appendix B: Use Cases

**Software System**: TaskTracker

**Actor**: User

### Use case: Add an event

**MSS**

1. User requests to add an event with the specified name, start date/time and
   end date/time.

2. TaskTracker adds the event to the database, and notifies the user that the
   event was successfully added.

   Use case ends.

**Extensions**

1a. The start datetime occurs after the end datetime.

> 1a1. TaskTracker notifies the user that the start datetime and end datetime
>      are invalid.

> Use case ends.

1b. The range of time specified by the start datetime and end datetime occurs
    in the past.

> 1b1. TaskTracker warns the user that the event is in the past.

> Use case resumes from step 2.

1c. The name of the event contains invalid characters.

> 1c1. TaskTracker notifies the user that the event name contains which invalid
>      character(s).

> Use case ends.

1d. The name of the event contains leading/trailing whitespace.

> 1d1. TaskTracker silently strips the leading/trailing whitespace from the
>      name.

> Use case resumes from step 2.

### Use case: Add a deadline

**MSS**

1. User requests to add a deadline with the specified name and end date/time.

2. TaskTracker adds the deadline to the database, and notifies the user that
   the deadline was successfully added.

   Use case ends.

**Extensions**

1a. The end datetime occurs in the past.

> 1a1. TaskTracker warns the user that the end datetime is in the past.

> Use case resumes from step 2.

1b. The name of the deadline contains invalid characters.

> 1b1. TaskTracker notifies the user that the deadline name contains which
>      invalid character(s).

> Use case ends.

1c. The name of the deadline contains leading/trailing whitespace.

> 1c1. TaskTracker silently strips the whitespace from the name.

> Use case resumes from step 2.

### Use case: Add a floating task

**MSS**

1. Use requests to add a floating task with the specified name.

2. TaskTracker adds the floating task to the database at the lowest priority,
   and notifies the user that the floating task was successfully added.

   Use case ends.

**Extensions**

1a. The name of the floating task contains invalid character(s).

> 1a1. TaskTracker notifies the user that the floating task name contains which
>      invalid character(s).

> Use case ends.

1b. The name of the floating task contains leading/trailing whitespace.

> 1b1. TaskTracker silently strips the leading/trailing whitespace from the
>      name.

> Use case resumes from step 2.

### Use case: View all floating tasks

**MSS**

1. User requests to view all floating tasks.

2. TaskTracker displays all floating tasks in the database as a list, ordered
   from highest to lowest priority. <br>
   Use case ends.

**Extensions**

1a. There are no floating tasks in the database.

> 1a1. TaskTracker notifies the user that there are no floating tasks in the
>      database.

> Use case ends.

### Use case: Revise the due time of a deadline task

**MSS**

1. User requests to update the due datetime of a certain deadline with new
   date/time information.

2. TaskTracker revises the deadline to the new time, and notifies the user that
   the due date/time was successfully revised.

   Use case ends.

**Extensions**

1a. The requested deadline task does not exist.

> 1a1. TaskTracker informs the user that the requested deadline does not exist.

> Use case ends.

1b. The new time is the same as the previous due datetime.

> 1b1. TaskTracker informs the user that the datetime remains unchanged.

> Use case ends.

1c. The new date/time occurs in the past.

> 1c1. TaskTracker warns the user that the end datetime is in the past.

> Use case resumes from step 2.

### Use case: Revise the time of an event

**MSS**

1. User requests to revise the the time of a certain deadline with new
   date/time information.

2. TaskTracker revises the event to a new time slot, and notifies the user that
   the time was successfully revised.

   Use case ends.

**Extensions**

1d. The requested event does not exist.

> 1a1. TaskTracker informs the user that the requested event does not exist.

> Use case ends.

1b. The range of time specified by the start datetime and end datetime
    intersects with the start-end datetime range of other event(s).

> 1b1. TaskTracker warns the user that the event clashes with which event(s).

> Use case resumes from step 2.

1c. The start datetime occurs after the end datetime.

> 1c1. TaskTracker notifies the user that the start datetime and end datetime
> are invalid.

> Use case ends.

1d. The range of time specified by the start datetime and end datetime occurs
    in the past.

> 1e1. TaskTracker warns the user that the event is in the past.

> Use case resumes from step 2.

1e. The new datetime is the same as the existing datetime.

> 1e1. TaskTracker informs the user that the datetime remains unchanged.

> Use case ends.

### Use case: mark a floating task/deadline as finished

**MSS**

1. User requests to mark a certain floating task/deadline as finished.

2. TaskTracker marks the task as finished and informs the user.
   Use case ends.

**Extensions**

1a. The floating task does not exist.

> 1a1. TaskTracker informs the user that the floating task does not exist.

> Use case ends.

### Use case: Generate a list of empty time slots

**MSS**

1. User request to generate a list of free time slots in a certain period with
   certain time duration.

2. TaskTracker lists all possible time slots in that period <br>
   Use case ends.

**Extensions**

1a. The time period requested is invalid (wrong format or time in the past)

> 1a1. TaskTracker shows an error message.

> Use case ends.

1b. The input duration is in wrong format

> 1b1. TaskTracker shows an error message to inform the error.

> Use case ends.

2a. There is no feasible time slot

> 2a1. TaskTracker shows an empty list and throw a message saying there is no
> avaliable time slots for the user in the given time period.

> Use case ends.

### Use case: Add priority tags

**MSS**

1. The user requests to add a priority tag for a specified event.

2. TaskTracker prompts the user to select the priority level of the event -
   "Very Important"/ "Important"/ "Not important".

3. The user selects the appropriate priority level for the event from these
   options.

4. TaskManager updates the priority of the event with the specified priority
   and it is displayed on the user’s schedule with a corresponding colored
   circle (for each priority level).

5. The user is notified that the update was successful.

   Use case ends.

**Extensions**

1a. The user places a request to add a priority tag, but does not add it.

> 1a1. User presses "Esc". TaskTracker goes back to the normal view of the
> schedule. Now even if the user clicks on any event, priority tag options will
> not be displayed as the TaskTracker switched from Priority Tag view to Normal
> view.

> Use case ends.

### Use case: Add new event under a specific "group activity"

**MSS**

1. When the user is adding a new event, he selects the option "Group Activity"
   which is present in the same window.

2. The existing group activities are displayed and the user chooses the one
   which is related to the new event that he is adding.

3. If he wants to create a new group activity, then the user places a request
   to add new group activity in the “Group Activity” window.

5. The user then enters the details of the group activity (such as name of the
   group activity and email addresses of the people in the group).

6. TaskTracker automatically adds the event to this new activity.

7. TaskTracker notifies the user that the event has been added to the schedule.

   User case ends.

**Extensions**

1a. The user enters an invalid email address of a group member, while creating
a new group activity.

> 1a1. TaskTracker displays the error message - "Invalid email".

> Use case ends.

1b. The user enters a group activity name that already exists, while adding a
    new group activity.

> 1b1. TaskTracker displays an error message "Name already exists".

> Use case ends.

### Use case: Add event description

**MSS**

1. The user requests to add a new event.

2. The user writes a small description about the event in the "Description"
   field, present in the "Add new event" window.

3. The event is added and the user is notified about the new event added.

   User case ends.

### Use case: Set reminders

**MSS**

1. When adding a new event, the user adds the time at which he wants to set a
   reminder, before the event occurs. The time is entered in day/hours/minutes
   format.

2. The new event is added to the schedule and the user is notified about the
   addition of the new event.

   User case ends.

**Extensions**

1a. The user enters the time in a wrong format.

> 1a1. TaskTracker shows an error message "Invalid time format"

> User case ends.

### See the manual

**Use case: see the manual**

**MSS**

1. User requests to see the manual.

2. TaskTracker shows manual.

   Use case ends.

### Search the manual

**Use case: search the manual**

**MSS**

1. User request to search the manual for a specific command.

2. TaskTracker shows the specific manual command.

   Use case ends

**Extensions**

1a. The command to be searched does not exist

> 1a1. TaskTracker informs the user that the command does not exist.

> Use case ends

### Undo an action

**User case: Undo an action, such as restoring a deleted a task**

**MSS**

1. The user wants to undo a completed action

2. The previous action is undone. Task manager is restored to state before the
   completed action.

   User case ends.

**Extensions**

1a. There is no action to undo

> 1a1. TaskTracker shows an error message "no action to undo"

> User Case ends.

## Appendix C: Non Functional Requirements

1. Should work on any mainstream OS as long as it has Java `1.8.0_60` or higher
   installed.

2. Should come with automated unit tests and open source code.

3. Have shortcut keys to launch/minimise the app.

4. Have the app notify me of the error in my command, and suggest the right
   command when I make a typo/forget the format of the command
