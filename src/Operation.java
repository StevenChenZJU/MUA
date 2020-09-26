public enum Operation {
    SELF(0), // return itself as a value
    MAKE(2),
    THING(1),
    PRINT(1),
    READ(0),
    ADD(2),
    SUB(2),
    MUL(2),
    DIV(2),
    MOD(2);

    private final int argNumber;
    Operation(int argNumber) {
        this.argNumber = argNumber;
    }
}
