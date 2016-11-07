# A0147999Y

###### docs\UserGuide.md

    ## Features
###### docs\UserGuide.md

    ### <a name="cf">Command Format</a>
    
    * Words in `UPPER_CASE` are parameters to be defined by the user.
    
    * Words in `lower_case` are the reserved keywords. All keywords can not be changed in each command line.
    
    * Items in `[SQUARE_BRACKETS]` are optional parameters. All paramters in square backets can be omitted.
    
    * Items in `<ANGLE_BRACKETS>` are optional parameters. However, a certain number of these kind of parameters will be required in different commands.
    
    * All square brackets and angle brackets should not be included in the real commands.
    
    * Items separated by `|` are in parallel relation, only one of them should be
      use in each command.
    
    * Items with `...` after them can have multiple instances.
    
    * The parameters have to follow the order in which they are presented, unless otherwise specified.
###### docs\UserGuide.md

    ### <a name="addft">Adding a floating task: `add`</a>
    
    Adds a floating task to TaskTracker.
    
    Format:
    ```
    add "FLOATING_TASK_NAME" [p-PRIORITY]
    ```
    
    * Task name should be in a pair of quotation marks. However, quotations marks are not allowed in task name.
    
    * Task name can be a single word or a phrase, white space are allowed.
    
    * All floating tasks will be shown according to their `PRIORITY`s.
    
    * Two floating tasks with the same `PRIORITY` will be shown according to the order of the time they are created.
    
    * The `PRIORITY` attribute is an integer which ranges from `0` to `5`, with `0` being the lowest pririoty and `5` the higest.
    
    * The default priority of a floating task is `0`.
    
    #### Examples
    
    * `add "EE2020 lab report" p-5`
    
      To create a floating task called `EE2020 lab report` with `PRIORITY` of 5.
    
    * `add "Progress Reflection"`
    
      To create a floating task called `Progress Reflection` with default `PRIORITY` of 0.
    
    ### <a name="adddt">Adding a deadline task: `add`</a>
    
    Adds a deadline with specific due date and time to TaskTracker.
    
    Format:
    ```
    add "DEADLINE_NAME" <DATE> <TIME>
    ```
    
    * Deadline name should be in a pair of quotation marks. And quotations marks are not allowed in deadline name.
    
    * Keywords like `tdy`, `tmr`, `yst`, can be used in the `DATE` field.
    
    * Formats of `DATE` and `TIME` should follow those stated above in this user guide. See [Date Format](#DateFormat) and [Time Format](#TimeFormat)
    
    #### Examples
    
    * `add "CS2103 milestone 1" 16/12/2016 2pm`
    
        To create a deadline task named `CS2103 milestone 1` with deadline of 16th December 2016, 2 pm.
    
    * `add "spend pizza vouchers" 20/11/2018 2pm`
    
        To create a deadline task named `spend pizza vouchers` with deadline of 20 November 2018, 6 pm.
    
    * `add "event proposal" tdy 6pm`
    
        To create a deadline named `event proposal` with due date of today, and due time of 6 pm.
    
    * `add "EE2024 homework 1" tmr 6am`
    
        To create a deadline named `EE2024 homework 1` with deadline of tommorow, 6 am.
    
    ### <a name="adde">Adding an event: `add`</a>
    
    Adds an event with specific starting date, starting time and ending date, ending time to TaskTracker.
    
    Format:
    ```
    add "EVENT_NAME" <START_DATE> <START_TIME> to <END_DATE> <END_TIME>
    ```
    
    * Event name should be within a pair of quotation marks. Quotations marks are not allowed in event name.
    
    * The four parameters `START_DATE`, `START_TIME`, `END_DATE`, `END_TIME` are not all required for adding an event. Only one of `START_DATE` and `START_TIME` is required and only one of `START_TIME` and `END_DATE` is required.
    
    * Formats of `START_DATE`, `START_TIME`, `END_DATE`, `END_TIME` should follow those stated above in this user guide. See [Date Format](#DateFormat) and [Time Format](#TimeFormat).
    
    #### Examples
    
    * <a name="to">`add "CS2103 week8 lecture" 7/10 2pm to 4pm`</a>
    
        To create an event `CS2103 week8 lecture` with starting date 7 October 2016, starting time 2pm, ending date 7 Oct 2016, ending time 4pm.
    
    * `add "programming workshop" tdy 10am to 5pm`
    
        To create an event `programming workshop` that starts today, 10am, and last till 5pm.
    
    * `add "sports training camp" 1/12/2016 7pm to 10/1/2017 1pm`
    
        To create an event `sports training camp` with starting date 1 December 2016, starting time 7pm, ending date 10 January 2017 and ending time 1pm.
    
    ### <a name="del">Deleting a floating task/event/deadline: `del`</a>
    
    Delete a useless floating task/event/deadline on TaskTracker.
    
    Format:
    ```
    del-float|-deadline|-event <INDEX>
    ```
    
    * A task's `INDEX` is the number displayed beside the task name in the user interface.
    
    #### Examples
    
    * `del-event 2`
    
        Delete the event with the index of `2` in the task list shown in user interface.
    
    ### Edit a floating task/deadline/event: `edit`
    
    * Edit command can only edit the parameters of each commands but cannot change
      the type of task.  For example, `edit` cannot transform a floating task to a
      event task.
    
    * To edit an task, key in the index of the event followed by the properties to
      be modified. Label the new properties with their respective field references.
    
    * The `[PARAMETERS]` need not follow the order shown in the command format.
    
    #### <a name="editft">Edit a floating task: `edit`</a>
    
    Edit a floating task to revise its name or priority.
    
    |Field reference | Definition                    |
    |:----------------|:-----------------------------|
    | n- | name |
    | p- | priority |
    
    Format:
    ```
    edit-float <INDEX> [n-NEW_NAME] [p-PRIORITY]
    ```
    
    * Quotation marks are not required for `NEW_NAME`.
    
    * `PRIORITY` should only be the integer ranges from `0` to `5`.
    
    ##### Examples
    
    * `edit-float 2 p-0`
    
        Edit floating task with index of `2`'s priority to 0.
    
    * `edit-float 2 n-buy stationary`
    
        Edit floating task with index of `2`'s name to `buy stationary`.
    
    * `edit-float 5 n-go to Nanyang Mart p-1`
    
        Edit floating task with index of `5`'s name to `go to Nanyang
        Mart` and priority to 1.
    
    #### <a name="editdl">Edit a deadline : `edit`</a>
    
    Edit a deadline to revise its name and due date/time.
    
    |Field reference | Definition                        |
    |:----------------|:---------------------------------|
    | n- | name |
    | dd- | due date |
    | dt- | due time |
    
    Format:
    ```
    edit-deadline <INDEX> [dd-DUE_DATE] [dt-DUE_TIME] [n-NEW_NAME]
    ```
    
    * Quotation marks are not required for `NEW_NAME`.
    
    * Formats of `[dd-DUE_DATE]` and `[dt-DUE_TIME]` should follow those stated above in this user guide. See [Date Format](#DateFormat) and [Time Format](#TimeFormat).
    
    ##### Examples
    
    * `edit-deadline 1 dt-5pm`
    
        Edit deadline with index of `1`'s due time to 5 pm.
    
    * `edit-deadline 2 dd-23/11/2016`
    
        Edit deadline with index of `2`'s due date to 2016 23th November.
    
    #### <a name="edite">Edit an event : `edit`</a>
    
    Edit an event to revise its name, starting/ending date/time and location.
    
    |Field reference | Definition                  |
    |----------------|:--------------------------- |
    | n- | name |
    | sd- | start date |
    | st- | start time |
    | ed- | end date |
    | et- | end time |
    | loc-| location |
    
    Format: 
    ```
    edit-event <INDEX> [ n-NEW_NAME] [sd-NEW_START_TIME] [st-NEW_START_DATE] [ed-NEW_END_DATE] [et-NEW_END_TIME] [n-NEW_NAME]
    ```
    
    * Quotation marks are not required for `NEW_NAME`.
    
    * Formates of `[sd-NEW_START_TIME]` `[st-NEW_START_DATE]` `[ed-NEW_END_DATE]` `[ed-NEW_END_DATE]` should follow those stated above in this user guide. See [Date Format](#DateFormat) and [Time Format](#TimeFormat).
    
    ##### Examples
    
    * `edit-event 2 st-4pm et-6pm`
    
        Edit event with index of `2`'s starting time to 4pm and ending time to 6pm.
    
    * `edit-event 7 n-"proposal meeting" st-7pm`
    
        Edit event with index of `7`'s starting time to 7pm and name to
        `proposal meeting`.
    
    ### <a name="fin">Mark a floating task/deadline as done/finished: `fin`</a>
    
    Mark a floating task/event/deadline as done on TaskTracker, the marked tasks
    will be archived.
    
    Format: 
    ```
    fin-float|-deadline|-event <INDEX>
    ```
    
    * Events that have already passed it `DUE_TIME` will be marked as done automatically.
    
    * Deadlines that have already passed it `DUE_TIME` will not be marked as done, but will be marked as "overdue" automatically.
    
    #### Examples
    
    * `fin-float 1`
    
        Marked floating task `1` as finished.
###### docs\UserGuide.md

    ### <a name="search">Search by keywords: `search`</a>
    
    Search task that contains specific keywords.
    
    Format: 
    ```
    search KEY_WORDS
    ```
    
    * The `KEY_WORDS` are CASE-SENSITIVE
    
    * Quotations marks are not allowed in `KEY_WORDS`
    
    * The results will be listed according to their `INDEX`
    
    #### Examples
    
    * `search lecture`
    
        Search for all the tasks that contain keyword `lecture`, TaskTracker will
        generate a list for view.
    
    * `search training SESSION`
    
        Search for all the tasks that contain keyword `training SESSION`,
        TaskTracker will generate a list for view.
###### docs\UserGuide.md

    ### <a name="exit">Exiting the program: `exit`</a>
    
    Exits the program.
    
    Format: 
    ```
    exit
    ```
    
    ## Command Summary
    
    Command | Format
    ------------ | :---------------------------------------------------------------
    Add Floating Task | `add "FLOATING_TASK_NAME" [p-PRIORITY]`
    Add Event |`add “EVENT_NAME” <STARTING_DATE> <STARTING_TIME>  to <ENDING_DATE> <ENDING_TIME>`
    Add Deadline |`add “DEADLINE_NAME” <DATE> <TIME>`
    Delete a task | `del-float|-deadline|-event] <INDEX>`
    Edit Floating Tasks | `edit-float <INDEX> [n-NEW_NAME] [p-PRIORITY]`
    Edit Event |`edit-event <INDEX> [sd-NEW_START_DATE] [st-NEW_START_TIME] [ed-NEW_END_DATE] [et-NEW_END_TIME] [n-NEW_NAME]`
    Edit Deadline |`edit-deadline <INDEX> [dd-DUE_DATE] [dt-DUE_TIME] [n-NEW_NAME]`
    Generate recommended time slots | `slot <STARTING_DATE> <STARTING_TIME> <ENDING_DATE> <ENDING_TIME> <h-HOUR> <m-MINUTE>`
    Mark a task as finished | `fin-float|-deadline|-event <INDEX>`
    View a date in calendar | `view [DATE]`
    Search for keywords | `search KEY_WORDS`
    Help | `help`
    Undo | `undo`
    Redo | `redo`
    Clear | `clear`
    Exit | `exit`
