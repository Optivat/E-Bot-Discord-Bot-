package essentials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Methods {

	public static void writeProp(String variable, String result) {
		try {
			OutputStream output = new FileOutputStream(System.getProperty("user.home") + "/Optivat's Inc/Discord E Bot/config.properties");
			Main.prop.setProperty(variable, result);
			Main.prop.store(output, null);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to write variable!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to store variable!");
			e.printStackTrace();
		}
	}
	public static void createPropFile(String dire) {
		File file = new File(System.getProperty("user.home") + dire);
		if(file.getParentFile().mkdirs()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Creation of file has failed!");
				e.printStackTrace();
			}
		} else {
			throw new Error("Failed to create directory " + file.getParent());
		}
	}
	public static boolean testFileDire(String dire) {
		File file = new File(dire);
			if(file.isFile()) {
				return true;
			} else {
				return false;
			}
	}
	public static boolean testIfLettersInString(String string) {
		int len = string.length();
		 for (int i = 0; i < len; i++) {
	         if (Character.isLetter(string.charAt(i)) == false) {
	            return false;
	         }
	      }
		 return true;
	}
}
