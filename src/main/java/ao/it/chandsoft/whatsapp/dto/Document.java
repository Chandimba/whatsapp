package ao.it.chandsoft.whatsapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.DocumentType;

import java.util.Objects;

@Data
public class Document extends FileMessage implements Message {

    private String filename;

    @Override
    public String toJson() {
        return super.toJson().replace("_TYPE_", "document")
                .replace("_OPTIONAL_", Objects.nonNull(filename) && !filename.isBlank()?
                        ",\n\t\t\"filename\": \"" + filename + "\"": "");
    }

}
