package seedu.address.logic;


import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.TaskBook;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.EventTask;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Task;
import seedu.address.storage.Storage;



/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final Parser parser;
    
    //for undo
    public static Stack<TaskBook> stateStack=new Stack<TaskBook>();//a stack of past TaskBook states
    public static final Stack<Command> modifyingDataCommandHistory=new Stack<Command>();
    
    //for redo
    public static Stack <Model> undoneStates = new Stack<Model>();
    public static Stack<Command> undoneCommands=new Stack<Command>();
    
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText) {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        
        if(command.modifiesData){
        	recordStateBeforeChange(model, command);
        	undoneCommands=new Stack<Command>();
        	undoneStates = new Stack<Model>(); 
        }
        
        command.setData(model);
        return command.execute();
    }
    
    private void recordStateBeforeChange(Model model, Command command){
    	TaskBook state = new TaskBook(model.getTaskBook());
    	stateStack.push(state);
    	modifyingDataCommandHistory.push(command);
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<FloatingTask> getFilteredFloatingTaskList() {
        return model.getFilteredFloatingTaskList();
    }

    @Override
    public ObservableList<EventTask> getFilteredEventTaskList() {
        return model.getFilteredEventTaskList();
    }

    @Override
    public ObservableList<DeadlineTask> getFilteredDeadlineTaskList() {
        return model.getFilteredDeadlineTaskList();
    }

}
