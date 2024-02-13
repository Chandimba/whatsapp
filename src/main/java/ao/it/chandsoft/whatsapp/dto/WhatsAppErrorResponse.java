package ao.it.chandsoft.whatsapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WhatsAppErrorResponse (ErrorResponse error) {

    public static record ErrorResponse(
            int code,
            String type,
            String message,
            @JsonProperty("error_data")
            ErrorData errorData,
            @JsonProperty("error_subcode")
            int errorSubcode,
            @JsonProperty("fbtrace_id")
            String fbTraceId
    ) {}

    public static record ErrorData(
            @JsonProperty("messaging_product")
            String messagingProduct,
            String details
    ) {}

}
