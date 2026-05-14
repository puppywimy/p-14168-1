package com.back.domain.post.post.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {
    @GetMapping("/posts/write")
    @ResponseBody
    public String write() {
        return """
                <div style="display: flex; align-items: center; justify-content: center; height: 100%;">
                    <form action="doWrite" method="POST">
                        <input type="text" name="title" placeholder="제목" />
                        <br />
                        <textarea name="content" placeholder="내용"></textarea>
                        <br />
                        <input type="submit" value="작성" />
                    </form>
                </div>
                """;
    }
}
