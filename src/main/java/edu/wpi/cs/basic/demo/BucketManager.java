package edu.wpi.cs.basic.demo;

public class BucketManager {
	
	// Should have other way of doing this.
	public static final String bucket = "teambasic";
	
	public static final String folder = "choice";
	public static final String test_folder = "test_choice";
	
	static String cachedResult = null;   // compute once and then retain
	
	/**
	 * Returns the folder to use, either production one (folder) or testing (test_folder).
	 */
	static String getChoiceFolder() {
		if (cachedResult != null) { return cachedResult; }    // won't change
		
		String test = System.getenv("lambdaTesting");
		if (test != null) {
			cachedResult = test_folder;
			System.out.println("USING TEST");
		} else {
			cachedResult = folder;
		}
		
		return cachedResult;
	}
	
}
