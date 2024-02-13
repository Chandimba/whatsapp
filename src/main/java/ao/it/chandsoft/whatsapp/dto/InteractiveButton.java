package ao.it.chandsoft.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public record InteractiveButton(String phonenumber, String header, String body, String footer, List<Button> buttons) implements Message {

    public record Button(String id, String title){}

    public static final String REPLY = """
                                {
                                "type": "reply",
                                "reply": {
                                    "id": "_ID_",
                                    "title": "_TITLE_"
                                }
                            }
            """;

    static final String JSON = """
                {
                    "messaging_product": "whatsapp",
                    "recipient_type": "individual",
                    "to": "_TO_",
                    "type": "interactive",
                    "interactive": {
                        "type": "button",
                        "header": {
                            "type": "text",
                            "text": "_HEADER_TEXT_"
                        },
                        "body": {
                            "text": "_BODY_TEXT_"
                        },
                        "footer": _FOOTER_,
                        "action": {
                            "buttons": [
                                _BUTTONS_
                            ]
                        }
                    }
                }
                """;

    @Override
    public String toJson() {
        return JSON.replace("_TO_", phonenumber)
                .replace("_HEADER_TEXT_", toNonNullValue(header))
                .replace("_BODY_TEXT_", body)
                .replace("_FOOTER_", translateFooter(footer))
                .replace("_BUTTONS_", buttonsToJson())
                ;
    }

    private String buttonsToJson() {
        return buttons.stream().limit(3).map(button -> REPLY.replace("_ID_", button.id)
                .replace("_TITLE_", button.title)
        ).collect(Collectors.joining(",\n"));

    }

    public String toNonNullValue(String value) {
        return value != null && !value.isBlank()? value: "";
    }

    public String translateFooter(String value) {
        return value != null && !value.isBlank()? "{\"text\": \"" + value+ "\"}": "null";
    }
}
