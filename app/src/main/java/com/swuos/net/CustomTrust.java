package com.swuos.net;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okio.Buffer;

public class CustomTrust {
    private X509TrustManager trustManager;
    private SSLSocketFactory sslSocketFactory;

    public CustomTrust() {

        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private static InputStream trustedCertificatesInputStream() {
        // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
        // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
        // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
        // Typically developers will need to get a PEM file from their organization's TLS administrator.
        String comodoRsaCertificationAuthority = "-----BEGIN CERTIFICATE-----\n" +
                "MIIE4jCCA8qgAwIBAgIQJRD/8XxWjXOcQdKXpyG1mjANBgkqhkiG9w0BAQsFADBSMQswCQYDVQQG\n" +
                "EwJDTjEaMBgGA1UEChMRV29TaWduIENBIExpbWl0ZWQxJzAlBgNVBAMTHldvU2lnbiBDbGFzcyAz\n" +
                "IE9WIFNlcnZlciBDQSBHMjAeFw0xNTEyMDQwMjQ2MjZaFw0xODEyMDQwMjQ2MjZaMGMxCzAJBgNV\n" +
                "BAYTAkNOMRIwEAYDVQQIDAnph43luobluIIxEjAQBgNVBAcMCemHjeW6huW4gjEVMBMGA1UECgwM\n" +
                "6KW/5Y2X5aSn5a2mMRUwEwYDVQQDDAwqLnN3dS5lZHUuY24wggEiMA0GCSqGSIb3DQEBAQUAA4IB\n" +
                "DwAwggEKAoIBAQC3ald6Is+p1csfsA0Ruwtt2g5Dt7ilNEU9Pfa5J8kKlZDBdIFymiLLugzHCSUL\n" +
                "U+ht6RbfzXknXo3ASxbn3CNxGkA/bc2ThmZ9M8kHBf1yWKq03aCDSrDwehlt46fvn/RALwiChdge\n" +
                "yD2rf1kCuDg8N9Pfn8NyMbebCJcgFRcjB9N1z5Sj53YuDRAX+DpBeLF4fx/lozi0/tg3KQ7cPfI4\n" +
                "/CaHAJv6MKTPoCXVLN+bw3s1ziB7BJLQQcADl2PG2nlD3LK9u691OV7wU7hXPq/6eUFNhE2eD11h\n" +
                "Wd3QKGbh1j2MR7CJkdAaNhH0jma8i/ZRo3zc+i5na6hz/4xCwJivAgMBAAGjggGhMIIBnTALBgNV\n" +
                "HQ8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwIGCCsGAQUFBwMBMAkGA1UdEwQCMAAwHQYDVR0O\n" +
                "BBYEFIHaRGtSJB2Dgd90ISMdgmbonpVYMB8GA1UdIwQYMBaAFPmL7AQ4aj+qBsaUrXOVKrDI5rj7\n" +
                "MHMGCCsGAQUFBwEBBGcwZTAvBggrBgEFBQcwAYYjaHR0cDovL29jc3AxLndvc2lnbi5jb20vY2E2\n" +
                "L3NlcnZlcjMwMgYIKwYBBQUHMAKGJmh0dHA6Ly9haWExLndvc2lnbi5jb20vY2E2LnNlcnZlcjMu\n" +
                "Y2VyMDgGA1UdHwQxMC8wLaAroCmGJ2h0dHA6Ly9jcmxzMS53b3NpZ24uY29tL2NhNi1zZXJ2ZXIz\n" +
                "LmNybDAjBgNVHREEHDAaggwqLnN3dS5lZHUuY26CCnN3dS5lZHUuY24wUAYDVR0gBEkwRzAIBgZn\n" +
                "gQwBAgIwOwYMKwYBBAGCm1EGAwIBMCswKQYIKwYBBQUHAgEWHWh0dHA6Ly93d3cud29zaWduLmNv\n" +
                "bS9wb2xpY3kvMA0GCSqGSIb3DQEBCwUAA4IBAQCN/1ZMhl2XSmqmhULh/AHARSdqFB6hwYLLp2Ek\n" +
                "DFFhGq9aY70bruQE+fph5CW3o2CF3Y4uIR+0GAtsIOcS9mbtpqL2fJ0ctK/ToCg7WYvdoqk4TSxI\n" +
                "ghjUqLJDQ2daFPbdMEEyaB/Ww7JxcO71+ndpI6A1rFD2F1L6S6pvy3yiFtbpJN/zvAtlhYUWau5h\n" +
                "J6Ai2araa44GW+h6Lek70n4F71KzzLJXQQZBJAGLDuGYv2OvNpA/gtVe5sbRPxIQ2LPsr0uCKKZ0\n" +
                "tE9M9r5J2OmaXCapV9MLf1VWN/1RiIFV+5m5j+k8lJSWNUvaGmsBpZHNrjPDBp6nIYKsN/6Vskkp\n" +
                "-----END CERTIFICATE-----";
        //    String entrustRootCertificateAuthority = ""
        //        + "-----BEGIN CERTIFICATE-----\n"
        //        + "MIIEkTCCA3mgAwIBAgIERWtQVDANBgkqhkiG9w0BAQUFADCBsDELMAkGA1UEBhMC\n"
        //        + "VVMxFjAUBgNVBAoTDUVudHJ1c3QsIEluYy4xOTA3BgNVBAsTMHd3dy5lbnRydXN0\n"
        //        + "Lm5ldC9DUFMgaXMgaW5jb3Jwb3JhdGVkIGJ5IHJlZmVyZW5jZTEfMB0GA1UECxMW\n"
        //        + "KGMpIDIwMDYgRW50cnVzdCwgSW5jLjEtMCsGA1UEAxMkRW50cnVzdCBSb290IENl\n"
        //        + "cnRpZmljYXRpb24gQXV0aG9yaXR5MB4XDTA2MTEyNzIwMjM0MloXDTI2MTEyNzIw\n"
        //        + "NTM0MlowgbAxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1FbnRydXN0LCBJbmMuMTkw\n"
        //        + "NwYDVQQLEzB3d3cuZW50cnVzdC5uZXQvQ1BTIGlzIGluY29ycG9yYXRlZCBieSBy\n"
        //        + "ZWZlcmVuY2UxHzAdBgNVBAsTFihjKSAyMDA2IEVudHJ1c3QsIEluYy4xLTArBgNV\n"
        //        + "BAMTJEVudHJ1c3QgUm9vdCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTCCASIwDQYJ\n"
        //        + "KoZIhvcNAQEBBQADggEPADCCAQoCggEBALaVtkNC+sZtKm9I35RMOVcF7sN5EUFo\n"
        //        + "Nu3s/poBj6E4KPz3EEZmLk0eGrEaTsbRwJWIsMn/MYszA9u3g3s+IIRe7bJWKKf4\n"
        //        + "4LlAcTfFy0cOlypowCKVYhXbR9n10Cv/gkvJrT7eTNuQgFA/CYqEAOwwCj0Yzfv9\n"
        //        + "KlmaI5UXLEWeH25DeW0MXJj+SKfFI0dcXv1u5x609mhF0YaDW6KKjbHjKYD+JXGI\n"
        //        + "rb68j6xSlkuqUY3kEzEZ6E5Nn9uss2rVvDlUccp6en+Q3X0dgNmBu1kmwhH+5pPi\n"
        //        + "94DkZfs0Nw4pgHBNrziGLp5/V6+eF67rHMsoIV+2HNjnogQi+dPa2MsCAwEAAaOB\n"
        //        + "sDCBrTAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0TAQH/BAUwAwEB/zArBgNVHRAEJDAi\n"
        //        + "gA8yMDA2MTEyNzIwMjM0MlqBDzIwMjYxMTI3MjA1MzQyWjAfBgNVHSMEGDAWgBRo\n"
        //        + "kORnpKZTgMeGZqTx90tD+4S9bTAdBgNVHQ4EFgQUaJDkZ6SmU4DHhmak8fdLQ/uE\n"
        //        + "vW0wHQYJKoZIhvZ9B0EABBAwDhsIVjcuMTo0LjADAgSQMA0GCSqGSIb3DQEBBQUA\n"
        //        + "A4IBAQCT1DCw1wMgKtD5Y+iRDAUgqV8ZyntyTtSx29CW+1RaGSwMCPeyvIWonX9t\n"
        //        + "O1KzKtvn1ISMY/YPyyYBkVBs9F8U4pN0wBOeMDpQ47RgxRzwIkSNcUesyBrJ6Zua\n"
        //        + "AGAT/3B+XxFNSRuzFVJ7yVTav52Vr2ua2J7p8eRDjeIRRDq/r72DQnNSi6q7pynP\n"
        //        + "9WQcCk3RvKqsnyrQ/39/2n3qse0wJcGE2jTSW3iDVuycNsMm4hH2Z0kdkquM++v/\n"
        //        + "eu6FSqdQgPCnXEqULl8FmTxSQeDNtGPPAUO6nIPcj2A781q0tHuu2guQOHXvgR1m\n"
        //        + "0vdXcDazv/wor3ElhVsT/h5/WrQ8\n"
        //        + "-----END CERTIFICATE-----\n";
        return new Buffer()
                .writeUtf8(comodoRsaCertificationAuthority)
                //        .writeUtf8(entrustRootCertificateAuthority)
                .inputStream();
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     * <p>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private static X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private static KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }
}
