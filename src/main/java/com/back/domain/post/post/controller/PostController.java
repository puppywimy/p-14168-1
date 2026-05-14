package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/write")
    @ResponseBody
    public String write() {
        return """
                <div style="display: flex; align-items: center; justify-content: center; height: 100%">
                    <form action="doWrite" method="POST" style="display: flex; flex-direction: column; row-gap: 4px; width: 300px; padding: 4px; background-color: #abe8d7">
                        <input type="text" name="title" placeholder="제목" style="height: 24px" />
                        <textarea name="content" placeholder="내용" style="height: 96px; resize: none;" ></textarea>
                        <input type="submit" value="작성" style="height: 24px" />
                    </form>
                </div>
                """;
    }

    @PostMapping("/posts/doWrite")
    @ResponseBody
    @Transactional
    public String write(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String content
    ) {
        if (title.isBlank()) return """
                <div style="display: flex; align-items: center; justify-content: center; height: 100%">
                    <form action="doWrite" method="POST" style="display: flex; flex-direction: column; row-gap: 4px; width: 300px; padding: 4px; background-color: #abe8d7">
                        <div style="color: #ff0000">제목을 입력해 주세요.</div>
                        <input type="text" name="title" placeholder="제목" style="height: 24px" />
                        <textarea name="content" placeholder="내용" style="height: 96px; resize: none;" ></textarea>
                        <input type="submit" value="작성" style="height: 24px" />
                    </form>
                </div>
                """;
        if (content.isBlank()) return """
                <div style="display: flex; align-items: center; justify-content: center; height: 100%">
                    <form action="doWrite" method="POST" style="display: flex; flex-direction: column; row-gap: 4px; width: 300px; padding: 4px; background-color: #abe8d7">
                        <input type="text" name="title" placeholder="제목" style="height: 24px" />
                        <div style="color: #ff0000">내용을 입력해 주세요.</div>
                        <textarea name="content" placeholder="내용" style="height: 96px; resize: none;" ></textarea>
                        <input type="submit" value="작성" style="height: 24px" />
                    </form>
                </div>
                """;

        Post post = postService.write(title, content);

        return "%d번 글이 생성되었습니다.".formatted(post.getId());
    }
}
