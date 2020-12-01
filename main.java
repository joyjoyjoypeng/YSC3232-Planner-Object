import java.time.Duration;
import planner.*; 

class main {
   public static void main(String[] args) throws AlreadyExistsException, NotFoundException{
      //Initialize all objects 
      Planner joyPlanner = new Planner();
      Project ysc3232_p = new Project("YSC3232", "Joy's homework");
      Task T1 = new Task("T1", "read textbook", Duration.ofMinutes(10));
      Task T2 = new Task("T2", "finish the quiz", Duration.ofMinutes(10));
      Task T3 = new Task("T3", "write a game", Duration.ofMinutes(10));
      Board ysc3232_b = new Board("YSC3232");
      Section todo = new Section("TODO");
      Section done = new Section("Done");
      
      //Add objects into the planner
      joyPlanner.addProject(ysc3232_p);
      ysc3232_p.addTask(T1);
      ysc3232_p.addTask(T2);
      ysc3232_p.addTask(T3);
      joyPlanner.addBoard(ysc3232_b);
      ysc3232_b.addSection(todo);
      ysc3232_b.addSection(done);
      todo.addTask(T1);
      todo.addTask(T2);
      done.addTask(T3);
      T1.addSubTask(new Task("download textbook", "look on canva", Duration.ofSeconds(20)));

      //produce an XML from the existing planner
      String xml = joyPlanner.writeXMLData();

      //create another planner and read the xml data produced from the first planner
      Planner joyPlanner2 = new Planner();
      joyPlanner2.readXMLData(xml);
      
      //produce an XML from the second planner and compare it against the XML from the first planner
      String xml2 = joyPlanner2.writeXMLData();
      System.out.println(xml2.equals(xml));
   }
   
}

