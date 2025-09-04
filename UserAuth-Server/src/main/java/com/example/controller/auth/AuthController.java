package com.example.controller.auth;

import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthenticationRequest;
import com.example.dto.AuthenticationResponse;
import com.example.dto.SignupRequest;
import com.example.dto.UserDto;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.services.auth.AuthService;
import com.example.services.jwt.UserService;
import com.example.utils.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")

public class AuthController {

	private final AuthService authService;
	
	private final UserRepository userRepository;
	
	private final JwtUtil jwtUtil;
	private final UserService userService;
	
	private final AuthenticationManager authenticationManager;
		

	
	
	@PostMapping("/signup")
	public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequest signupRequest)
	{
	
		if(authService.hasUserWithEmail(signupRequest.getEmail()))
		{
			
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user already exist with this email");
		}
		UserDto createdUserDto=authService.signupUser(signupRequest);
		if(createdUserDto==null)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
	}
	

	

	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest)
	{
		try  //that user with that email and pass exist in our db 
		{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
					(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		}catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect password or email!");
		}
		    final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
			Optional<User>optionalUser=	userRepository.findFirstByEmail(authenticationRequest.getEmail());
			final String jwtToken=jwtUtil.generateToken(userDetails);
			AuthenticationResponse authenticationResponse=new AuthenticationResponse();
			if(optionalUser.isPresent())
			{
				authenticationResponse.setJwt(jwtToken);
				authenticationResponse.setUserId(optionalUser.get().getId());
				authenticationResponse.setUserRole(optionalUser.get().getUserRole());
			}
			return  authenticationResponse;
	}


	
}
