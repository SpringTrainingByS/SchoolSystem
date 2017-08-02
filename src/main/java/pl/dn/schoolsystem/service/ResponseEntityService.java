package pl.dn.schoolsystem.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseEntityService {
	
	public ResponseEntity prepareErrorResponse(String message) {
		HttpHeaders responseHeaders = new HttpHeaders(); 
		responseHeaders.set("error_message" , message);
		
		return new ResponseEntity<>("", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
