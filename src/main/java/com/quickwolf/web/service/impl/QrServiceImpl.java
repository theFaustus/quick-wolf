package com.quickwolf.web.service.impl;

import com.quickwolf.web.service.QrService;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.quickwolf.util.FileUtil;

@Service
public class QrServiceImpl implements QrService {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private FileUtil fileUtil;

    @Override
    public File generateQrCodeImage(String content) {
        File qrFile = fileUtil.createTempFile();
        ByteArrayOutputStream stream = QRCode.from(content)
                .withSize(200, 200)
                .to(ImageType.PNG)
                .stream();
        writeTo(stream, qrFile.getAbsolutePath());
        return moveFile(qrFile, "/resources/images");
    }

    private File moveFile(File qrFile, String destination) {
        try {
            String filePath = servletContext.getRealPath(destination) + File.separator + qrFile.getName();
            if (!Files.exists(Paths.get(filePath)))
                Files.createDirectories(Paths.get(filePath).getParent());
            Files.move(qrFile.toPath(), Paths.get(filePath));
            qrFile.delete();
            return new File(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeTo(ByteArrayOutputStream stream, String filePath) {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            out.write(stream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
