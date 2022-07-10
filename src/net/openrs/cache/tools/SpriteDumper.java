package net.openrs.cache.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.Container;
import net.openrs.cache.FileStore;
import net.openrs.cache.ReferenceTable;
import net.openrs.cache.sprite.Sprite;
import net.openrs.util.ImageUtils;

/**
 * @author Kyle Friz
 * @since Dec 30, 2015
 */
public class SpriteDumper {

    public static void main(String[] args) throws IOException {
        File directory = new File(Constants.SPRITE_PATH);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            ReferenceTable table = cache.getReferenceTable(8);
            for (int i = 0; i < table.capacity(); i++) {
                if (table.getEntry(i) == null)
                    continue;

                Container container = cache.read(8, i);
                Sprite sprite = Sprite.decode(container.getData());

                for (int frame = 0; frame < sprite.size(); frame++) {
                    File file = new File(Constants.SPRITE_PATH, i +  ".png");

                    BufferedImage image = ImageUtils.createColoredBackground(ImageUtils.makeColorTransparent(sprite.getFrame(frame), Color.WHITE), new java.awt.Color(0xFF00FF, false));

                    ImageIO.write(image, "png", file);
                }

                double progress = (double) (i + 1) / table.capacity() * 100;

                System.out.printf("%d out of %d %.2f%s\n", (i + 1), table.capacity(), progress, "%");

            }

            Container container = cache.read(10, cache.getFileId(10, "title.jpg"));
            byte[] bytes = new byte[container.getData().remaining()];
            container.getData().get(bytes);
            Files.write(Paths.get(Constants.SPRITE_PATH).resolve("title.jpg"), bytes);

        }
    }

}

