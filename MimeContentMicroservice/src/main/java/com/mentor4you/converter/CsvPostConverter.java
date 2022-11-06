package com.mentor4you.converter;

import com.mentor4you.domain.Post;
import com.mentor4you.domain.PostDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Component
public class CsvPostConverter implements ContentConverter<PostDTO> {

    @Override
    public String convert(Iterable<PostDTO> postDTOS) {
        try (StringWriter out = new StringWriter();
             CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("id", "title", "content"))) {
            postDTOS.forEach(postDTO -> {
                try {
                    final String postDtoAsString = postDTOAsCsvString(postDTO);
                    csvPrinter.printRecord(postDtoAsString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            csvPrinter.flush();
            out.flush();
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String postDTOAsCsvString(PostDTO postDTO) {
        return String.format("%s,%s,%s", postDTO.getId(), postDTO.getTitle(), postDTO.getContent());
    }
}
