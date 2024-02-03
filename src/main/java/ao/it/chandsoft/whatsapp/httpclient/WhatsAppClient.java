package ao.it.chandsoft.whatsapp.httpclient;

import ao.it.chandsoft.whatsapp.config.WhatsAppClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "whatsappApi",
        url = "https://graph.facebook.com/v18.0/",
        configuration = WhatsAppClientConfig.class
)
public interface WhatsAppClient {

    @PostMapping(value = "/{channelId}/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
    String sendMessage(@PathVariable String channelId, @RequestBody Object message);

    @PostMapping(value = "/{channelId}/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@PathVariable String channelId,
                      @RequestPart("messaging_product") String messagingProduct,
                      @RequestPart("file") MultipartFile file,
                      @RequestPart("type") String type);

}
