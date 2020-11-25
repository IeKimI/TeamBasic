package edu.wpi.cs.basic.demo.http;

import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;


public class GetAlternativesResponse extends GenericResponse{
	public List<AlternativeChoice> alternatives;
	
	public GetAlternativesResponse(String response, List<AlternativeChoice> alternatives) {
		super(response);
		this.alternatives = alternatives;
	}
	
	public GetAlternativesResponse(int code, String response) {
		super(response, code);
		this.alternatives = null;
	}

}