package ao.it.chandsoft.whatsapp.enums;

import lombok.Getter;

import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

@Getter
public enum FileType {
    //images
    JPG(".jpg", "image/jpeg", 5L, 5_000_000L, "MB"),
    JPEG(".jpeg", "image/jpeg", 5L, 5_000_000L, "MB"),
    PNG(".png", "image/png", 5L, 5_000_000L, "MB"),

    //sticker
    WEBP(".webp", "image/webp", 5L, 100_000, "KB"),

    //documents
    // text/plain,
    // application/pdf,
    // application/vnd.ms-powerpoint,
    // application/msword, application/vnd.ms-excel,
    // application/vnd.openxmlformats-officedocument.wordprocessingml.document,
    // application/vnd.openxmlformats-officedocument.presentationml.presentation,
    // application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
    TXT(".txt", "text/plain", 100L, 100_000_000L, "MB"),
    PDF(".pdf", "application/pdf", 100L, 100_000_000L, "MB"),

    XLS(".xls", "application/vnd.ms-excel", 100L, 100_000_000L, "MB"),
    XLT(".xlt", "application/vnd.ms-excel", 100L, 100_000_000L, "MB"),
    XLA(".xla", "application/vnd.ms-excel", 100L, 100_000_000L, "MB"),
    XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 100L, 100_000_000L, "MB"),

    DOCX(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", 100L, 100_000_000L, "MB"),

    PPT(".ppt", "application/vnd.ms-powerpoint", 100L, 100_000_000L, "MB"),
    POT(".pot", "application/vnd.ms-powerpoint", 100, 100_000_000L, "MB"),
    PPS(".pps", "application/vnd.ms-powerpoint", 100L, 100_000_000L, "MB"),
    PPA(".ppa", "application/vnd.ms-powerpoint", 100L, 100_000_000L, "MB"),
    PPTX(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation", 100L, 100_000_000L, "MB"),

    //audios
    AAC(".acc", "audio/aac", 16L, 16_000_000L, "MB"),
    MP4(".mp4", "audio/mp4", 16L, 16_000_000L, "MB"),
    MPEG(".mpeg", "audio/mpeg", 16L, 16_000_000L, "MB"),
    AMR(".amr", "audio/amr", 16L, 16_000_000L, "MB"),
    OGG(".ogg", "audio/ogg", 16L, 16_000_000L, "MB"),

    //videos -- video/mp4, video/3gp
    VIDEO_MP4(".mp4", "video/mp4", 16L, 16_000_000L, "MB"),
    VIDEO_3GP(".3gp", "video/3gp", 16L, 16_000_000L, "MB")
    ;

    String extension;
    String contentType;
    long maxSizeMB;
    long maxSizeB;
    String unit;

    FileType(String extension, String contentType, long maxSizeMB, long maxSizeB, String unit) {
        this.extension = extension;
        this.contentType = contentType;
        this.maxSizeMB = maxSizeMB;
        this.maxSizeB = maxSizeB;
        this.unit = unit;
    }

    public static FileType getFileTypeByExtension(File file) {
        return getFileTypeByExtension(file.getName().substring(file.getName().lastIndexOf(".")));
    }
    public static FileType getFileTypeByExtension(String extension) {
        if(!extension.isBlank()) {
            for (FileType fileType: values()) {
                if(fileType.getExtension().equalsIgnoreCase(extension)) {
                    return fileType;
                }
            }

            throw new IllegalArgumentException("Extension file is not supported type");
        }

        throw new IllegalArgumentException("Extension file is required");
    }

    public static boolean isSuportedImageType(File file) {
        return Stream.of(JPG, JPEG, PNG, WEBP)
                .anyMatch(fileType -> file.getName().toLowerCase().endsWith(fileType.getExtension()));
    }

    public static boolean isSuportedDocumentType(File file) {
        return Stream.of(TXT, PDF, XLS, XLT, XLA, XLSX, DOCX, PPT, POT, PPS, PPA, PPTX)
                .anyMatch(fileType -> file.getName().toLowerCase().endsWith(fileType.getExtension()));
    }

    public static boolean isSuportedVideoType(File file) {
        return Stream.of(VIDEO_MP4, VIDEO_3GP)
                .anyMatch(fileType -> file.getName().toLowerCase().endsWith(fileType.getExtension()));
    }

    public static boolean isSuportedAudioype(File file) {
        return Stream.of(AAC, MP4, MPEG, AMR, OGG)
                .anyMatch(fileType -> file.getName().toLowerCase().endsWith(fileType.getExtension()));
    }

}
