package planner;
import java.util.*;

public class Section implements ISection, Comparable<Section>{
    String name;

    //How is this sorted?
    Set<Task> tasks = new TreeSet<Task>();

    public Section(String name_input){
        name = name_input;
    }

    public String getName(){
        return name;
    }

    public Iterable<Task> getTasks(){
        return tasks;
    }
    
    public void addTask(Task t) throws AlreadyExistsException{
        for (Task e: tasks){
            if(e.getName() == t.getName() && e.getDescription() == t.getDescription() && e.getExpectedDuration() == t.getExpectedDuration()){
                throw new AlreadyExistsException("This task " + t.getName() + " already exists!");
            }
        }
        tasks.add(t);
    }

    public void removeTask(Task t) throws NotFoundException{
        int state = 0;
        for (Task e: tasks){
            if(e.getName() == t.getName() && e.getDescription() == t.getDescription() && e.getExpectedDuration() == t.getExpectedDuration()){
                tasks.remove(t);
                state = 1;
            }
        }
        if(state == 0){
            throw new NotFoundException("This task " + t.getName() + " does not exist, so you cannot remove it!");
        }
    }

    public int compareTo(Section p){
        return name.compareTo(p.getName());
     }
}
