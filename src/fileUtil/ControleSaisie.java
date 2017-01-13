package fileUtil;

public class ControleSaisie {

	public static int isInteger(String s){
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
