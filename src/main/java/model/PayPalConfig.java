package model;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PayPalConfig {
    private static final String CLIENT_ID = "AaJVDCwIA6-oQ6x_D1jaJi2diareU1bIUnzPwBgk80p1T7Nvzg99Gt6DtCMtlSAH2RLxceqrb4PZks-q";
    private static final String CLIENT_SECRET = "EF7S9Cv2Dj_KDiQuKD11gfLmqf-4Juvpjj3YJnJjPPspdqg8b19FVoTeEkCNXTYvhqSNB5XOS1F_Gukg";
    private static final String MODE = "sandbox"; // or "live" for production

    public static APIContext getAPIContext() throws PayPalRESTException {
        return new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
    }

}