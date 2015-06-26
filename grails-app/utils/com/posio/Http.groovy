package com.posio

import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method
import org.json.simple.JSONObject

@Log4j
class Http {

    static HTTPBuilder httpBuilder = new HTTPBuilder()

    static def get(request) {
        execute(Method.GET, request)
    }

    static def post(request) {
        execute(Method.POST, request)
    }

    static def put(request) {
        execute(Method.PUT, request)
    }

    static def execute(Method _method, request) {
            // Checking for isUnsubscribed seems unnecessary in our case
            // since this is not multiple results, callers will never unsubscribe before it is complete
            // if (!observer.isUnsubscribed()) {

            String _url = request.url
            Map _headers = request.headers ?: ["Content-Type": 'text/plain']
            String _data = request.data

            def _contentType = _headers."Content-Type"  //?: ContentType.JSON

            _headers << ['Accept': _contentType]

            doExecute(_url, _method, _contentType, _headers, _data)
    }

    static def doExecute(_url, _method, _contentType, _headers, _data) {

        httpBuilder.request(_url, _method, _contentType) {

            log.debug "Doing a ${_method} to url:${_url}"

            uri = _url

            if (log.isDebugEnabled()) {
                _headers.each { k, v ->
                    log.debug "Adding headers $k = ${v}"
                }
            }

            headers.putAll(_headers)

            if (_data) {
                body = _data
            }

            response.success = { HttpResponseDecorator responseDecorator, responseData ->
                log.debug "Successful response status code:${responseDecorator.status}"
                readResponse(responseData)
            }

            response.failure = { HttpResponseDecorator responseDecorator, responseData ->
                readResponse(responseData)
            }
        }
    }

    /**
     * assume that if our caller told us to expect text/plain then override every thing else.
     *
     * @param responseData
     * @return
     */
    static def readResponse(responseData) {
        if (responseData instanceof InputStreamReader) {
            responseData = responseData.text
        }
        log.debug "Response Data: $responseData"
        responseData
    }
}
