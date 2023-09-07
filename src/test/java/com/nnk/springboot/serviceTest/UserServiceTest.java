package com.nnk.springboot.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exceptions.UserException;
import com.nnk.springboot.service.UserService;

public class UserServiceTest {
	
	private UserService userService = new UserService();
	
	 	@Test
	    public void testValidPassword() {
	        User user = new User();
	        user.setPassword("ValidPassword1!");
	        assertDoesNotThrow(() -> userService.validatePasswordUser(user));
	    }

	    @Test
	    public void testShortPassword() {
	        User user = new User();
	        user.setPassword("Short1!");
	        UserException exception = assertThrows(UserException.class, () -> userService.validatePasswordUser(user));
	        assertEquals("Password must contain at least 8 characters", exception.getMessage());
	    }

	    @Test
	    public void testNoUppercaseLetter() {
	        User user = new User();
	        user.setPassword("noupper1!");
	        UserException exception = assertThrows(UserException.class, () -> userService.validatePasswordUser(user));
	        assertEquals("Password must contain at least 1 uppercase letter", exception.getMessage());
	    }

	    @Test
	    public void testNoDigit() {
	        User user = new User();
	        user.setPassword("NoDigit!");
	        UserException exception = assertThrows(UserException.class, () -> userService.validatePasswordUser(user));
	        assertEquals("Password must contain at least 1 digit", exception.getMessage());
	    }

	    @Test
	    public void testNoSpecialCharacter() {
	        User user = new User();
	        user.setPassword("NoSpecialCharacter1");
	        UserException exception = assertThrows(UserException.class, () -> userService.validatePasswordUser(user));
	        assertEquals("Password must contain at least 1 special character", exception.getMessage());
	    }

}
