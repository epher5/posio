package com.posio

import com.simplify.payments.domain.CardToken
import com.voxeo.tropo.Tropo
import com.voxeo.tropo.actions.AskAction
import com.voxeo.tropo.actions.Do
import com.voxeo.tropo.actions.SayAction
import groovy.json.JsonSlurper
import groovy.util.logging.Log4j

import static com.voxeo.tropo.Key.*
import static com.voxeo.tropo.enums.Mode.DTMF
import static com.voxeo.tropo.enums.Mode.SPEECH


@Log4j
class PhoneController {

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC392b0a1fd84e0fccff6be38be44db3fc"
    public static final String AUTH_TOKEN = "fd1b1d1be7a144bd7b801f4d29f669e4"

    static String amount
    static String phone
    static String last4
    static String merchant

    def index() {}
    def authorize = {
        log.info("authorize params ${params} request ${request}")
//        forward(action: "call", controller: 'phone', params: params)

//        sendPush()

//        redirect(url: 'https://api.tropo.com/1.0/sessions?action=create&token=0b7c36bf4b09354f8d283d7bfd3150b774f8cc486bfe0a8be5d330bce8708ed928b802a0058c1b5135c8d156&numberToDial=13143151468&customerName=John+Dyer&msg=the+sky+is+falling')

        def request = [:]
        def key = "key=${params.key}"
        request.url = 'https://api.tropo.com/1.0/sessions?action=create&token=0b7c36bf4b09354f8d283d7bfd3150b774f8cc486bfe0a8be5d330bce8708ed928b802a0058c1b5135c8d156'
        request.headers = ['token': '0b7c36bf4']
        request.data = [to: 'here', data: [:]]

        Http.post(request)
    }
    def spokenanswer = {
        log.info("spokenanswer params ${params} request ${request}")
        render(text: "<Response>\n" +
                        "    <Speak>\n" +
                        "        Thank you.\n" +
                        "    </Speak>\n" +
                        "</Response>", contentType: "text/xml", encoding: "UTF-8")
    }
    def confirmeddigitanswer = {
        log.info("confirmeddigitanswer params ${params} request ${request}")

        render(text: "<Response>\n" +
                "    <Speak>\n" +
                "        Thank you.\n" +
                "    </Speak>\n" +
                "</Response>", contentType: "text/xml", encoding: "UTF-8")
    }
    def digitanswer = {
        log.info("digitanswer params ${params} request ${request}")

        if (params.Event == 'Redirect') {
            if (params.Digits == '1') {
                render(text: "<Response>\n" +
                        "    <GetDigits action=\"http://fc3a6fc6.ngrok.io/posio/phone/confirmeddigitanswer\" method=\"GET\" numDigits=\"1\" retries=\"1\" timeout=\"10\">\n" +
                        "        <Speak>\n" +
                        "            You have pressed ${params.Digits}. Press 1 to confirm. Press any other number to cancel.\n" +
                        "        </Speak>\n" +
                        "    </GetDigits>\n" +
                        "    <Speak>\n" +
                        "        Sorry, I didn't catch that. Goodbye.\n" +
                        "    </Speak>\n" +
                        "</Response>", contentType: "text/xml", encoding: "UTF-8")
                return
            }
        }
        render(text: "<Response>\n" +
                "    <Speak>\n" +
                "        Cancelling charge. Thank you.\n" +
                "    </Speak>\n" +
                "</Response>", contentType: "text/xml", encoding: "UTF-8")
    }
    def fallback = {
        log.info("fallback params ${params} request ${request}")
    }
    def statuscallback = {
        log.info("statuscallback params ${params} request ${request}")
    }
    def answer = {
        log.info("answer params ${params} request ${request}")
//        log.info("request ${request.result?.actions?.interpretation}")
//        log.info("request ${getFields(request)}")
        try {

            def json = request.JSON
            def ans = ''
            if (json) {
                ans = json.get('result')?.actions?.interpretation
            }

            log.info("answer ${ans}")

            Tropo tropo = new Tropo()
            if (ans == 'yes') {
                tropo.say("You have confirmed the payment. Thank you.")
            } else if (ans == 'no') {
                tropo.say("You have cancelled the payment. Thank you.")
            } else {
                tropo.say("I did not recognize you response. Payment is cancelled. Thank you.")
            }
            tropo.render(response)
            log.info("response ${response}")
        //forward(controller: 'payment', action: 'create')
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def call = {
        log.info("call params ${params} request ${request} session ${session}")

        try {

//            Tropo tropo = new Tropo()
//            tropo.call(TO("${phone}")) //, FROM("+15733192497"), TIMEOUT(10.0f), ANSWER_ON_MEDIA(false)).and(
////                    Do.headers(new String[]{"fooKey","fooValue"}, new String[]{"barKey","barValue"}),
////                    Do.startRecording(URL("http://foobar"), METHOD("POST"), FORMAT(Format.MP3), USERNAME("jose"), PASSWORD("passwd")));
////
////            PaymentsApi.PUBLIC_KEY = "sbpb_OWRmZDI5MjktMGUxOC00MzE4LTlmMTYtNWJiZWVmMzliMzUx"
////            PaymentsApi.PRIVATE_KEY = "wu0powWN1rOLmOdcS8/uP564gMPmAFdhrE1WfHPE/dZ5YFFQL0ODSXAOkNtXTToq"
////            CardToken token = CardToken.find(params.simplifyToken)
//
//            if (last4) {
//                tropo.say("Hello. This is Team Access. ${merchant} requests to charge a card ending in ${last4} in the amount of ${amount} dollars requires your authorization. ")
//
//            } else {
//                tropo.say("Hello. This is Team Access. ${merchant} requests to charge a card in the amount of ${amount} dollars requires your authorization. ")
//
//            }
//            tropo.ask(NAME("userChoice"), BARGEIN(true), MODE(SPEECH), TIMEOUT(25f), ATTEMPTS(2)).and(
//                    Do.say(VALUE("Sorry, I didn't hear anything"), EVENT("timeout"))
//                            .say("Say yes to confirm. Say no to cancel."),
//                    Do.choices(VALUE("yes(1, yes), no(2, no)")))
//
//            // (choices, attempts, bargein, minConfidence, name, recognizer, required, say, timeout, voice);
////            tropo.ask(choices, null, null, null, "directory", null, null, say, 15, null)
//            tropo.on(EVENT("continue"), NEXT("answer"))
//            tropo.on(EVENT("incomplete"), NEXT("fallback"))
//            tropo.on(EVENT("hangup"), NEXT("fallback"))
//            tropo.on(EVENT("error"), NEXT("statuscallback"))
////            tropo.render(response)
//
//            log.info("response ${tropo.text()}")
//
////            render(tropo.text())

            render("{\"tropo\":[{\"call\":{\"to\":\"${phone}\"}}," +
                    "{\"say\":[{\"value\":\"Hello. This is Team Access Card. ${merchant} requests to charge a card ending in ${last4} in the amount of ${amount} dollars requires your authorization. \"}]}," +
                    "{\"ask\":{\"name\":\"userChoice\",\"bargein\":true,\"mode\":\"any\",\"timeout\":25,\"attempts\":2,\"say\":[{\"value\":\"Sorry, I didn't hear anything\",\"event\":\"timeout\"}," +
                    "{\"value\":\"Say yes to confirm. Say no to cancel.\"}],\"choices\":{\"value\":\"yes(1, yes), no(2, no)\"}}}," +
                    "{\"on\":{\"event\":\"continue\",\"next\":\"answer\"}}," +
                    "{\"on\":{\"event\":\"incomplete\",\"next\":\"fallback\"}}," +
                    "{\"on\":{\"event\":\"hangup\",\"next\":\"fallback\"}}," +
                    "{\"on\":{\"event\":\"error\",\"next\":\"statuscallback\"}}]}")
//forward(controller: 'payment', action: 'create')
        } catch (Exception e) {
            e.printStackTrace()
        }

//        redirect(controller:'main')
//        render("Calling for charge confirmation...")
    }

    // Get all the fields in the Response
    public static String getFields(def obj) throws IllegalAccessException {
//        obj.pr
//                buffer.append("\n");
//        StringBuffer buffer = new StringBuffer();
//        Field[] fields = obj.getClass().getDeclaredFields();
//        for (Field f : fields) {
//            if (!Modifier.isStatic(f.getModifiers())) {
//                f.setAccessible(true);
//                Object value = f.get(obj);
//                buffer.append(f.getName());
//                buffer.append("=");
//                buffer.append("" + value);
//            }
//        }
//        return buffer.toString();
        def filtered = ['class', 'active']

        obj.properties
                .sort{it.key}
                .collect{it}
                .findAll{!filtered.contains(it.key)}
                .join('\n')
    }

}
