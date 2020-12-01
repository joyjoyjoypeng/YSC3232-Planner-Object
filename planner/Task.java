package planner;


import java.util.*;
import java.time.Duration;

//visibility of the elements in the inherenting class?
interface ITask {

    String         getName();
    String         getDescription() ;
    Duration       getExpectedDuration();
    
    Iterable<Task> getSubTasks() ;
    void           addSubTask(Task t) throws AlreadyExistsException;
    void           removeSubTask(Task t) throws NotFoundException;
    
}

//connections between all classes
public class Task implements ITask,Comparable<Task> {
    String name;
    String description;
    Duration est_duration;
    Set<Task> subtasks = new HashSet<Task>();

    public Task(String name_input, String description_input, Duration duration_input){
        name = name_input;
        description = description_input;
        est_duration = duration_input;
    }

    public String getName(){
        return name;
    }   
    public String getDescription(){
        return description;
    }
    public Duration getExpectedDuration() {
        return est_duration;
    }
    
    // Does this automatically iterate over the subtasks or do I need to print out the tasks names?
    public Iterable<Task> getSubTasks(){
        return subtasks;
        }

    // .contains work? or use iterable getSubTasks
    public void addSubTask(Task t) throws AlreadyExistsException{
        for (Task e: subtasks){
            if(e.getName() == t.getName() && e.getDescription() == t.getDescription() && e.getExpectedDuration() == t.getExpectedDuration()){
                throw new AlreadyExistsException("This subtask" + t.getName() + "already exists!");
            }
        }
        subtasks.add(t);
    }

    public void removeSubTask(Task t) throws NotFoundException{
        int state = 0;
        for (Task e: subtasks){
            if(e.getName() == t.getName() && e.getDescription() == t.getDescription() && e.getExpectedDuration() == t.getExpectedDuration()){
                subtasks.remove(t);
                state = 1;
            }
        }
        if(state == 0){
            throw new NotFoundException("This subtask " + t.getName() + " does not exist, so you cannot remove it!");
        }
    }

    public int compareTo(Task t){
        return name.compareTo(t.getName());
     }
}

