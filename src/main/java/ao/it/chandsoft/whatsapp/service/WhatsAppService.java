package ao.it.chandsoft.whatsapp.service;

import ao.it.chandsoft.whatsapp.dto.Message;
import ao.it.chandsoft.whatsapp.dto.WhatsAppResponse;
import org.springframework.web.multipart.MultipartFile;

public interface WhatsAppService {
    WhatsAppResponse send(String channelId, Message documentMessage);
    <T> T upload(String channelId, MultipartFile file);
}
