public enum Operation {
    SELF(0, new ArgType[]{}), // return itself as a value
    MAKE(2, new ArgType[]{
        ArgType.WORD, ArgType.VALUE
    }),
    THING(1, new ArgType[]{
        ArgType.NAME
    }),
    PRINT(1, new ArgType[]{
        ArgType.VALUE
    }),
    READ(0, new ArgType[]{}),
    ADD(2, new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    SUB(2, new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    MUL(2, new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    DIV(2, new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    MOD(2, new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	});

    private final int argNumber;
    private final ArgType[] argTypes;
    Operation(int argNumber, ArgType[] argTypes) {
        this.argNumber = argNumber;
        this.argTypes = argTypes;
    }
    ArgType[] getArgsType() {
        return argTypes;
    }
}
