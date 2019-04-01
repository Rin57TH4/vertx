package com.rin.vertx.common;

import com.rin.vertx.code.entities.CommandFlag;

/**
 * Created by duongittien
 */
public class CommonFlagConfig {
    public static final CommandFlag CRT = new CommandFlag(CommandFlag.FLAG_ACTIVE, CommandFlag.FLAG_NO_CRYPTO, CommandFlag.FLAG_NOCOMPRESS);
    public static final CommandFlag LGI = new CommandFlag(CommandFlag.FLAG_ACTIVE, CommandFlag.FLAG_NO_CRYPTO, CommandFlag.FLAG_NOCOMPRESS);
    public static final CommandFlag RGI = LGI;
    public static final CommandFlag LBL = LGI;
    public static final CommandFlag ROL = LGI;
    public static final CommandFlag PNG = CRT;
    public static final CommandFlag ROM = LGI;
    public static final CommandFlag CHA = LGI;
    public static final CommandFlag GAME = LGI;
    public static final CommandFlag USR = LGI;
    public static final CommandFlag FRI = LGI;
    public static final CommandFlag PASSIVE = new CommandFlag(CommandFlag.FLAG_PASSIVE, CommandFlag.FLAG_NO_CRYPTO, CommandFlag.FLAG_NOCOMPRESS);
    public static final CommandFlag ACTIVE = new CommandFlag(CommandFlag.FLAG_ACTIVE, CommandFlag.FLAG_NO_CRYPTO, CommandFlag.FLAG_NOCOMPRESS);

    public static CommandFlag from(int flag) {
        return CommandFlag.buildFromByte(ApiCodeConfig.Type.getByteFromInt(flag));
    }

}
