package ao.it.chandsoft.whatsapp.controller;

import ao.it.chandsoft.whatsapp.dto.*;
import ao.it.chandsoft.whatsapp.service.WhatsAppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/whatsapp")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    @Operation(method = "POST", summary = "Upload file to WhatsApp",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "multipart/form-data")
            ))
    @PostMapping(value = "/{channelId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object upload(@PathVariable String channelId,  @RequestParam("file") MultipartFile file) {
        return whatsAppService.upload(channelId, file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = "PUT", summary = "Mark a message as read")
    @PutMapping("/{channelId}/message")
    public void markAsRead(@Validated @RequestBody MessageReader messageReader, @PathVariable String channelId) {
        whatsAppService.markAsRead(channelId, messageReader);
    }

    @Operation(method = "POST", summary = "Send text message")
    @PostMapping("/{channelId}/text")
    public WhatsAppResponse sendText(@Validated @RequestBody Text text, @PathVariable String channelId) {
        return whatsAppService.send(channelId, text);
    }

    @Operation(method = "POST", summary = "Send document message")
    @PostMapping("/{channelId}/document")
    public WhatsAppResponse sendDocument(@RequestBody Document document, @PathVariable String channelId) {
        return whatsAppService.send(channelId, document);
    }

    @Operation(method = "POST", summary = "Send image message")
    @PostMapping("/{channelId}/image")
    public WhatsAppResponse sendImage(@RequestBody Image image, @PathVariable String channelId) {
        return whatsAppService.send(channelId, image);
    }

    @Operation(method = "POST", summary = "Send video message")
    @PostMapping("/{channelId}/video")
    public WhatsAppResponse sendVideo(@RequestBody Video video, @PathVariable String channelId) {
        return whatsAppService.send(channelId, video);
    }

    @Operation(method = "POST", summary = "Send audio message")
    @PostMapping("/{channelId}/audio")
    public WhatsAppResponse sendAudio(@RequestBody Audio audio, @PathVariable String channelId) {
        return whatsAppService.send(channelId, audio);
    }

    @Operation(method = "POST", summary = "Send sticker message")
    @PostMapping("/{channelId}/sticker")
    public WhatsAppResponse sendSticker(@RequestBody Sticker sticker, @PathVariable String channelId) {
        return whatsAppService.send(channelId, sticker);
    }

    @Operation(method = "POST", summary = "Send interactive button reply message ")
    @PostMapping("/{channelId}/interactive-button")
    public WhatsAppResponse sendInteractive(@RequestBody InteractiveButton interactiveButton,
                                            @PathVariable String channelId) {
        return whatsAppService.send(channelId, interactiveButton);
    }

    @Operation(method = "POST", summary = "Send interactive list message ")
    @PostMapping("/{channelId}/interactive-list")
    public WhatsAppResponse sendInteractive(@RequestBody InteractiveList interactiveList,
                                            @PathVariable String channelId) {
        return whatsAppService.send(channelId, interactiveList);
    }

}
