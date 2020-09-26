public enum Operation {
    SELF(new ArgType[]{}), // return itself as a value
    MAKE(new ArgType[]{
        ArgType.WORD, ArgType.VALUE
    }),
    THING(new ArgType[]{
        ArgType.WORD
    }),
    PRINT(new ArgType[]{
        ArgType.VALUE
    }),
    READ(new ArgType[]{}),
    ADD(new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    SUB(new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    MUL(new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    DIV(new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	}),
    MOD(new ArgType[]{
        ArgType.NUMBER, ArgType.NUMBER
	});

    private final ArgType[] argTypes;
    Operation(ArgType[] argTypes) {
        this.argTypes = argTypes;
    }
    ArgType[] getArgsType() {
        return argTypes;
    }
}
