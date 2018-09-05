package com.example.todoapp.controllers;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


import com.example.todoapp.models.User;
import com.example.todoapp.repositories.UserRepository;
import com.example.todoapp.service.UserService;
import com.querydsl.core.types.Predicate;

import static com.example.todoapp.common.ObjectMapper.mapObjectWithExcludeFilter;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;	
	
	@Value("${appUrl}")
	private String appUrl;
	@Value("${from}")
	private String from;
	@Value("${host}")
	private String host;
	

	//getAllUser
		@RequestMapping(method = RequestMethod.GET, value = "/allUser", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<User>> allUser() {
			try {
				
				List<User> savedUser = userService.findAll();
				
				
				return new ResponseEntity<List <User>>(savedUser, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		//particularUser
				@RequestMapping(method = RequestMethod.POST, value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
				public ResponseEntity<User> singleUser(@RequestBody User user) {
					try {
						User savedUser=null;
						 savedUser = userService.findByUserName(user);
						System.out.println(":::::::saved User::::;::"+savedUser.getTeamName());
						savedUser.setPassword(null);
						savedUser.setConfirmPassword(null);
						
						return new ResponseEntity<User>(savedUser, HttpStatus.OK);
					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
	
	
	//login service
	@RequestMapping(method = RequestMethod.POST, value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody User user) {
		try {
			
			User savedUser = userService.findByUserNameAndPassword(user);
			
			if (null != savedUser.getUserName()) {
				savedUser.setTeamName(null);
			}
			return new ResponseEntity<User>(savedUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//signup create new user
	
	@RequestMapping(method = RequestMethod.POST, value = "/signUp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> signUp(@RequestBody User user) {
		try {
			User savedUser = userService.findByUserName(user);
			if (null != savedUser) {
				savedUser.setPassword(null);
				System.out.println("inside if::::::"+new ResponseEntity<User>(savedUser, HttpStatus.OK).toString());
				return new ResponseEntity<User>(savedUser, HttpStatus.OK);
			}else {
				User newUser=userService.saveUser(user);			
				newUser.setPassword(null);
				System.out.println("inside else::::::"+new ResponseEntity<User>(savedUser, HttpStatus.OK).toString());
				return new ResponseEntity<User>(newUser, HttpStatus.OK);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//Change Password
	
	@RequestMapping(method = RequestMethod.PUT, value = "/updateUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePassword(@RequestBody User user) {
		try {	
			User updateUser=null;
			if(null!=user) {
				updateUser=userService.partialUpdateUser(user);
				
			}
			
			
			return new ResponseEntity<User>(updateUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	//forgot 
	@RequestMapping(method = RequestMethod.POST, value = "/forgot", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> forgot(@RequestBody User user) {
		
		try {	
			System.out.println("Inside forget password");
			User savedUser = userService.findUserByEmail(user);
			System.out.println(user.getEmailId());
			if (null == savedUser) {
				user.setUserCreated(false);
				return new ResponseEntity<User>(user, HttpStatus.OK);
			}else {
				// Generate random 36-character string token for reset password
				InternetAddress[] address = {new InternetAddress(savedUser.getEmailId())};
				savedUser.setResetToken(UUID.randomUUID().toString());
				
				// Save token to database
				userService.saveUser(savedUser);
				
				//read it from property file
				String appUrl= "http://localhost:4200";
				String from = "noreply@kpn.com";
				String host = "158.67.100.31";
				
				boolean debug = Boolean.valueOf(false).booleanValue();
				
			    Properties props = System.getProperties();
				props.put("mail.smtp.host", host);
				Session session = Session.getInstance(props, null);
				session.setDebug(debug);		
			    
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));				
				msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject("Password Reset Request");
				
				MimeBodyPart mbp1 = new MimeBodyPart();
				mbp1.setText("To reset your password, click the link below:\n\n" + appUrl + "/login?token=" + savedUser.getResetToken());

				Multipart mp = new MimeMultipart();
				mp.addBodyPart(mbp1);

				msg.setContent(mp);
				msg.setSentDate(new Date());

				Transport.send(msg);
				savedUser.setUserCreated(true);
				
				return new ResponseEntity<User>(savedUser, HttpStatus.OK);
				} 
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> reset(@RequestBody User user,@RequestHeader(name = "token") String tokenObj) {
	try {
		User savedUser = userService.findUserByResetToken(tokenObj);
		if (null == savedUser) {
			user.setUserCreated(false);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}else {
			savedUser.setPassword(user.getPassword());
			savedUser.setResetToken(null);
			userService.saveUser(savedUser);
			savedUser.setUserCreated(true);
			return new ResponseEntity<User>(savedUser, HttpStatus.OK);
		}
	}catch (Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}
	}
	
	


	
}
