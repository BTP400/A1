package Provider.MaxMind_GeoIPLookup;

import org.apache.commons.lang.*;

import java.io.*;
import java.util.*;

/**
 * IpAddressLookupData contains the following fields, which are retrieved from MaxMind:
 * <ol>
 * <li>ISO 3166 Two-letter Country Code,
 * <li>Region Code,
 * <li>City,
 * <li>Postal Code,
 * <li>Latitude,
 * <li>Longitude,
 * <li>Metropolitan Code,
 * <li>Area Code,
 * <li>ISP,
 * <li>Organization,
 * <li>Error code
 * </ol>
 *
 * @author Nazmul Idris
 * @version 1.0
 * @since Mar 23, 2008, 3:08:47 PM
 */
public class IpAddressLookupData implements Serializable {
static final long serialVersionUID = 8304883843659847079L;

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// constants
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public enum Keys {
  CountryCode,
  RegionCode,
  City,
  PostalCode,
  Latitude,
  Longitude,
  MetropolitanCode,
  AreaCode,
  ISP,
  Organization,
  ErrorCode
}

public enum ErrorCodeType {
  //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
  // Warnings
  //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
  WarningIpNotFound("IP_NOT_FOUND"),
  WarningCountryNotFound("COUNTRY_NOT_FOUND"),
  WarningCityNotFound("CITY_NOT_FOUND"),
  WarningCityRequired("CITY_REQUIRED"),
  WarningPostalCodeRequired("POSTAL_CODE_REQUIRED"),
  WarningPostalCodeNotFound("POSTAL_CODE_NOT_FOUND"),

  //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
  // Fatal Errors
  //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
  ErrorInvalidLicenseKey("INVALID_LICENSE_KEY"),
  ErrorMaxRequestsPerLicense("MAX_REQUESTS_PER_LICENSE"),
  ErrorIpRequired("IP_REQUIRED"),
  ErrorLicenseRequired("LICENSE_REQUIRED"),
  ErrorCountryRequired("COUNTRY_REQUIRED"),
  ErrorMaxRequestsReached("MAX_REQUESTS_REACHED");

  ErrorCodeType(String msg) { errMsg = msg; }
  public String errMsg;
  public String getMsg() { return errMsg; }
  /**
   * if there is no match, then returns null
   *
   * @return might be null
   */
  public static ErrorCodeType match(String key) {
    Validate.notEmpty(key, "key can not be empty or null");

    for (ErrorCodeType type : values()) {
      if (type.getMsg().equals(key)) return type;
    }

    return null;
  }
}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// data
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
private HashMap<Keys, String> _data = new HashMap<Keys, String>();

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// constructor
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public IpAddressLookupData(String data) {
  _data = parseMaxMindResponse(data).getData();
}

public IpAddressLookupData() {}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// parse method
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
/**
 * given a string response from MaxMind, parse it into this data model
 *
 * @param cdfData comma delimited format containing all the fields
 */
public static IpAddressLookupData parseMaxMindResponse(String cdfData) throws IllegalArgumentException {

  Validate.notEmpty(cdfData, "cdfData can not be empty or null");

  IpAddressLookupData data = new IpAddressLookupData();

  StringTokenizer st = new StringTokenizer(cdfData, ",");

  if (st.countTokens() == 1) {
    // error condition
    data._data.put(Keys.ErrorCode, st.nextToken());
  }
  else {
    // data is good, no error or warning
    int i = 0;

    while (st.hasMoreTokens()) {
      Keys key = Keys.values()[i];
      String val = st.nextToken();
      ScannerEx quotScanner = new ScannerEx(val);
      if (quotScanner.hasNextQuotedString()) {
        val = quotScanner.nextQuotedString();
      }

      data._data.put(key, val);

      i++;
    }
  }

  return data;

}

public HashMap<Keys, String> getData() {
  return _data;
}

public boolean containsData() {
  if (containsError()) return false;

  if (!_data.values().isEmpty()) return true;

  return false;
}

public boolean containsError() {
  if (getError() != null) {
    return true;
  }
  else {
    return false;
  }
}

/** if there is an error or warning code in this data, this will retrieve it */
public ErrorCodeType getError() {
  if (_data.containsKey(Keys.ErrorCode)) {
    String ecStr = _data.get(Keys.ErrorCode);
    return ErrorCodeType.match(ecStr);
  }
  return null;
}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// accessors for frequently accessed fields
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public double getLatitude() throws IllegalArgumentException {

  return _getDouble(Keys.Latitude);

}

public double getLongitude() throws IllegalArgumentException {

  return _getDouble(Keys.Longitude);

}

private double _getDouble(Keys key) {
  try {
    return Double.parseDouble(getData().get(key));
  }
  catch (Exception e) {
    throw new IllegalArgumentException(
        "There is something wrong with the +" + getClass().getSimpleName() + " object. Could not parse: " + key);
  }
}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// debug
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public String toHTML() {
  return toHTML("");
}

public String toHTML(String msg) {
  StringBuilder sb = new StringBuilder();

  sb.append("<html>");

  if (!msg.equals("")) sb.append(msg).append("<br>");

  sb.
      append("<b>").append(getClass().getSimpleName()).append("</b>").
      append(" data:<br>");

  for (Keys key : Keys.values()) {

    if (_data.containsKey(key)) {
      sb.
          append("&nbsp;&nbsp;--&gt;").append(key).append("=").
          append(_data.get(key)).append("<br>");
    }

  }

  sb.append("</html>");

  return sb.toString();
}

public String toString() {
  StringBuilder sb = new StringBuilder();

  sb.append(getClass().getSimpleName()).append(" data:\n");

  for (Keys key : Keys.values()) {

    if (_data.containsKey(key)) {
      sb.
          append("\t-->").append(key).append("=").
          append(_data.get(key)).append("\n");
    }

  }

  return sb.toString();
}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// self test method
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public static final String ValidDataString =
    "US,NY,New York,10011,40.742100,-74.001801,501,212,\"Road Runner\",\"Road Runner\"";
public static final String ErrorDataString = ",,,,,,,,,,INVALID_LICENSE_KEY";
public static final String WarningDataString = ",,,,,,,,,,IP_NOT_FOUND";

/**
 * getting data from this sample service -
 * <a href="http://geoip1.maxmind.com/f?l=<License>&i=24.24.24.24">MaxMind test service</a>
 */
public static void main(String[] args) {

  // valid data
  _parseData("valid data", ValidDataString);

  // error data
  _parseData("error data", ErrorDataString);

  // warning data
  _parseData("warning data", WarningDataString);

}
private static void _parseData(String msg, String dataFromMaxMind) {
  System.out.println(msg);
  System.out.println("RAW DATA:" + dataFromMaxMind);
  IpAddressLookupData data = IpAddressLookupData.parseMaxMindResponse(dataFromMaxMind);
  System.out.println(data.toString());

  System.out.println("containsData=" + data.containsData());
  System.out.println("containsError=" + data.containsError());
  System.out.println("error=" + data.getError());

}

}//end class IpAddressLookupData