/**
 * Showmyip_lookupPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Provider.Ippages_GeoIPLookup;

public interface Showmyip_lookupPortType extends java.rmi.Remote {
    public Provider.Ippages_GeoIPLookup.IP_Address_Lookup_Properties showmyip_lookup(java.lang.String lookup_ip, java.lang.String lookup_host, java.lang.String optional_registered_ddns_name, java.lang.String optional_language_code, java.lang.String optional_access_key) throws java.rmi.RemoteException;
}
