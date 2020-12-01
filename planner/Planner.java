package planner;

import java.util.*;
import java.time.Duration;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.StringWriter;
import java.io.StringReader;
import org.xml.sax.InputSource;



interface IPlanner {
    void addBoard(Board b) throws AlreadyExistsException;

    void addProject(Project p) throws AlreadyExistsException;

    Iterable<Board> getBoards();

    Iterable<Project> getProjects();

    public String writeXMLData();

    public void readXMLData(String data);
}

public class Planner implements IPlanner {
    
    //I made all of the lists treesets so DOM stores and returns the objects in the same order every time we generate an XML data.
    Set<Board> boards = new TreeSet<Board>();
    Set<Project> projects = new TreeSet<Project>();
    
    public Planner() {
    }

    public void addBoard(Board b) throws AlreadyExistsException {
        for (Board e : boards) {
            if (e.getName() == b.getName()) {
                throw new AlreadyExistsException("This subtask already exists!");
            }
        }
        boards.add(b);
    }

    public void addProject(Project p) throws AlreadyExistsException {
        for (Project e : projects) {
            if (e.getName() == p.getName()) {
                throw new AlreadyExistsException("This task " + p.getName() + " already exists!");
            }
        }
        projects.add(p);
    }

    public Iterable<Board> getBoards() {
        return boards;
    }

    public Iterable<Project> getProjects() {
        return projects;
    }

