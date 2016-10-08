package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.task.Task;

/**
 * An Immutable TaskBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableTaskBook implements ReadOnlyTaskBook {

    @XmlElement
    private List<XmlAdaptedTask> persons;

    {
        persons = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableTaskBook() {}

    /**
     * Conversion
     */
    public XmlSerializableTaskBook(ReadOnlyTaskBook src) {
        persons.addAll(src.getTasks().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<Task> getTasks() {
        final ObservableList<Task> out = FXCollections.observableArrayList();
        for (final XmlAdaptedTask task : persons) {
            try {
                out.add(task.toModelType());
            } catch (IllegalValueException e) {
                e.printStackTrace();
                // TODO: better error handling
                return null;
            }
        }
        return out;
    }

}
