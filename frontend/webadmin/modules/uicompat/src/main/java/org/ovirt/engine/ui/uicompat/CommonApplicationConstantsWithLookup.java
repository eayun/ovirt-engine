package org.ovirt.engine.ui.uicompat;

public interface CommonApplicationConstantsWithLookup extends com.google.gwt.i18n.client.ConstantsWithLookup {
    @DefaultStringValue("stateless")
    String stateless();

    @DefaultStringValue("vdsGroupId")
    String vdsGroupId();

    @DefaultStringValue("memSizeMb")
    String memSizeMb();

    @DefaultStringValue("numOfIoThreads")
    String numOfIoThreads();

    @DefaultStringValue("numOfSockets")
    String numOfSockets();

    @DefaultStringValue("cpuPerSocket")
    String cpuPerSocket();

    @DefaultStringValue("threadsPerCpu")
    String threadsPerCpu();

    @DefaultStringValue("customEmulatedMachine")
    String customEmulatedMachine();

    @DefaultStringValue("customCpuName")
    String customCpuName();

    @DefaultStringValue("numOfMonitors")
    String numOfMonitors();

    @DefaultStringValue("singleQxlPci")
    String singleQxlPci();

    @DefaultStringValue("timeZone")
    String timeZone();

    @DefaultStringValue("usbPolicy")
    String usbPolicy();

    @DefaultStringValue("defaultBootSequence")
    String defaultBootSequence();

    @DefaultStringValue("niceLevel")
    String niceLevel();

    @DefaultStringValue("cpuShares")
    String cpuShares();

    @DefaultStringValue("ssoMethod")
    String ssoMethod();

    @DefaultStringValue("kernelUrl")
    String kernelUrl();

    @DefaultStringValue("kernelParams")
    String kernelParams();

    @DefaultStringValue("initrdUrl")
    String initrdUrl();

    @DefaultStringValue("userDefinedProperties")
    String userDefinedProperties();

    @DefaultStringValue("predefinedProperties")
    String predefinedProperties();

    @DefaultStringValue("customProperties")
    String customProperties();

    @DefaultStringValue("defaultDisplayType")
    String defaultDisplayType();

    @DefaultStringValue("vncKeyboardLayout")
    String vncKeyboardLayout();

    @DefaultStringValue("minAllocatedMem")
    String minAllocatedMem();

    @DefaultStringValue("runAndPause")
    String runAndPause();

    @DefaultStringValue("SerialNumberPolicy")
    String SerialNumberPolicy();

    @DefaultStringValue("customSerialNumber")
    String customSerialNumber();

    @DefaultStringValue("bootMenuEnabled")
    String bootMenuEnabled();

    @DefaultStringValue("spiceFileTransferEnabled")
    String spiceFileTransferEnabled();

    @DefaultStringValue("spiceCopyPasteEnabled")
    String spiceCopyPasteEnabled();

    /** AuthenticationResult **/
    @DefaultStringValue("Authentication Failed. Please verify the username and password.")
    String INVALID_CREDENTIALS();

    @DefaultStringValue("Authentication Failed. The Engine clock is not synchronized with directory services (must be within 5 minutes difference).")
    String CLOCK_SKEW_TOO_GREAT();

    @DefaultStringValue("Authentication Failed. Please verify the fully qualified domain name that is used for authentication is correct.")
    String NO_KDCS_FOUND();

    @DefaultStringValue("Authentication Failed. Error in DNS configuration. Please verify the Engine host has a valid reverse DNS (PTR) record.")
    String DNS_ERROR();

    @DefaultStringValue("Kerberos error. Please check log for further details.")
    String OTHER();

    @DefaultStringValue("Authentication failed. The user is either locked or disabled.")
    String USER_ACCOUNT_DISABLED_OR_LOCKED();

    @DefaultStringValue("Authentication Failed. Cannot lookup DNS for SRV records. Please check your DNS configuration.")
    String DNS_COMMUNICATION_ERROR();

    @DefaultStringValue("Authentication Failed. Connection to LDAP server has timed out. Please contact your system administrator.")
    String CONNECTION_TIMED_OUT();

    @DefaultStringValue("Authentication Failed. Wrong domain name was provided for authentication.")
    String WRONG_REALM();

    @DefaultStringValue("Connection refused or some configuration problems exist. Possible DNS error. Check your Kerberos and LDAP records.")
    String CONNECTION_ERROR();

    @DefaultStringValue("Cannot find valid LDAP server for domain.")
    String CANNOT_FIND_LDAP_SERVER_FOR_DOMAIN();

    @DefaultStringValue("No user information was found for user.")
    String NO_USER_INFORMATION_WAS_FOUND_FOR_USER();

    @DefaultStringValue("Authentication Failed. The password has expired. Please change your password and login again.")
    String PASSWORD_EXPIRED();

    @DefaultStringValue("Authentication Failed. Client not found in kerberos database.")
    String CLIENT_NOT_FOUND_IN_KERBEROS_DATABASE();

    @DefaultStringValue("An internal error has ocurred in the Kerberos implementation of the Java virtual machine. This usually means that the LDAP server is configured with a minimum security strength factor (minssf) of 0. Change it to 1 and try again. You can also try to change the SASL quality of protection to \"auth\" which will lower the protection level. To change the SASL quality of protection to \"auth\" use engine-config -s SASL_QOP=auth and restart engine.")
    String NTERNAL_KERBEROS_ERROR();

    @DefaultStringValue("")
    String OK();
}
