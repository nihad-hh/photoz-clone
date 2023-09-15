package com.companyName.nihad.photoz.clone.web;

import com.companyName.nihad.photoz.clone.service.PhotozService;
import com.companyName.nihad.photoz.clone.model.Photo;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

// Controller converts JSON to Java Objects, extract path variables and send back JSON
@RestController
public class DownloadController {

    private final PhotozService photozService;

    // automaticlly called when starting the app
    public DownloadController(PhotozService photozService) {
        this.photozService = photozService;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id){

        Photo photo = photozService.get(id);
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        byte[] data = photo.getData();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(photo.getContentType()));
        ContentDisposition build = ContentDisposition.builder("attachment")
                        .filename(photo.getFileName())
                        .build();
        headers.setContentDisposition(build);

        return new ResponseEntity<byte[]>(data, headers, HttpStatus.OK);
    }
}
