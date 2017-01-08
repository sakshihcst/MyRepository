package StringProblems;

public class RotatedString {
	
	public boolean isRotated(String inputString1, String inputString2){
		String s = inputString1 + inputString1;
		if(s.contains(inputString2)){
			return true;
		}
		return false;
	}

	public static void main(String ab[]){
		RotatedString rs = new RotatedString();
		String inputString1 = "JavaSpringMongoAngular";
		String inputString2 = "MongoAngularJavaSpring";
		System.out.println(rs.isRotated(inputString1, inputString2));
	}
}
