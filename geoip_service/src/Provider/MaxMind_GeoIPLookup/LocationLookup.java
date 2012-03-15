package Provider.MaxMind_GeoIPLookup;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.lang.*;

import java.io.*;
import java.util.*;

/**
 * AddressLookup is a simple class that uses MaxMind's GeoIPLookup service to resolve an IP address to a
 * street address, etc. More details are provided here:
 * <a href="http://www.maxmind.com/app/web_services_guide#cityisporg">MaxMind site</a>.
 *
 * @author Nazmul Idris
 * @version 1.0n
 * @since Mar 23, 2008, 2:51:57 PM
 */
public class LocationLookup {

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// data
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public static final String MaxMindUri = "http://geoip1.maxmind.com/f";
public static String License = "";
public static final String IpAddressUriKey = "i";
public static final String LicenseUriKey = "l";

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// methods
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public static void setLicenseKey(String lic) {
  License = lic;
}

public static IpAddressLookupData performLookup(String ipaddress)
    throws IllegalArgumentException, IOException, LookupException
{
  Validate.notNull(ipaddress, "ipaddress can not be null");

  HashMap<String, String> map = new HashMap<String, String>();
  map.put(LicenseUriKey, License);
  map.put(IpAddressUriKey, ipaddress);
  String uri = Utils.buildUri(MaxMindUri, map);

  System.out.println(uri);

  IpAddressLookupData data = _getResponse(uri);

  if (data.containsError()) {
    throw new LookupException("IP address=" + ipaddress + ", error or warning= " + data.getError());
  }
  else {
    return data;
  }
}

/** use HttpClient to make a HTTP request to the uri and get the response as a string */
private static IpAddressLookupData _getResponse(String uri) throws IOException {
  Validate.notEmpty(uri, "uri can not be empty or null");

  HttpClient client = new HttpClient();
  GetMethod get = new GetMethod(uri);

  client.executeMethod(get);

  String respStr = get.getResponseBodyAsString();

  return new IpAddressLookupData(respStr);
}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
// self test method
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public static void main(String[] args) throws IOException, LookupException {

  // make sure to set a valid license key!
  setLicenseKey("valid key here");
  IpAddressLookupData addressLookup = performLookup("24.24.24.24");
  System.out.println(Utils.mapToString(addressLookup.getData()));

}

private static class LookupException extends Exception {
  public LookupException(String s) {
    super(s);
  }
}

}//end class LocationLookup
