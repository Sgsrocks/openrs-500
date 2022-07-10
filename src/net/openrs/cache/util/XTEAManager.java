/**
* Copyright (c) Kyle Fricilone
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.openrs.cache.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import net.openrs.cache.Constants;

/**
 * @author Kyle Friz
 * 
 * @since Jun 30, 2015
 */
public final class XTEAManager {

      private static final Map<Integer, int[]> maps = new HashMap<>();
      private static final Map<Integer, int[]> tables = new HashMap<>();

      public static final int[] NULL_KEYS = new int[4];

      public static int[] lookupTable(int id) {
            int[] keys = tables.get(id);
            if (keys == null)
                  return NULL_KEYS;

            return keys;
      }

      public static int[] lookup(int region) {
            return maps.getOrDefault(region, NULL_KEYS);
      }

      public static boolean load(File xteaDir) throws IOException {
            final File[] files = xteaDir.listFiles() == null ? new File[0] : xteaDir.listFiles();

            for (File file : files) {
                  if (file.getName().endsWith(".txt")) {
                        final int region = Integer.valueOf(file.getName().substring(0, file.getName().indexOf(".txt")));
                        final int[] keys = Files.lines(xteaDir.toPath().resolve(file.getName())).map(Integer::valueOf).mapToInt(Integer::intValue).toArray();
                        maps.put(region, keys);
                  }
            }

            return !maps.isEmpty();
      }

}
