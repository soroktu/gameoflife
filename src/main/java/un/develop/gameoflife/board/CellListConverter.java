package un.develop.gameoflife.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import un.develop.gameoflife.cell.Cell;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter
public class CellListConverter implements AttributeConverter<List<Cell>, String> {

    private Logger log = LoggerFactory.getLogger(getClass());
    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Cell> attribute) {
        String jsonString = "";
        try {
            log.debug("Start convertToDatabaseColumn");
            jsonString = objectMapper.writeValueAsString(attribute);
            log.debug("convertToDatabaseColumn" + jsonString);
        } catch (JsonProcessingException e) {
            log.debug("convertToDatabaseColumn" + jsonString);
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return jsonString;
    }

    @Override
    public List<Cell> convertToEntityAttribute(String dbData) {

        List<Cell> list = null;
        try {
            log.debug("Start convertToEntityAttribute");
            // convert json to list of POJO
            list = objectMapper.readValue(
                    dbData, new TypeReference<List<Cell>>() { });
            log.debug("JsonDocumentsConverter.convertToDatabaseColumn" + list);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
}
