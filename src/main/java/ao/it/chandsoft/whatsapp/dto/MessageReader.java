package ao.it.chandsoft.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageReader(String messageId) {

    public String toJson() {
        return """
            {
             	"messaging_product": "whatsapp",
             	"status": "read",
             	"message_id": "%s"
            }
            """.formatted(messageId);
    }
}
