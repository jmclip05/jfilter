package com.json.ignore.util;

import com.json.ignore.FieldAccessException;
import com.json.ignore.filter.file.FileConfig;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class FileUtilTest {
    private static final String EXISTED_FILE = "config.xml";
    private static final String UN_EXISTED_FILE = "unexisted_config.xml";

    @Test
    public void testResourceFileNotNull() {
        File file = FileUtil.resourceFile(EXISTED_FILE);
        assertNotNull(file);
    }

    @Test
    public void testResourceFileUnExistFile() {
        File file = FileUtil.resourceFile(UN_EXISTED_FILE);
        assertNull(file);
    }

    @Test
    public void testResourceFileNull() {
        File file = FileUtil.resourceFile(null);
        assertNull(file);
    }

    @Test
    public void testXmlFileToClassNotNull() {
        File file = FileUtil.resourceFile(EXISTED_FILE);
        FileConfig config = FileUtil.xmlFileToClass(file, FileConfig.class);
        assertNotNull(config);
    }

    @Test
    public void testXmlFileToClassNull() {
        FileConfig config = FileUtil.xmlFileToClass(null, FileConfig.class);
        assertNull(config);
    }

    @Test(expected = FieldAccessException.class)
    public void testXmlFileToClassIncorrectClass() {
        File file = FileUtil.resourceFile(EXISTED_FILE);
        FileUtilTest config = FileUtil.xmlFileToClass(file, FileUtilTest.class);
        assertNull(config);
    }

    @Test(expected = FieldAccessException.class)
    public void testXmlFileToClassBadFile() {
        File file = FileUtil.resourceFile("bad_config.xml");
        FileUtilTest config = FileUtil.xmlFileToClass(file, FileUtilTest.class);
        assertNull(config);
    }


}
