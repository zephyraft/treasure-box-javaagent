package zephyr.agent.asm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ByteCodeUtils {

    private ByteCodeUtils() {
    }

    /**
     * 字节码写入文件
     * @param bytes 字节码
     * @param filePath 文件路径
     */
    public static Path writeToFile(byte[] bytes, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.write(path, bytes);
    }

}
