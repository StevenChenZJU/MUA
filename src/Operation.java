public enum Operation {
    UNKNOWN(new ArgType[]{}), // ERROR TYPE
    VALUE(new ArgType[]{}), // VALUE TYPE -- return value
    NAME(new ArgType[]{}), // NAME TYPE -- interpreted as possible name
    MAKE(new ArgType[]{
        ArgType.WORD, ArgType.VALUE
    }),
    THING(new ArgType[]{
        ArgType.WORD // should be name, but name is a special word
    }),
    COMMA(new ArgType[]{}),
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

    public ArgType[] getArgsType() {
        return argTypes;
    }

}
