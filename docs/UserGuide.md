# User Guide

* [Quick Start](#quick-start)

## Quick Start

1. Ensure you have Java version `1.8.0_60` or later installed in your computer.

  > Having any Java 8 version is not enough.
  > This app will not work with earlier versions of Java 8.

2. Download the latest `TaskTracker.jar` from the 'releases' tab.

3. Copy the file to the folder you want to use as the home folder for your task
   database.

4. Double-click the file to start the app. The GUI should appear in a few
   seconds.

5. Set up: enter your name and verify the curent date and time.

6. You're good to go! Try adding your first Task. press ';' to launch/hide the
   command bar. Press Enter to enter a command. For floating task, `add
   [taskname]`!

   ![alt text](./images/userguide/command_bar.png "command bar")

7. Add task with due date/time, example commands are:

	* `add "bake cookies" 31/12 1500`<br>
      bake cookies by 31 December of this year, 3pm.

	* `add "bake cookies" 1500`<br>
      bake cookies by 3pm today.

   ![alt text](./images/userguide/task.png "")

8. Add event. An event is a task with a start time and end time, example
   commands are:

	* `add "CS2103t summer camp" 30/8 1300 3/1/2017 1800`

      Event CS2103t summer camp starts on 30 Aug 2016, 1pm, and ends on 3
      January 2017,6pm.

	* `add "potato peeling" 1500 1900`

      Event potato peeling starts today 3pm and ends today 7pm.

	* `add "cupcake festival" 30/08 800 1200`

      Event cupcake festival starts on 8am and ends on 12pm of 24 Oct of this
      year.

	* `add "Trick or treat" 800 31/10 900`

      Event Trick or treat starts today 8pm and ends on 31 Oct 9pm.

   The events for current selected day will show up under schedule.

   ![alt text](./images/userguide/event.png "")

9. To see the schedule of a different day, press 'C' to toggle calender mode on
   and off. Use arrow keys and enter to select the day to view. The schedule
   for the day will show up in the schedule column.

   ![alt text](./images/userguide/calender.png "")

   ![alt text](./images/userguide/toggle_calender.png "")

   Alternatively, use the command line to view schedules. For example: `view 30/08/2016`

10. To delete a task/event, try:

	    del "cupcake festival"

11. To edit a deadline, try:

	* `edit "bake potato" dd-12/10 dt-1500`

      The following fields of task are modified: due date, due time. (`dd`
      refers to `due date`, `dt` refers to `due time`)

	* `edit "bake potato" dt-1500`

      Due time of bake potato modified.

11. To edit an event, try:

	* `edit "cupcake festival" loc-NUS`

      Location of cupcake festival set to NUS.

	* `edit "cupcake festival" st-1500 sd-12/10 et-1700 -ed 13/10 loc-Yishun`

      All fields modified. (`st` : `starting time`, `sd` : `starting date`,
      `et` : `ending time`, `ed` : `ending date`)

    * `edit "cupcake festival" st-1500`

      start time of cupcake festival modified.

12. To edit a floating task, try:

	* `edit "bake potato" p-1"`

      cupcake festival is given a priority of 1.

13. To exit the program, try:

	* `exit`

      Close the Task-tracker.
