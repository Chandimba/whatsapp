package ao.it.chandsoft.whatsapp.dto;

import java.util.List;
import java.util.stream.Collectors;

public record InteractiveList(String phonenumber, String header, String body, String footer, String button, List<Section> sections) implements Message{

    public record Section(String title, List<Row> rows) {}

    public record Row(String id, String title, String description) {}

    public static final String SECTION_TO_JSON = """
                            {
                                "title": _TITLE_,
                                "rows": [
                                    _ROWS_
                                ]
                            }
            """;
    public static final String ROW_TO_JSON = """
                                    {
                                        "id": "_ID_",
                                        "title": "_TITLE_",
                                        "description": _DESCRIPTION_
                                    }
            """;
    public static final String JSON = """
                {
                    "messaging_product": "whatsapp",
                    "recipient_type": "individual",
                    "to": "_TO_",
                    "type": "interactive",
                    "interactive": {
                        "type": "list",
                        "header": {
                            "type": "text",
                            "text": "_HEADER_TEXT_"
                        },
                        "body": {
                            "text": "_BODY_TEXT_"
                        },
                        "footer": _FOOTER_TEXT_,
                        "action": {
                            "button": "_BUTTON_",
                            "sections": [
                                _SECTIONS_
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
                .replace("_FOOTER_TEXT_", translateFooter(footer))
                .replace("_BUTTON_", button)
                .replace("_SECTIONS_", sectionsToJson())
                ;
    }

    private String sectionsToJson() {
        return sections.stream().limit(10).map(section -> {
            String rows = section.rows.stream().map(row -> ROW_TO_JSON.replace("_ID_", row.id)
                    .replace("_TITLE_", row.title)
                    .replace("_DESCRIPTION_", toNonNullValue3(row.description))
            ).collect(Collectors.joining(",\n"));

            return SECTION_TO_JSON.replace("_TITLE_", toNonNullValue3(section.title))
                    .replace("_ROWS_", rows);
        }).collect(Collectors.joining(",\n"));
    }

    public String toNonNullValue(String value) {
        return value != null && !value.isBlank()? value: "";
    }

    public String toNonNullValue2(String value) {
        return value != null && !value.isBlank()? "\"" + value + "\"": "";
    }

    public String toNonNullValue3(String value) {
        return value != null && !value.isBlank()? "\"" + value + "\"": "null";
    }

    public String translateFooter(String value) {
        return value != null && !value.isBlank()? "{\"text\": \"" + value+ "\"}": "null";
    }

}
