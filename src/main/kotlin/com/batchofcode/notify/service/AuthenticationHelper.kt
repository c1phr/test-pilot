package com.batchofcode.notify.service

import SSLUtil
import com.batchofcode.utils.traceString
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class AuthenticationHelper constructor(private val env: Environment, private val restTemplate: RestTemplate) {

    @Value("\${deployApi.host}\${deployApi.extension}")
    private lateinit var deployApi: String

    @Throws(Exception::class)
    internal fun <T : Any> makeAuthenticatedCall(endpoint: String, method: HttpMethod, pojo: Class<T>, requestBody: Any? = null): HttpEntity<T> {
        restTemplate.messageConverters.add(MappingJackson2HttpMessageConverter())
        val injectedPassword: String? = env.getProperty("deployPassword")
        val injectedUsername: String? = env.getProperty("deployUsername")
        val callUrl = when {
            endpoint.contains("http") -> endpoint
            else -> deployApi + endpoint
        }
        if (injectedPassword == null || injectedUsername == null) {
            throw Exception("Missing username or password")
        }
        val credentials: String = "$injectedUsername:$injectedPassword"
        val encodedCredentials: String = Base64.getEncoder().encodeToString(credentials.toByteArray())
        val headers = HttpHeaders()
        headers.add("Authorization", "Basic $encodedCredentials")
        if (method == HttpMethod.POST || method == HttpMethod.PUT) {
            headers.contentType = MediaType.APPLICATION_JSON
        }
        val request = when (requestBody) {
            null -> HttpEntity<Any>(headers)
            else -> HttpEntity<Any>(requestBody, headers)
        }
        if (env.activeProfiles.any { it == "local" }) { // Turn off SSL checking locally only
            SSLUtil.turnOffSslChecking()
        }
        try {
            return restTemplate.exchange(callUrl, method, request, pojo)
        } catch (ex: RestClientException) {
            print("makeAuthenticatedCall failed with: ${ex.message}. \n" +
                    "Params: \n" +
                    "Username: $injectedUsername\n" +
                    "Method: $method\n" +
                    "Request Body: $requestBody\n" +
                    "Endpoint: $endpoint\n" +
                    "Full URL: $callUrl\n" +
                    "Trace: \n ${ex.stackTrace.traceString()}")
            throw ex
        }
    }
}