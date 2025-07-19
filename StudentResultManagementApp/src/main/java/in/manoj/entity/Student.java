package in.manoj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Roll No is required")
    private String rollNumber;

    @Min(value = 0, message = "Math marks must be ≥ 0")
    @Max(value = 100, message = "Math marks must be ≤ 100")
    private int mathMarks;

    @Min(value = 0, message = "Science marks must be ≥ 0")
    @Max(value = 100, message = "Science marks must be ≤ 100")
    private int scienceMarks;

    @Min(value = 0, message = "English marks must be ≥ 0")
    @Max(value = 100, message = "English marks must be ≤ 100")
    private int englishMarks;

    private int totalMarks;
    private String grade;
    @Column(nullable = false)
    private String email;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public int getMathMarks() {
		return mathMarks;
	}
	public void setMathMarks(int mathMarks) {
		this.mathMarks = mathMarks;
	}
	public int getScienceMarks() {
		return scienceMarks;
	}
	public void setScienceMarks(int scienceMarks) {
		this.scienceMarks = scienceMarks;
	}
	public int getEnglishMarks() {
		return englishMarks;
	}
	public void setEnglishMarks(int englishMarks) {
		this.englishMarks = englishMarks;
	}
	public int getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(int totalMarks) {
		this.totalMarks = totalMarks;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", rollNumber=" + rollNumber + ", mathMarks=" + mathMarks
				+ ", scienceMarks=" + scienceMarks + ", englishMarks=" + englishMarks + ", totalMarks=" + totalMarks
				+ ", grade=" + grade + ", email=" + email + "]";
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
