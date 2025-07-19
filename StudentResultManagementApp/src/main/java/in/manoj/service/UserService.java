package in.manoj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.manoj.entity.User;
import in.manoj.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	public boolean registerUser(User user) {
		
		User existingUser = userRepo.findByEmail(user.getEmail());
		
		if (existingUser != null) {
            return false;
        }

        userRepo.save(user);
        return true;
    }
	
	public User login(String email,String password) {
		User user = userRepo.findByEmail(email);
		if (user != null && user.getPassword().equals(password)) {
	        return user;
	    }
		return null;
	}
	
	
	public boolean resetPassword(String email,String newPassword) {
		User user = userRepo.findByEmail(email);
		
		if(user!=null) {
			user.setPassword(newPassword);
			userRepo.save(user);
			return true;
		}
		return false;
	}
}
