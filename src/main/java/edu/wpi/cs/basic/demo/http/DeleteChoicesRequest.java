package edu.wpi.cs.basic.demo.http;

public class DeleteChoicesRequest {

		public float nDaysOld;
		
		public void setNDaysOld(float nDaysOld) {this.nDaysOld = nDaysOld; }
		public float getNDaysOld() {return nDaysOld; }
		
		public DeleteChoicesRequest (float nDaysOld) {
			this.nDaysOld = nDaysOld;
		}

		public DeleteChoicesRequest() {
			
		}
		
		public String toString() {
			return "Delete(" + nDaysOld + ")";
		}
	}
