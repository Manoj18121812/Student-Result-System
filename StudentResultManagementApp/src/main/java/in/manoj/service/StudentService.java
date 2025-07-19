package in.manoj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.manoj.entity.Student;
import in.manoj.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public List<Student> getAll() {
        return repo.findAll();
    }

    public Student getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Student create(Student student) {
        int total = student.getMathMarks() + student.getScienceMarks() + student.getEnglishMarks();
        student.setTotalMarks(total);
        student.setGrade(calculateGrade(total));
        return repo.save(student);
    }

    public Student update(Long id, Student s) {
        Student student = getById(id);
        if (student != null) {
            student.setName(s.getName());
            student.setRollNumber(s.getRollNumber());
            student.setMathMarks(s.getMathMarks());
            student.setScienceMarks(s.getScienceMarks());
            student.setEnglishMarks(s.getEnglishMarks());
            int total = student.getMathMarks() + student.getScienceMarks() + student.getEnglishMarks();
            student.setTotalMarks(total);
            student.setGrade(calculateGrade(total));
            return repo.save(student);
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Student getByRoll(String roll) {
        return repo.findByRollNumber(roll).orElse(null);
    }

    private String calculateGrade(int total) {
        if (total >= 200) return "A";
        else if (total >=150) return "B";
        else if (total >= 100) return "C";
        else return "D";
    }
    
    public List<Student> search(String keyword){
    	return repo.findByNameContainingIgnoreCaseOrRollNumberContainingIgnoreCase(keyword, keyword);
    }
    
}
