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
    UNKNOWN,
    PING,

    WEATHER,

    MCLEAR,
    MPRINT,
    MATH;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

