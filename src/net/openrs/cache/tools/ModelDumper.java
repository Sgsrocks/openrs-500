package net.openrs.cache.tools;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.Container;
import net.openrs.cache.FileStore;
import net.openrs.cache.ReferenceTable;
import net.openrs.cache.util.CompressionUtils;

/**
 * @author Kyle Friz
 * @since Dec 30, 2015
 */
public class ModelDumper {

    public static void main(String[] args) throws IOException {
        File dir = new File("./dump/886models/");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (Cache cache = new Cache(FileStore.open("D:\\886cache"))) {
            ReferenceTable table = cache.getReferenceTable(7);

            for (int i = 0; i < table.capacity(); i++) {
                if (table.getEntry(i) == null)
                    continue;

                Container container = cache.read(7, i);
                byte[] bytes = new byte[container.getData().limit()];
                container.getData().get(bytes);

                try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(dir, i + ".gz")))) {
                    dos.write(CompressionUtils.gzip(bytes));
                }

                double progress = (double) i / table.capacity() * 100;

                System.out.printf("%.2f%s\n", progress, "%");
            }
        }
    }

}
