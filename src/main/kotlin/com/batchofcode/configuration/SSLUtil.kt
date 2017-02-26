
import org.springframework.context.annotation.Profile
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Profile("local", "junit") // For safety, only allow SSL verification to be turned off locally
class SSLUtil private constructor() {

    init {
        throw UnsupportedOperationException("Do not instantiate libraries.")
    }

    companion object {

        private val UNQUESTIONING_TRUST_MANAGER = arrayOf<TrustManager>(object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate>? {
                return null
            }

            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) {
            }

            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) {
            }
        })

        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        fun turnOffSslChecking() {
            // Install the all-trusting trust manager
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, UNQUESTIONING_TRUST_MANAGER, null)
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
        }

        @Throws(KeyManagementException::class, NoSuchAlgorithmException::class)
        fun turnOnSslChecking() {
            // Return it to the initial state (discovered by reflection, now hardcoded)
            SSLContext.getInstance("SSL").init(null, null, null)
        }
    }
}