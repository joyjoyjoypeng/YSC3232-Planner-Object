package planner;
import java.util.*;

public class Project implements IProject, Comparable<Project>{
    String name;
    String description;
    String deadline;
    Set<Task> tasks = new TreeSet<Task>();

    public Project(String name_input, String description_input, String deadline_input){
        name = name_input;
        description = description_input;
        deadline = deadline_input;
    }

    public Project(String name_input, String description_input){
        name = name_input;
        description = description_input;
    }
   
    public String getName(){
        return name;
    }
    
    public Iterable<Task> getTasks(){
        return tasks;
    }

    public String getdes(){
         return description;
    }

    public String getdeadline(){
        return deadline;
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

    public int compareTo(Project p){
        return name.compareTo(p.getName());
     }
    
}