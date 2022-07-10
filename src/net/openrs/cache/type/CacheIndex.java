package net.openrs.cache.type;

/**
 * @author Kyle Friz
 *
 * @since May 27, 2015
 */
public enum CacheIndex {

    FRAMES(0),
    FRAMEMAPS(1),
    CONFIGS(2),
    INTERFACES(3),
    SOUNDEFFECTS(4),
    MAPS(5),
    TRACK1(6),
    MODELS(7),
    SPRITES(8),
    TEXTURES(9),
    BINARY(10),
    TRACK2(11),
    CLIENTSCRIPT(12),
    FONTS(13),
    VORBIS(14),
    INSTRUMENTS(15),
    WORLDMAP(16);

    private final int id;

    CacheIndex(int id) {
        this.id = id;
    }

    public final int getID() {
        return id;
    }

}