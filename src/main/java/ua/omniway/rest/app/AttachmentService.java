package ua.omniway.rest.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.omniway.services.app.FileDownloadService;
import ua.omniway.services.exceptions.ServiceException;
import ua.omniway.utils.LogMarkers;

@Slf4j
@RestController
@RequestMapping(path = "/attachments")
public class AttachmentService {
    private final FileDownloadService downloadService;

    @Autowired
    public AttachmentService(FileDownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping(path = "{oid}")
    public ResponseEntity<String> getAttachment(@PathVariable("oid") String oid) throws ServiceException {
        log.info(LogMarkers.OT_ACTIVITY, "Download attachment by oid: {}", oid);
        return ResponseEntity.ok().body(downloadService.getAttachment(oid));
    }

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}
