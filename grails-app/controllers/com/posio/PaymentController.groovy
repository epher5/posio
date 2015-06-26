package com.posio

import com.google.gson.JsonObject
import grails.converters.JSON
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j

@Log4j
class PaymentController {

    def index() {


    }

    def create = {
        log.info("create params ${params} request ${request}")

    }

    def authorize = {
        log.info("authorize params ${params} request ${request} body ${request.body}")
//        forward(action: "call", controller: 'phone', params: params)

//        sendPush()

//        redirect(url: 'https://api.tropo.com/1.0/sessions?action=create&token=0b7c36bf4b09354f8d283d7bfd3150b774f8cc486bfe0a8be5d330bce8708ed928b802a0058c1b5135c8d156&numberToDial=13143151468&customerName=John+Dyer&msg=the+sky+is+falling')

        PhoneController.phone = params.phone ?: '15733532206'
        PhoneController.amount = params.amount
        PhoneController.last4 = params.last4 ?: '5555'
        PhoneController.merchant = params.merchant ?: 'Abc'

        def request = [:]
//        def key = "key=${params.key}"
        request.url = 'https://api.tropo.com/1.0/sessions?action=create&token=0b7c36bf4b09354f8d283d7bfd3150b774f8cc486bfe0a8be5d330bce8708ed928b802a0058c1b5135c8d156'
//        request.headers = ['token': '0b7c36bf4']
//        request.data = request.body ? new JsonSlurper().parseText(request.body) // [to: 'here', data: [:]]

        def response = Http.post(request)
        log.info ("response ${response}")
        response
    }

    def sendGcm = {
        log.info("sendGcm params ${params} request ${request}")

        StringBuffer buf = new StringBuffer('{')
        int i = 0
        params.each { k, v ->
            if (v) {
                if (i) {
                    buf.append(',')
                }
                buf.append('"').append(k).append('"').append(':').append('"').append(v).append('"')
                i++
            }
        }
        def request = [:]
        request.url = 'https://gcm-http.googleapis.com/gcm/send'
        request.headers = ['Content-Type': 'application/json',
                           'Authorization': 'key=AIzaSyBBoDRg_AXy6cDpAna7EBBBRuBDNcfTpF0'] //AIzaSyA8CclqOuTVsqoasFkRuTYMXrD6VErGf_o

        request.data = '{"to": "dzv7HotXhcg:APA91bGj21mChO1cTTZAOKh3mtle38upBrPT_myKL2n8maf3mRMLuR8xX0pGSkJtNIygVunjMuQcDTR85t7UJlIAlKJtN6GoaBVh5Pbknqbq-70pklaAZ1gDu17Wz5ATYUrKNnH0gZpx",' +
                       '"data":' + buf.toString() + '}}'

        log.info("data ${request.data}")

        Http.post(request)
    }

}
