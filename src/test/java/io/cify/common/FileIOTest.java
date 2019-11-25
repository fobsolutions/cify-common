package io.cify.common;

import io.cify.common.io.FileIO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileIOTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private File capabilitiesFile;

    @Before
    public void setUp() throws IOException {
        testProjectDir.create();
        capabilitiesFile = testProjectDir.newFile(CifyConstants.CIFY_CONFIG_FILE_NAME);
        Files.write(capabilitiesFile.toPath(), "This is line one\nThis is line two".getBytes());
    }

    @Test
    public void testRead() throws CifyCommonException {
        Assert.assertEquals("This is line one\nThis is line two", FileIO.read(capabilitiesFile.getPath()));
    }
}
