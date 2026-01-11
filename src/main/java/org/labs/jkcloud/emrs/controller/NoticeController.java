package org.labs.jkcloud.emrs.controller;


import lombok.RequiredArgsConstructor;
import org.labs.jkcloud.emrs.model.Notice;
import org.labs.jkcloud.emrs.service.NoticeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @PostMapping
    public Notice createNotice(@RequestBody Notice notice) {
        return noticeService.saveNotice(notice);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable Long id, @RequestParam String fileUrl) {
        noticeService.deleteNotice(id, fileUrl);
    }
}
