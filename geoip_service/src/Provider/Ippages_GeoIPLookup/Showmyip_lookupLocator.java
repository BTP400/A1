/**
 * Showmyip_lookupLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Provider.Ippages_GeoIPLookup;

public class Showmyip_lookupLocator extends org.apache.axis.client.Service implements Provider.Ippages_GeoIPLookup.Showmyip_lookup {

    public Showmyip_lookupLocator() {
    }


    public Showmyip_lookupLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Showmyip_lookupLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for showmyip_lookupPort
    private java.lang.String showmyip_lookupPort_address = "http://www.ippages.com/soap/lookupserver.php";

    public java.lang.String getshowmyip_lookupPortAddress() {
        return showmyip_lookupPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String showmyip_lookupPortWSDDServiceName = "showmyip_lookupPort";

    public java.lang.String getshowmyip_lookupPortWSDDServiceName() {
        return showmyip_lookupPortWSDDServiceName;
    }

    public void setshowmyip_lookupPortWSDDServiceName(java.lang.String name) {
        showmyip_lookupPortWSDDServiceName = name;
    }

    public Provider.Ippages_GeoIPLookup.Showmyip_lookupPortType getshowmyip_lookupPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(showmyip_lookupPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getshowmyip_lookupPort(endpoint);
    }

    public Provider.Ippages_GeoIPLookup.Showmyip_lookupPortType getshowmyip_lookupPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            Provider.Ippages_GeoIPLookup.Showmyip_lookupBindingStub _stub = new Provider.Ippages_GeoIPLookup.Showmyip_lookupBindingStub(portAddress, this);
            _stub.setPortName(getshowmyip_lookupPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setshowmyip_lookupPortEndpointAddress(java.lang.String address) {
        showmyip_lookupPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (Provider.Ippages_GeoIPLookup.Showmyip_lookupPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                Provider.Ippages_GeoIPLookup.Showmyip_lookupBindingStub _stub = new Provider.Ippages_GeoIPLookup.Showmyip_lookupBindingStub(new java.net.URL(showmyip_lookupPort_address), this);
                _stub.setPortName(getshowmyip_lookupPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("showmyip_lookupPort".equals(inputPortName)) {
            return getshowmyip_lookupPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.ippages.com/soap/lookup/", "showmyip_lookup");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.ippages.com/soap/lookup/", "showmyip_lookupPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

if ("showmyip_lookupPort".equals(portName)) {
            setshowmyip_lookupPortEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
