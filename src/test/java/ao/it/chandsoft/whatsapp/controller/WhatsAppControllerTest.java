package ao.it.chandsoft.whatsapp.controller;

import ao.it.chandsoft.whatsapp.dto.Text;
import ao.it.chandsoft.whatsapp.dto.WhatsAppResponse;
import ao.it.chandsoft.whatsapp.enums.FileType;
import ao.it.chandsoft.whatsapp.service.WhatsAppService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@SpringBootTest
public class WhatsAppControllerTest {

    @Autowired
    private WhatsAppController whatsAppController;
    private ObjectMapper objectMapper;
    private String phonenumber;
    private String channelID;

    @BeforeEach
    public void setup() {
        phonenumber = "244945263402";
        channelID = "142459815608774";

        objectMapper = new ObjectMapper();
    }

    @Test
    public void sendTextMessage() {
        Text message = new Text(phonenumber, "The spartan never surrender");

        WhatsAppResponse response = whatsAppController.sendText(message, channelID);

        Assertions.assertNotNull(response, "Message text not send");
        Assertions.assertNotNull(response.messageId(), "Message text id not send");

    }

    @Test
    public void uploadFile() throws IOException {
        String upload = getId(new File("C:/Users/2022/Downloads/Documents/java-se-language-updates.pdf"));

        Map<String, String> response = objectMapper.readValue(upload, Map.class);
        Assertions.assertNotNull(response, "Cannot upload file");
        Assertions.assertNotNull(response.containsKey("id"), "Error to upload file");

    }

    @Test
    public void uploadFile2() throws IOException {
        //String upload = getId(new File("C:/Users/2022/Downloads/Documents/java-se-language-updates.sdc"));

        //Assertions.assertThrows(getId(new File("C:/Users/2022/Downloads/Documents/java-se-language-updates.sdc")));

    }

    private String getId(File file) throws IOException, IllegalArgumentException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                file.getName(),
                file.getName(),
                FileType.getFileTypeByExtension(file).getContentType(),
                new FileInputStream(file));

        String upload = (String) whatsAppController.upload(channelID, multipartFile);

        return upload;
    }

}
