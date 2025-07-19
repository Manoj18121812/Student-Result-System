package in.manoj.controller;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.manoj.entity.Student;
import in.manoj.service.EmailService;
import in.manoj.service.StudentService;
import in.manoj.util.StudentPdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class StudentController {
	@Autowired
    private StudentService service;

	@Autowired
	private EmailService emailService;
    @GetMapping("/")
    public String home(@RequestParam(name="keyword",required=false) String keyword ,Model model,HttpSession session) {
        
    	if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
    	
    	if(keyword!=null && !keyword.trim().isEmpty()) {
        	model.addAttribute("students", service.search(keyword));
        }else {
        	model.addAttribute("students", service.getAll());
        }
    	
    	model.addAttribute("keyword", keyword);
        return "home";
    }

    @GetMapping("/add")
    public String addForm(Model model,HttpSession session) {
    	if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
    	
    	model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Student student,BindingResult result,Model model) {
        
    	if(result.hasErrors()){
    	return "add-student";	
    	}
    	service.create(student);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model,HttpSession session) {
    	if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
    	
    	model.addAttribute("student", service.getById(id));
        return "edit-student";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Student student,BindingResult result ,Model model) {
        
    	if(result.hasErrors()) {
    		return "edit-student";
    	}
    	service.update(id, student);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,HttpSession session) {
        
    	if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
    	
    	service.delete(id);
        return "redirect:/";
    }
    
    @GetMapping("/export/pdf")
    public void exportPdf(HttpServletResponse response) throws IOException{
    	response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=students.pdf");
    	
    	List<Student> list = service.getAll();
    	
    	StudentPdfExporter exporter = new StudentPdfExporter(list);
    	exporter.export(response);
    	
    }
   
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/send-email/{id}")
    public String sendEmail(@PathVariable Long id,RedirectAttributes redirectAttributes) {
    	
    	Student student = service.getById(id);
    	
    	if(student !=null && student.getEmail()!=null) {
    		String to = student.getEmail();
            String subject = "Student Result - " + student.getName();
            String body = "Hello " + student.getName() + ",\n\n"
                    + "Here are your result details:\n"
                    + "Roll No: " + student.getRollNumber() + "\n"
                    +"English Marks :"+student.getEnglishMarks()+"\n\n"
                    +"Math Marks :"+student.getMathMarks()+"\n\n"
                    +"Science Marks :"+student.getScienceMarks()+"\n\n"
                    + "Total Marks: " + student.getTotalMarks() + "\n"
                    + "Grade: " + student.getGrade() + "\n\n"
                    + "Regards,\nStudent Result Management System";
            
            emailService.sendEmail(to, subject, body);
            redirectAttributes.addFlashAttribute("msg", "Email sent to " + to);
    	}else {
            redirectAttributes.addFlashAttribute("error", "Email address not available for student");
        }
    	return "redirect:/";
    }
   
    	@GetMapping("/send-all-emails")
       public String sendEmailToAllStudents(RedirectAttributes redirectAttributes) {
    	   
    	   List<Student> students =service.getAll();
    	   int sentCount =0;
    	   
    	   for(Student student :students) {
    		   if (student.getEmail() != null && !student.getEmail().isEmpty()) {
    	            String to = student.getEmail();
    	            String subject = "Student Result - " + student.getName();
    	            String body = "Hello " + student.getName() + ",\n\n"
    	                    + "Here are your result details:\n"
    	                    + "Roll No: " + student.getRollNumber() + "\n"
    	                    + "Grade: " + student.getGrade() + "\n\n"
    	                    +"English Marks :"+student.getEnglishMarks()+"\n\n"
    	                    +"Math Marks :"+student.getMathMarks()+"\n\n"
    	                    +"Science Marks :"+student.getScienceMarks()+"\n\n"
    	                    + "Total Marks: " + student.getTotalMarks() + "\n"
    	                    + "Regards,\nStudent Result Management System";

    	            emailService.sendEmail(to, subject, body);
    	            sentCount++;
    	   }
    	   }
    	   redirectAttributes.addFlashAttribute("msg", sentCount + " emails sent successfully.");
    	   return "redirect:/";
       }
    }

















/*
@Controller
public class StudentController {
	@Autowired
    private StudentService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("students", service.getAll());
        return "home";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute
    		Student student) {
        service.create(student);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", service.getById(id));
        return "edit-student";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Student student) {
        service.update(id, student);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}*/