    public String writeXMLData() {

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element root = document.createElement("Planner");
            document.appendChild(root);

            for (Project p : this.getProjects()) {
                Element project = document.createElement("Project");
                root.appendChild(project);

                Attr proj_name = document.createAttribute("name");
                proj_name.setTextContent(p.getName());
                project.setAttributeNode(proj_name);

                Attr proj_description = document.createAttribute("descriptions");
                proj_description.setTextContent(p.getdes());
                project.setAttributeNode(proj_description);

                Attr proj_deadline = document.createAttribute("deadline");
                proj_deadline.setTextContent(p.getdeadline());
                project.setAttributeNode(proj_deadline);

                for (Task t : p.getTasks()) {
                    Element task1 = document.createElement("Task");
                    project.appendChild(task1);

                    Attr task1_name = document.createAttribute("name");
                    task1_name.setTextContent(t.getName());
                    task1.setAttributeNode(task1_name);

                    Attr task1_desc = document.createAttribute("description");
                    task1_desc.setTextContent(t.getDescription());
                    task1.setAttributeNode(task1_desc);

                    Attr task1_time = document.createAttribute("estimated_duration");
                    task1_time.setTextContent(t.getExpectedDuration().toString());
                    task1.setAttributeNode(task1_time);

                    for (Task st : t.getSubTasks()) {
                        Element task2 = document.createElement("Sub_task");
                        task1.appendChild(task2);
    
                        Attr task2_name = document.createAttribute("name");
                        task2_name.setTextContent(st.getName());
                        task2.setAttributeNode(task2_name);
    
                        Attr task2_desc = document.createAttribute("description");
                        task2_desc.setTextContent(st.getDescription());
                        task2.setAttributeNode(task2_desc);
    
                        Attr task2_time = document.createAttribute("estimated_duration");
                        task2_time.setTextContent(st.getExpectedDuration().toString());
                        task2.setAttributeNode(task2_time);
                    }
                }
            }

            for (Board b : this.getBoards()) {
                Element board = document.createElement("Board");
                root.appendChild(board);
                Attr BoardName = document.createAttribute("name");
                BoardName.setTextContent(b.getName());
                board.setAttributeNode(BoardName);

                for (Section s : b.getSections()) {
                    Element section = document.createElement("Section");
                    board.appendChild(section);

                    Attr sectionName = document.createAttribute("name");
                    sectionName.setTextContent(s.getName());
                    section.setAttributeNode(sectionName);

                    for (Task t : s.getTasks()) {
                        Element task1 = document.createElement("Task");
                        section.appendChild(task1);
                        Attr task1_name = document.createAttribute("name");
                        task1_name.setTextContent(t.getName());
                        task1.setAttributeNode(task1_name);
                        Attr task1_desc = document.createAttribute("description");
                        task1_desc.setTextContent(t.getDescription());
                        task1.setAttributeNode(task1_desc);
                        Attr task1_time = document.createAttribute("estimated_duration");
                        task1_time.setTextContent(t.getExpectedDuration().toString());
                        task1.setAttributeNode(task1_time);

                        for (Task st : t.getSubTasks()) {
                            Element task2 = document.createElement("Sub_task");
                            task1.appendChild(task2);
        
                            Attr task2_name = document.createAttribute("name");
                            task2_name.setTextContent(st.getName());
                            task2.setAttributeNode(task2_name);
        
                            Attr task2_desc = document.createAttribute("description");
                            task2_desc.setTextContent(st.getDescription());
                            task2.setAttributeNode(task2_desc);
        
                            Attr task2_time = document.createAttribute("estimated_duration");
                            task2_time.setTextContent(st.getExpectedDuration().toString());
                            task2.setAttributeNode(task2_time);
                        }
                    }

                }
            }
            

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
        
            //StreamResult result = new StreamResult(new File("./planner.xml"));
            //transformer.transform(source, result);
            
            StringWriter writer = new StringWriter();
            StreamResult result2 = new StreamResult(writer);
            transformer.transform(source, result2);
            String strResult = writer.toString();

            return (strResult);

        } 
        catch (Exception e) {
            e.printStackTrace();
            return ("Please revise");
        }
        
    }

    public void readXMLData(String data){

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(data)));

            Element root = document.getDocumentElement();

            System.out.println("Root element : " + document.getDocumentElement().getNodeName());

            NodeList list = root.getChildNodes();

            for(int j = 0; j < list.getLength(); j++){
                String listType = ((Element) list.item(j)).getTagName();
                System.out.println("    "+listType+":");
                String typeName = ((Element) list.item(j)).getAttribute("name");
                
                if(listType.equals("Project")){ 
                    System.out.println("        Name = " + typeName);
                    String proj_desc = ((Element) list.item(j)).getAttribute("descriptions");
                    System.out.println("        Description = "  + proj_desc);

                    String proj_deadline = ((Element) list.item(j)).getAttribute("deadline");
                    System.out.println("        Deadline = "  + proj_deadline);

                    Project project = new Project(typeName, proj_desc, proj_deadline);
                    this.addProject(project);

                    NodeList task_p = list.item(j).getChildNodes();

                    for (int i = 0; i < task_p.getLength(); i++) {
                        System.out.println("        " + ((Element) task_p.item(i)).getTagName()+":");
                        String task_name = ((Element) task_p.item(i)).getAttribute("name");
                        System.out.println("            Name = " + task_name);
                        String task_desc = ((Element) task_p.item(i)).getAttribute("description");
                        System.out.println("            Description = " + task_desc);
                        String task_est = ((Element) task_p.item(i)).getAttribute("estimated_duration");
                        System.out.println("            Estimated Duration = " + task_est);
                        
                        Duration duration = Duration.parse(task_est);
                        Task task = new Task(task_name, task_desc, duration);
                        project.addTask(task);

                        NodeList subtask_p = task_p.item(i).getChildNodes();

                        for (int k = 0; k < subtask_p.getLength(); k++) {
                            System.out.println("            " + ((Element) subtask_p.item(k)).getTagName()+":");
                            String subtask_name = ((Element) subtask_p.item(k)).getAttribute("name");
                            System.out.println("                Name = " + subtask_name);
                            String subtask_desc = ((Element) subtask_p.item(k)).getAttribute("description");
                            System.out.println("                Description = " + subtask_desc);
                            String subtask_est = ((Element) subtask_p.item(k)).getAttribute("estimated_duration");
                            System.out.println("                Estimated Duration = " + subtask_est);
                            
                            Duration subduration = Duration.parse(subtask_est);
                            Task subtask = new Task(subtask_name, subtask_desc, subduration);
                            task.addSubTask(subtask);
                        }
                    }
                }

                if(listType.equals("Board")){
                    System.out.println("    Name = " + typeName+":");

                    Board board = new Board(typeName);
                    this.addBoard(board);

                    NodeList sections = list.item(j).getChildNodes();

                    for (int i = 0; i < sections.getLength(); i++) {
                        System.out.println("        " + ((Element) sections.item(i)).getTagName()+":");
                        String sec_name = ((Element) sections.item(i)).getAttribute("name");
                        System.out.println("        Name = " + sec_name);

                        Section sec = new Section(sec_name);
                        board.addSection(sec);
                    

                        NodeList task_b = sections.item(i).getChildNodes();

                        for (int b = 0; b < task_b.getLength(); b++) {
                            System.out.println("                " + ((Element) task_b.item(b)).getTagName()+":");
                            String task_name = ((Element) task_b.item(b)).getAttribute("name");
                            System.out.println("                Name = " + task_name);
                            String task_desc = ((Element) task_b.item(b)).getAttribute("description");
                            System.out.println("                Description = " + task_desc);
                            String task_est = ((Element) task_b.item(b)).getAttribute("estimated_duration");
                            System.out.println("                Estimated Duration = " + task_est);

                            Duration duration = Duration.parse(task_est);
                            Task task = new Task(task_name, task_desc, duration);
                            sec.addTask(task);

                            NodeList subtask_b = task_b.item(b).getChildNodes();

                            for (int k = 0; k < subtask_b.getLength(); k++) {
                                System.out.println("                " + ((Element) subtask_b.item(k)).getTagName()+":");
                                String subtask_name = ((Element) subtask_b.item(k)).getAttribute("name");
                                System.out.println("                    Name = " + subtask_name);
                                String subtask_desc = ((Element) subtask_b.item(k)).getAttribute("description");
                                System.out.println("                    Description = " + subtask_desc);
                                String subtask_est = ((Element) subtask_b.item(k)).getAttribute("estimated_duration");
                                System.out.println("                    Estimated Duration = " + subtask_est);
                                
                                Duration subduration = Duration.parse(subtask_est);
                                Task subtask = new Task(subtask_name, subtask_desc, subduration);
                                task.addSubTask(subtask);
                            }
                        }
                    }
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}


