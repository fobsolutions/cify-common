package io.cify.common.io;

import io.cify.common.CifyCommonException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {
    public static String read(String filePath) throws CifyCommonException {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new CifyCommonException(String.format("Cannot read file with path %s", filePath), e);
        }
    }

}
