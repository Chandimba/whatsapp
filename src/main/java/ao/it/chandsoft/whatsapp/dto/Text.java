package ao.it.chandsoft.whatsapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Text(
        @Size(min = 10, max = 25, message = "Phonenumber must contain between {min} and {max} characters")
        @Pattern(regexp = "[0-9]+", message = "Phonenumber is required and it must contains only numbers")
        String phonenumber,
        @NotBlank(message = "Message text is required") String text) implements Message {

        private static final String TEXT = """
            {
             	"messaging_product": "whatsapp",
             	"to": "_TO_",
             	"type": "text",
             	"text": {
             		"body": "_BODY_"
             	}
            }
            """;

        public String toJson() {
                return TEXT.replace("_TO_", phonenumber)
                        .replace("_BODY_", text);
        }


}
