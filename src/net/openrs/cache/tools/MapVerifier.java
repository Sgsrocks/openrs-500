package net.openrs.cache.tools;

import java.util.Arrays;

import net.openrs.cache.Cache;
import net.openrs.cache.Constants;
import net.openrs.cache.FileStore;
import net.openrs.cache.util.XTEAManager;

/**
 * @author Kyle Friz
 *
 * @since Jun 30, 2015
 */
public class MapVerifier {

    public static void main(String[] args) {
        int count = 0;

        try (Cache cache = new Cache(FileStore.open(Constants.CACHE_PATH))) {
            for (int i = 0; i < 32_768; i++) {
                int[] keys = XTEAManager.lookup(i);
                int land = cache.getFileId(5, "l" + (i >> 8) + "_" + (i & 0xFF));

                if (land != -1) {
                    try {
                        cache.read(5, land, keys).getData();
                    } catch (Exception e) {
                        if (keys[0] != 0) {
                            System.out.println("Region ID: " + i + ", Coords: (" + ((i >> 8) << 6) + ", "
                                    + ((i & 0xFF) << 6) + "), File: (5, " + land + "), Keys: " + Arrays.toString(keys));
                            count++;

                            try {
                                cache.read(5, land).getData();
                                System.out.println("Region ID: " + i + " no longer encrypted");
                            } catch (Exception ee) {
                            }
                        }
                    }
                }
            }

            System.out.println("Incorrect: " + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
