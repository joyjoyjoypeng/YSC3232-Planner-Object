package planner;
import java.util.*;

public class Board implements IBoard, Comparable<Board>{
    String name;
    Set<Section> sections = new TreeSet<Section>();
    
    public Board(String name_input){
        name = name_input;
    }
    public String getName(){
        return name;
    }
    
    public Iterable<Section> getSections(){
        return sections;
    }

    public void  addSection(Section t) throws AlreadyExistsException{
        for (Section e: sections){
            if(e.getName() == t.getName()){
                throw new AlreadyExistsException("This section already exists!");
            }
        }
        sections.add(t);
    }
    public void  removeSection(Section t) throws NotFoundException{
        int state = 0;
        for (Section e: sections){
            if(e.getName() == t.getName()){
                sections.remove(t);
                state = 1;
            }
        }
        if(state == 0){
            throw new NotFoundException("This section does not exist, so you cannot remove it!");
        }    
    }
    public Section getSection(String sectionName) throws NotFoundException{
        for(Section o : sections){
            if(o.getName().equals(sectionName)){  
                return o;   
            }
        }
        throw new NotFoundException("This section does not exist, so you cannot find it!");
    }

    public int compareTo(Board p){
        return name.compareTo(p.getName());
     }
}