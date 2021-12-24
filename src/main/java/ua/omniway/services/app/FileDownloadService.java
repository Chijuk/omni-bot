package ua.omniway.services.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.telegram.TelegramBot;
import ua.omniway.services.exceptions.ServiceException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@Service
public class FileDownloadService {
    private final TelegramFileDownloader downloader;
    private final TelegramBot bot;

    @Autowired
    public FileDownloadService(TelegramFileDownloader downloader, TelegramBot bot) {
        this.downloader = downloader;
        this.bot = bot;
    }

    public String getAttachment(String oid) throws ServiceException {
        java.io.File tempFile = null;
        try {
            final File file = bot.execute(new GetFile(oid));

            tempFile = java.io.File.createTempFile(file.getFileId(), ".tmp");
            final java.io.File downloadedFile = downloader.downloadFile(file, tempFile);

            return Base64.getEncoder().encodeToString(Files.readAllBytes(downloadedFile.toPath()));
        } catch (IOException | TelegramApiException e) {
            log.error("", e);
            throw new ServiceException(e.getMessage(), e);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                try {
                    Files.delete(tempFile.toPath());
                    log.debug("Temporary file has been deleted: {}", tempFile.getAbsolutePath());
                } catch (IOException e) {
                    log.error("Error while deleting temporary file {}", tempFile.getAbsolutePath(), e);
                }
            }
        }
    }
}
