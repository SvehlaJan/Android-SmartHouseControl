package dk.summerinnovationweek.futurehousing;


public class FutureHousingConfig
{
	public static final boolean DEV_API = true;
	public static final boolean LOGS = true;
	
	public static final boolean BUILD_DEBUG = BuildConfig.DEBUG;
	
	public static final String API_ENDPOINT_PRODUCTION = "http://smarthouses.summerinnovationweek.dk/Api/";
	public static final String API_ENDPOINT_DEVELOPMENT = "http://smarthouses.summerinnovationweek.dk/Api/";
	
	public static final String GCM_REGISTER_URL = "http://example.com/register";
	public static final String GCM_UNREGISTER_URL = "http://example.com/unregister";
	public static final String GCM_SENDER_ID = "0123456789";
	
	public static final String FACEBOOK_APP_ID = "0123456789";
	
	public static final String SSL_CERTIFICATE_SHA1 = "myhash="; // encoded representation of the trusted certificate
	public static final String SSL_KEYSTORE_PASSWORD = "mypassword"; // password for BKS keystore, generated via script: https://github.com/petrnohejl/Android-Scripts/blob/master/certkey.bat
}
