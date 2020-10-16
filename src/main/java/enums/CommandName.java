package enums;

public enum CommandName {
    JOIN,
    LEAVE,
    PLAY,
    NEXT,
    PAUSE,
    CLEAR,
    PLAYLIST,
    INFO,

    HELP,
    UNKNOWN;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

