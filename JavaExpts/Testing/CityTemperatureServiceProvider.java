
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

public class CityTemperatureServiceProvider {

	static boolean doMock = true;

	public static void turnOnMockMode() {
		doMock = true;
	}

	public static void turnOffMockMode() {
		doMock = false;
	}

	public static String lookup(String cityString) {
		String rawText = null;
		if (doMock == false) {
			try {
				String wUrl = "http://www.google.com/ig/api?weather=" + cityString;
				rawText = Resources.toString(new URL(wUrl), Charsets.UTF_8);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return "MalformedURLException";
			} catch (IOException e) {
				e.printStackTrace();
				return "IOException";
			}
		} else {
          rawText = "...<condition data=\"Overcast\"/><temp_f data=\"451\"/><temp_c data=\"23\"/>...";
		}
		String anchor1 = "temp_f data=\"";
		String anchor2 = "\"/><temp_c";
		int offset = rawText.indexOf(anchor1) + anchor1.length();
		int end = rawText.indexOf(anchor2);
		String temperature = rawText.substring(offset, end);
		return temperature;
	}

}
