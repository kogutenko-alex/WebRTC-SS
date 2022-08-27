package io.github.benkoff.webrtcss.videocall.controller;

import io.github.benkoff.webrtcss.videocall.service.VideoSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@ControllerAdvice
public class VideoController {
    private final VideoSocketService mainService;
    
    @Autowired
    public VideoController(VideoSocketService mainService) {
        this.mainService = mainService;
    }

    @GetMapping({"", "/", "/index", "/home", "/main"})
    public ModelAndView displayMainPage(Long id, String uuid) {
        return this.mainService.displayMainPage(id, uuid);
    }

    @PostMapping(value = "/room", params = "action=create")
    public ModelAndView processRoomSelection(@ModelAttribute("id") String sid, @ModelAttribute("uuid") String uuid, BindingResult binding) {
        return this.mainService.processRoomSelection(sid, uuid, binding);
    }

    @GetMapping("/room/{sid}/user/{uuid}")
    public ModelAndView displaySelectedRoom(@PathVariable("sid") String sid, @PathVariable("uuid") String uuid) {
        return this.mainService.displaySelectedRoom(sid, uuid);
    }

    @GetMapping("/room/{sid}/user/{uuid}/exit")
    public ModelAndView processRoomExit(@PathVariable("sid") String sid, @PathVariable("uuid") String uuid) {
        return this.mainService.processRoomExit(sid, uuid);
    }

    @GetMapping("/room/random")
    public ModelAndView requestRandomRoomNumber(@ModelAttribute("uuid") String uuid) {
        return mainService.requestRandomRoomNumber(uuid);
    }

}
