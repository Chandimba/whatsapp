package ao.it.chandsoft.whatsapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Image extends FileMessage implements Message {

    @Setter
    @Getter
    private String caption;

    @Override
    public String toJson() {
        return super.toJson().replace("_TYPE_", "image")
                .replace("_OPTIONAL_", Objects.nonNull(caption) && !caption.isBlank()?
                        ",\n\t\t\"caption\": \"" + caption + "\"": "");
    }

}
