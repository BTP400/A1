package Provider.Ippages_GeoIPLookup;

import org.apache.commons.lang.builder.*;

/**
 * Lookup is a simple class that uses the ippages.com GeoIP lookup web service. Given an IP address, it
 * returns a {@link IP_Address_Lookup_Properties} object, which contains all the geographic information
 * available on the given IP address.
 *
 * @author Nazmul Idris
 * @version 1.0
 * @since Dec 18, 2007, 7:36:46 PM
 */
public class LocationLookup {

/**
 * Simple driver for this class. Takes an input from the command line, which is an IP address and
 * tries to perform a lookup to get the location information associated with the IP address.
 *
 * @param args IP address that you want to get location information on
 *
 * @throws Exception if there are issues with service invocation, exceptions are thrown
 */
public static void main(String[] args) throws Exception {
  String ipaddress = (args.length == 0)
                     ? "64.41.145.134"
                     : args[0];

  IP_Address_Lookup_Properties locationInfo = lookupLocationForIPAddress(ipaddress);

  String prettyPrint = ToStringBuilder.reflectionToString(locationInfo, ToStringStyle.MULTI_LINE_STYLE);

  System.out.println(prettyPrint);
}

/**
 * This is the actual method which calls the SOAP service on ippages.com. The classes used by this
 * method are all generated from the WSDL provided by ippages.com, using Axis1.4.
 *
 * @param ipaddr IP address you want to get info on
 *
 * @return location information
 */
public static IP_Address_Lookup_Properties lookupLocationForIPAddress(String ipaddr) throws Exception {
  Showmyip_lookupBindingStub binding;

  binding = (Showmyip_lookupBindingStub)
      new Showmyip_lookupLocator().getshowmyip_lookupPort();

  // Time out after a minute
  binding.setTimeout(60000);

  // Perform Lookup
  IP_Address_Lookup_Properties value = null;

  value = binding.showmyip_lookup(ipaddr, "", "", "", "");

  return value;
}

}//end class Lookup
