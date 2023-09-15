package com.companyName.nihad.photoz.clone.web;

import com.companyName.nihad.photoz.clone.service.PhotozService;
import com.companyName.nihad.photoz.clone.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
public class PhotozController {
    private final PhotozService photozService;
    // automaticlly called when starting the app
    public PhotozController(@Autowired PhotozService photozService) {
        this.photozService = photozService;
    }

    @GetMapping("/")
    public String hello(){
        return "Hello world";
    }

    @GetMapping("/photoz")
    public Iterable<Photo> get(){
        return photozService.get();
    }

    @GetMapping("/photoz/{id}")
    public Photo get(@PathVariable Integer id){
        Photo photo = photozService.get(id);
        if(photo==null) throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        return photo;
    }
//     inConsoleClient code:
//      (async function deletePhoto(id){
//        await fetch('http://localhost:8090/photoz/'+id,{
//            method: "DELETE"
//        })})("1");
    @DeleteMapping("/photoz/{id}")
    public void delete(@PathVariable Integer id){
        photozService.remove(id);
    }
//    inConosoleClient code:
//      (async function createPhoto(){
//        let photo = {"fileName":"hello.jpg"}
//        await fetch('http://localhost:8090/photoz'+id,{
//                method: "POST",
//                headers: {
//            Accept: 'application/json',
//                    'Content-Type':'application/json'
//        },
//        body: JSON.stringify(photo)
//        }).then(result=>result.text()).
//        then(text => alert(text));
//    })();
    @PostMapping ("/photoz")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
    }


}
