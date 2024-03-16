package com.alarms.demo;

import java.security.Principal;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import jakarta.annotation.security.RolesAllowed;

@RestController
	@RequestMapping("/")
	public class TestController {

	  @GetMapping("/a")
	  public ResponseEntity<String> getAnonymous() {
	    return ResponseEntity.ok("Anonymous response");
	  }

	  @RolesAllowed("iot-read") // definiranje uloge koja je potrebna za ovaj upit
	  @GetMapping("/read")
	  public ResponseEntity<String> getUser() {
	      return ResponseEntity.ok("Reading response");
	  }

	  @RolesAllowed("iot-write")
	  @GetMapping("/write")
	  public ResponseEntity<String> getAdmin() {
	      return ResponseEntity.ok("Writing response");
	  }

	  @RolesAllowed({"iot-write", "iot-read"})
	    @GetMapping("/info")
	    public ResponseEntity<String> getInfo(Authentication authentication) {
	        StringBuilder sb = new StringBuilder();

	        sb.append("Principal: ");
	        sb.append(authentication.getName());
	        sb.append("\nAuthorities:\n");

	        sb.append(authentication.getAuthorities().stream()
	                .map(GrantedAuthority::getAuthority)
	                .collect(Collectors.joining("\n  ", "  ", "\n")));

	        return ResponseEntity.ok("Info response\n" + sb.toString());
	    }
	}


