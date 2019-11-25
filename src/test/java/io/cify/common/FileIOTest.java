package io.cify.common;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class FileIOTest {

    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    private File capabilitiesFile;

    @Before
    public void setUp() throws IOException {
        testProjectDir.create();
        capabilitiesFile = testProjectDir.newFile(Constants.CIFY_CONFIG_FILE_NAME);
    }

}
