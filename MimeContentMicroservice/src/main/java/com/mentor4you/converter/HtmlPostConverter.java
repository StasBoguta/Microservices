package com.mentor4you.converter;

import com.mentor4you.domain.PostDTO;
import org.springframework.stereotype.Component;

@Component
public class HtmlPostConverter implements ContentConverter<PostDTO> {

    private static final String DOCUMENT_PATTERN =
            """
            <!DOCTYPE html>
            <html>
            <head></head>
            <body>%s</body>
            </html>
            """;

    private static final String POST_PATTERN =
            """
            <div>
            <span>%s</span>
            <span>%s</span>
            <span>%s</span>
            <span>%s</span>
            <span>%s</span>
            <span>%s</span>
            <span>%s</span>
            <span>%s</span>
            </div>
            """;

    @Override
    public String convert(Iterable<PostDTO> postDTOS) {
        final StringBuilder postsAsHtml = new StringBuilder();
        postDTOS.forEach(postDTO ->
                postsAsHtml
                    .append(POST_PATTERN.formatted(
                            postDTO.getId(),
                            postDTO.getTitle(),
                            postDTO.getContent(),
                            postDTO.getAuthor().getId(),
                            postDTO.getAuthor().getEmail(),
                            postDTO.getAuthor().getRole(),
                            postDTO.getCategory().getId(),
                            postDTO.getCategory().getName())
                    .indent(8))
                    .append('\n')
        );
        postsAsHtml.deleteCharAt(postsAsHtml.length() - 1);
        return DOCUMENT_PATTERN.formatted(postsAsHtml.toString().indent(4));
    }
}
